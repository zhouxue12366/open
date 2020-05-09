package com.genner.formwork.config;

import com.genner.formwork.model._MappingKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;

/**
 * 
 * @Title JfinalConf.java
 * @Description 
 * @Company: 周大炮工作室
 * @author zg
 * @date 2020年5月9日 下午2:48:32
 * @version V1.0
 */
public class JfinalConf {
	
	public static String jdbcUrl="jdbc:mysql://localhost/cuoxipo";
	public static String root="root";
	public static String password="root";
	
	/**
	 * 初始化数据库操作插件
	 * @Title initJfinalActiveRecord
	 * @Description   
	 * @since 2020年5月9日 下午2:48:29
	 */
	public static void initJfinalActiveRecord() {
		DruidPlugin dp = new DruidPlugin(jdbcUrl,root, password);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
		
		//如果不加下面的mapping()就无法使用类.dao操作数据库
		_MappingKit.mapping(arp);
		
		// 与 jfinal web 环境唯一的不同是要手动调用一次相关插件的start()方法
		dp.start();
		arp.start();
	}
	
	/**
	 * 创建连接池插件
	 * @Title createDruidPlugin
	 * @Description  
	 * @return 
	 * @since 2020年4月10日 上午9:22:55
	 */
	public static DruidPlugin createDruidPlugin() {
		return new DruidPlugin(jdbcUrl, root, password);
	}
}
