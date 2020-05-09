package com.genner.formwork.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @Title HtmlUtils.java
 * @Description 
 * @Company: 周大炮工作室
 * @author zg
 * @date 2020年5月9日 上午10:05:21
 * @version V1.0
 */
public class HtmlUtils {

	/**
     * 输出信息到页面
     * @param resp
     * @param result
	 * @throws IOException 
     */
    public static void writeObject(HttpServletResponse resp,InputStream is) throws IOException{
    	String html = HtmlUtils.readToBuffer(is);
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer=null;
        try {
            writer= resp.getWriter();
            
            writer.println(html);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            writer.flush();
            writer.close();
        }
        
    }
    
    /**
	 * 将文本文件中的内容读入到buffer中
	 * 
	 * @Title readToBuffer
	 * @Description
	 * @param buffer
	 * @param filePath
	 * @throws IOException
	 * @since 2020年5月9日 下午1:19:16
	 */
	public static String readToBuffer(InputStream is) throws IOException {
		StringBuffer buffer = new StringBuffer();
		// InputStream is = new FileInputStream(filePath);
		String line; // 用来保存每行读取的内容
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		line = reader.readLine(); // 读取第一行
		while (line != null) { // 如果 line 为空说明读完了
			buffer.append(line); // 将读到的内容添加到 buffer 中
			buffer.append("\n"); // 添加换行符
			line = reader.readLine(); // 读取下一行
		}
		reader.close();
		is.close();
		return buffer.toString();
	}
}
