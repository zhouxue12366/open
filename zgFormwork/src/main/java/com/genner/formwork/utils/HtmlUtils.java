package com.genner.formwork.utils;

import java.io.IOException;
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
     * @param o
     */
    public static void writeObject(HttpServletResponse resp,Object o){
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer=null;
        try {
            writer= resp.getWriter();
            writer.println(o);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            writer.flush();
            writer.close();
        }
    }
}
