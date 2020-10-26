package com.basic.framework.controller.netease;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.basic.framework.utils.HttpUtil;
import com.jfinal.core.Controller;

/**
 * QQ音乐MP3
 * @Title QQMusicController.java
 * @Description 
 * @Company: 周大炮工作室
 * @author zg
 * @date 2020年10月23日 下午5:33:26
 * @version V1.0
 */
public class QQMusicController extends Controller{
	private Logger log = Logger.getLogger(QQMusicController.class);
	private static final String API_URL ="https://c.y.qq.com";
	
	/**
	 *	参数	说明	是否必须	默认值
	 *	p	分页	否	默认为1
	 *	n	请求数量	否	默认为10
	 *	w	关键字	是	无
	 *	format	格式化	否	如果用Retrofit的Json解析的话记得一定要format=json
	 *
	 * 说明：相对于搜索歌词来说，加了t参数：没有默认值，如果为搜索专辑，则t为8。如果是搜索mv,t=12。
	 * 
	 * *****/
	private static final String SEARCH_URL ="/soso/fcgi-bin/client_search_cp?p=1&format=json&t=8&w=";
	
	/**
	 * 获取播放地址的请求url
	 */
	private static final String player_url ="https://u.y.qq.com/cgi-bin/musicu.fcg?format=json&data=";

	/**
	 * 默认进入的方法 
	 * @throws UnsupportedEncodingException 
	 * @Title index
	 * @Description   
	 * @since 2020年10月26日 上午9:57:41
	 */
	public void index() throws UnsupportedEncodingException{
		this.mp3();
	}
	
	/**
	 * 
	 * @Title mp3
	 * @Description  
	 * @throws UnsupportedEncodingException 
	 * @since 2020年10月26日 上午10:06:33
	 */
	public void mp3() throws UnsupportedEncodingException{
		String paramName = getPara(0);
		if(StringUtils.isBlank(paramName)){
			paramName= getPara("name");
		}
		
		if(null == paramName){
			return;
		}
		paramName = URLDecoder.decode(paramName, "UTF-8");
		setAttr("name", paramName);
		
		try {
			JSONObject obj = this.apiRequest(paramName);
			log.info(obj);
			
			String songmid = "001jXE4i32kcZX";
			JSONObject mp3Json = this.getPlayerUrlParam(songmid);
			log.info(mp3Json);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 1.搜索歌曲和歌手：
	 * 请求地址：https://c.y.qq.com
	 * 请求示例：https://c.y.qq.com/soso/fcgi-bin/client_search_cp?p=1&n=2&w=没那么简单&format=json
	 * 说明：
	 * 参数	说明	是否必须	默认值
	 * p	分页	否	默认为1
	 * n	请求数量	否	默认为10
	 * w	关键字	是	无
	 * format	格式化	否	如果用Retrofit的Json解析的话记得一定要format=json
	 * 2.搜索专辑：
	 * 请求地址：https://c.y.qq.com
	 * 请求示例：https://c.y.qq.com/soso/fcgi-bin/client_search_cp?p=1&n=2&w=没那么简单&format=json&t=8
	 * 说明：相对于搜索歌词来说，加了t参数：没有默认值，如果为搜索专辑，则t为8。如果是搜索mv,t=12。
	 * @Title apiRequest
	 * @Description  
	 * @param name
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException 
	 * @since 2020年10月26日 下午2:16:00
	 */
	private JSONObject apiRequest(String name) throws ClientProtocolException, IOException {
		// https://autumnfish.cn/search?keywords= 海阔天空
		String url = API_URL + SEARCH_URL + name;
		String mp3Str = HttpUtil.sendGetData(url, "UTF-8");
		JSONObject obj = JSONObject.parseObject(mp3Str);
		return obj;
	}
	
	/**
	 * 根本albnumMID获取歌曲的播放地址
	 * 
	 * 
	 * 
	 * @Title apiRequest
	 * @Description  
	 * @param name
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException 
	 * @since 2020年10月26日 下午2:15:31
	 */
	private JSONObject getPlayerUrl(String name) throws ClientProtocolException, IOException {
		// https://autumnfish.cn/search?keywords= 海阔天空
		String url = API_URL + SEARCH_URL + name;
		String mp3Str = HttpUtil.sendGetData(url, "UTF-8");
		JSONObject obj = JSONObject.parseObject(mp3Str);
		return obj;
	}
	
	/**
	 * 说明:更换请求的话，只需要更换songmid后的003Ue6Ia32q4gl即可。
	 * 
	 * @Title getPlayerUrlParam
	 * @Description  
	 * @return 
	 * @since 2020年10月26日 下午2:01:38
	 */
	private JSONObject getPlayerUrlParam(String songmid){
		String text = "{\"req_0\":{\"module\":\"vkey.GetVkeyServer\",\"method\":\"CgiGetVkey\",\"param\":{\"guid\":\"358840384\","
				+ "\"songmid\":[\"003Ue6Ia32q4gl\"],\"songtype\":[0],\"uin\":\"1443481947\",\"loginflag\":1,\"platform\":\"20\"}},"
				+ "\"comm\":{\"uin\":\"18585073516\",\"format\":\"json\",\"ct\":24,\"cv\":0}}";
		JSONObject result = JSONObject.parseObject(text);
		if(StringUtils.isNotBlank(songmid)){
			JSONObject req0 = result.getJSONObject("req_0");
			JSONObject param = req0.getJSONObject("param");
			JSONArray  songmidArray =param.getJSONArray("songmid");
			songmidArray.clear();
			songmidArray.add(songmid);
		}
		return result;
	}
}
