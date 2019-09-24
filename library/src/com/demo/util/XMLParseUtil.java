package com.demo.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.innofi.utils.xml.XmlDocument;
import com.innofi.utils.xml.XmlNode;
import com.innofi.utils.xml.dom4j.Dom4jXmlBuilder;

/**
 * xml解析成List，内部是Map
 * 
 * @author gui
 */
public class XMLParseUtil {
	private static final Logger logger = Logger.getLogger(XMLParseUtil.class);
	/**
	 * 解析XMl数据，转化集合，并返回
	 * @param xml xml数据
	 * @return 返回用xml数据实例化之后的集合
	 */
	public static List parse(String xml) throws Exception {
		List list = new ArrayList();
		
		// dch 2014.06.09 11:08
		// 因编码问题，将xml头去除，使用系统默认编码：utf-8
		if(xml != null && !xml.startsWith("<?xml")){
			xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+xml;
			logger.debug("====xml====<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		}
		
		Dom4jXmlBuilder builder = new Dom4jXmlBuilder();
        XmlDocument mxXml = builder.buildDocument(xml);
        XmlNode rootNode = mxXml.getRootNode();
        XmlNode[] children = rootNode.getChildren();
		for(XmlNode node : children){
			List<XmlNode> leaves = findLeaves(node);
			Map map = transMap(leaves);
			list.add(map);
		}
		return list;
	}
	/**
	 * 解析XMl数据，转化集合，取指定的nodeName的值，并返回
	 * @param xml xml数据
	 * @return 返回用xml数据实例化之后的集合
	 */
	public static List parseNodename(String xml,String nodeName) throws Exception {
		List list = new ArrayList();
		
		// dch 2014.06.09 11:08
		// 因编码问题，将xml头去除，使用系统默认编码：utf-8
		if(xml != null && !xml.startsWith("<?xml")){
			xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+xml;
			logger.debug("====xml====<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		}
		
		Dom4jXmlBuilder builder = new Dom4jXmlBuilder();
        XmlDocument mxXml = builder.buildDocument(xml);
        XmlNode rootNode = mxXml.getRootNode();
        XmlNode[] children = rootNode.getChildren();
		for(XmlNode node : children){
			List<XmlNode> leaves = findLeaves(node);
			Map map = transMapNode(leaves, nodeName);
			list.add(map);
		}
		return list;
	}
	public static List parse(File file) throws Exception {
		List list = new ArrayList();
		Dom4jXmlBuilder builder = new Dom4jXmlBuilder();
        XmlDocument mxXml = builder.buildDocument(file);
        XmlNode rootNode = mxXml.getRootNode();
        XmlNode[] children = rootNode.getChildren();
		for(XmlNode node : children){
			List<XmlNode> leaves = findLeaves(node);
			Map map = transMap(leaves);
			list.add(map);
		}
		return list;
	}
	/**
	 * 递归法提取节点的值
	 * @param element 节点
	 * @param map 保存节点的值
	 */
	private static List<XmlNode> findLeaves(XmlNode node) {
		List<XmlNode> list = new ArrayList<XmlNode>();
		XmlNode[] children =  node.getChildren();
		if(children.length == 0){
			list.add(node);
		}
		for (XmlNode child : children) {
			list.addAll(findLeaves(child));
		}
		return list;
	}
	/**
	 * 递归法提取节点的值
	 * @param element 节点
	 * @param map 保存节点的值
	 */
	private static Map transMap(List<XmlNode> leaves) {
		Map map = new HashMap();
		for(XmlNode node : leaves){
			String name = node.getName();
			//不覆盖，多个值取第一个
			if(!map.containsKey(name)){
				map.put(node.getName(), node.getContent());
			}
		}
		return map;
	}
	/**
	 * 递归法提取指定节点的值
	 * @param element 节点
	 * @param map 保存节点的值
	 */
	private static Map transMapNode(List<XmlNode> leaves,String name) {
		Map map = new HashMap();
		for(XmlNode node : leaves){
			if(name.equals(node.getName())){
				map.put(node.getName(), node.getContent());
			}
		}
		return map;
	}
}