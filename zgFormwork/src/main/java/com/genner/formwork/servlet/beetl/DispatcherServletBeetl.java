package com.genner.formwork.servlet.beetl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.beetl.core.resource.WebAppResourceLoader;
import org.beetl.ext.servlet.ServletGroupTemplate;

import com.genner.formwork.annotation.Autowired;
import com.genner.formwork.annotation.Controller;
import com.genner.formwork.annotation.RequestMapping;
import com.genner.formwork.annotation.Service;
import com.genner.formwork.utils.StringUtils;

/**
 * 手写的一个spring
 * 
 * @Title DispatcherServlet.java
 * @Description
 * @Company: 周大炮工作室
 * @author zg
 * @date 2020年5月8日 下午6:14:47
 * @version V1.0
 */
public class DispatcherServletBeetl extends HttpServlet {

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 1L;

	private Properties contextConfig = new Properties();

	private List<String> classNames = new ArrayList<String>();

	private Map<String, Object> ioc = new HashMap<String, Object>();

	private Map<String, Method> handlerMapping = new HashMap<String, Method>();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 根据URL去找到对应的Method
		try {
			resp.setContentType("text/html;charset=utf-8");
			this.doDispatch(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
			resp.getWriter().write("500, Exception detaii:" + Arrays.toString(e.getStackTrace()));
		}

	}

	/**
	 * 根据URL去找到对应的Method
	 * 
	 * @Title doDispatch
	 * @Description
	 * @param request
	 * @param response
	 * @throws IOException
	 * @since 2020年5月8日 下午4:30:45
	 */
	private void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String url = request.getRequestURI();
		String contextPath = request.getContextPath();
		url = url.replaceAll(contextPath, "").replaceAll("/+", "/");

		if (!this.handlerMapping.containsKey(url)) {
			response.getWriter().write("404 Not Found!!!");
			return;
		}

//		 Map<String, String[]> params = request.getParameterMap();

		Method method = this.handlerMapping.get(url);
		String beanName = StringUtils.toLowerFirstCase(method.getDeclaringClass().getSimpleName());
		Object result = null;
		
		Parameter[] methodParams = method.getParameters();
		if (null == methodParams || 0 == methodParams.length) {
			result = method.invoke(ioc.get(beanName));
		} else {
			List<Object> objects = new ArrayList<Object>();
			for (Parameter parameter : methodParams) {
				Class<?> types = parameter.getType();
				if (types.equals(HttpServletRequest.class)) {
					objects.add(request);
				} else if (types.equals(HttpServletResponse.class)) {
					objects.add(response);
				} else if (types.equals(HttpSession.class)) {
					objects.add(request.getSession());
				} else {
					
					objects.add(request.getParameter(parameter.getName()));
				}
				// System.out.println(types);
			}
			result = method.invoke(ioc.get(beanName), objects.toArray());
		}

		// String basePathHtml = "/views/page/";
		String suffix = ".html";
		// String html = basePathHtml + result.toString() + suffix;
		// 直接返回
		// InputStream is = this.getClass().getClassLoader()
		// .getResourceAsStream(html);
		// HtmlUtils.writeObject(resp, is);

		// 使用beetl模板
		WebAppResourceLoader resourceLoader = new WebAppResourceLoader();
		ServletGroupTemplate.instance().getGroupTemplate().setResourceLoader(resourceLoader);
		ServletGroupTemplate.instance().render(result.toString() + suffix, request, response);
	}

	@Override
	public void init(ServletConfig config) throws ServletException {

		// 扫描并加载配置文件
		this.doLoadConfig(config.getInitParameter("contextConfigLocation"));

		// 扫描相关的类
		this.doScanner(contextConfig.getProperty("scanPackage"));

		// ioc容器初始实例化，并且实例化扫描到的相关的类放入到ioc容器之中
		this.doInstance();

		// 完成依赖注入
		this.doAutowired();

		// 初始化HandlerMapping
		this.doInitHandlerMapping();

	}

	/**
	 * 初始化HandlerMapping
	 * 
	 * @Title doInitHandlerMapping
	 * @Description
	 * @since 2020年5月8日 下午4:26:58
	 */
	private void doInitHandlerMapping() {
		if (ioc.isEmpty()) {
			return;
		}

		for (Entry<String, Object> entry : ioc.entrySet()) {
			Class<?> clazz = entry.getValue().getClass();

			// 判断是否加了controller注解
			if (!clazz.isAnnotationPresent(Controller.class)) {
				continue;
			}

			String baseUrl = "";
			if (clazz.isAnnotationPresent(RequestMapping.class)) {
				RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
				baseUrl = requestMapping.value();
			}

			for (Method method : clazz.getMethods()) {
				if (!method.isAnnotationPresent(RequestMapping.class)) {
					continue;
				}

				RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
				String url = ("/" + baseUrl + "/" + requestMapping.value()).replaceAll("/+", "/");
				handlerMapping.put(url, method);
				System.out.println("Mapped" + url + "," + method);
			}

		}
	}

	/**
	 * 完成依赖注入
	 * 
	 * @Title doAutowired
	 * @Description
	 * @since 2020年5月8日 下午4:27:06
	 */
	private void doAutowired() {
		if (ioc.isEmpty()) {
			return;
		}
		for (Entry<String, Object> entry : ioc.entrySet()) {
			Field[] fields = entry.getValue().getClass().getDeclaredFields();
			for (Field field : fields) {
				if (!field.isAnnotationPresent(Autowired.class)) {
					continue;
				}

				Autowired autowired = field.getAnnotation(Autowired.class);
				String beanName = autowired.value();
				if ("".equals(beanName)) {
					beanName = field.getType().getName();
				}

				// 强制暴力访问
				field.setAccessible(true);

				try {
					field.set(entry.getValue(), ioc.get(beanName));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * ioc容器初始化实例化，并且实例化扫描到的相关的类放入到ioc容器之中
	 * 
	 * @Title doInstance
	 * @Description
	 * @since 2020年5月8日 下午4:27:13
	 */
	private void doInstance() {
		if (classNames.isEmpty()) {
			return;
		}

		try {
			for (String className : classNames) {
				Class<?> clazz = Class.forName(className);

				if (clazz.isAnnotationPresent(Controller.class)) {
					// 默认类名首字母小写
					String beanName = StringUtils.toLowerFirstCase(clazz.getSimpleName());
					Object instance = clazz.newInstance();
					ioc.put(beanName, instance);

				} else if (clazz.isAnnotationPresent(Service.class)) {
					// 默认类名首字母小写
					String beanName = StringUtils.toLowerFirstCase(clazz.getSimpleName());

					// 自定义命名beanName
					Service service = clazz.getAnnotation(Service.class);
					if (!"".equals(service.value())) {
						beanName = service.value();
					}

					Object instance = clazz.newInstance();
					ioc.put(beanName, instance);

					// 根据类型来起beanName
					for (Class<?> i : clazz.getInterfaces()) {
						if (ioc.containsKey(i.getName())) {
							throw new Exception("the beanName is exists!!!");
						}
						ioc.put(i.getName(), instance);
					}

				} else {
					continue;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 扫描相关的类
	 * 
	 * @Title doScanner
	 * @Description
	 * @since 2020年5月8日 下午4:27:20
	 */
	private void doScanner(String scanPackage) {
		// 拿到包替换成文件路径
		URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));
		File classPath = new File(url.getFile());
		for (File file : classPath.listFiles()) {
			if (file.isDirectory()) {
				// 如果是文件夹就递归
				doScanner(scanPackage + "." + file.getName());
			} else {
				if (!file.getName().endsWith(".class")) {// 排除其他文件
					continue;
				}
				String className = (scanPackage + "." + file.getName().replace(".class", ""));
				// 反射class.forName(className).newInstance();
				classNames.add(className);
			}

		}

	}

	/**
	 * 扫描并加载配置文件
	 * 
	 * @Title doLoadConfig
	 * @Description
	 * @since 2020年5月8日 下午4:27:28
	 */
	private void doLoadConfig(String contextConfigLocation) {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
		try {
			contextConfig.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
