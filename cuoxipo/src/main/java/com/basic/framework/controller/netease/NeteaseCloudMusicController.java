package com.basic.framework.controller.netease;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.basic.framework.interceptor.LoginInterceptor;
import com.basic.framework.utils.HttpUtil;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;

/**
 * 网易云音乐下载 https://music.163.com/song?id=5270974&userid=1398493188
 * 
 * @Title NeteaseCloudMusicController.java
 * @Description 借鉴：https://blog.csdn.net/jadsgfuhya/article/details/108953448?
 *              utm_medium=distribute.pc_category.none-task-blog-hot-3.nonecase&
 *              depth_1-utm_source=distribute.pc_category.none-task-blog-hot-3.
 *              nonecase&request_id=
 * 
 * @Company: 周大炮工作室
 * @author zg
 * @date 2020年10月9日 下午3:39:58
 * @version V1.0
 * 
 *          获取到音乐的 id 后，将 https://music.163.com/song/media/outer/url?id=id.mp3 以
 *          src 赋予 Audio 即可播放
 *          --------------------------------
 *  API：https://binaryify.github.io/NeteaseCloudMusicApi/#/?id=%e8%8e%b7%e5%8f%96%e6%ad%8c%e6%9b%b2%e8%af%a6%e6%83%85
 */
public class NeteaseCloudMusicController extends Controller {
	private Logger log = Logger.getLogger(NeteaseCloudMusicController.class);

	private String API_URL = "https://autumnfish.cn";
	private String SEARCH_URL = "/search?keywords=";
	// private String CLOUDSEARCH_URL = "/cloudsearch?keywords=";
	 private String DETAIL_URL = "/song/detail?ids=";
	 
	 @Clear(LoginInterceptor.class)
	 public void index() throws UnsupportedEncodingException{
		 this.mp3();
	 }

	@Clear(LoginInterceptor.class)
	public void mp3() throws UnsupportedEncodingException {
		// URLDecoder.decode(request.getRequestURI(),encoding）
		String paramName = getPara(0);
		if(StringUtils.isBlank(paramName)){
			paramName= getPara("name");
		}
		
		if(null == paramName){
			return;
		}
		paramName = URLDecoder.decode(paramName, "UTF-8");
		setAttr("name", paramName);

		JSONArray mp3List = new JSONArray();
		try {
			JSONObject obj = this.apiRequest(paramName);
			JSONObject result = JSONObject.parseObject(obj.getString("result"));
			JSONArray songs = JSONArray.parseArray(result.getString("songs"));
			JSONObject mp3 = null;

			for (int i = 0; i < songs.size(); i++) {
				JSONObject song = songs.getJSONObject(i);
				mp3 = new JSONObject();

				String id = song.getString("id");
				String name = song.getString("name");
				// 专辑
				String album = song.getString("album");
				JSONObject albumJson = JSONObject.parseObject(album);
				String albumName = albumJson.getString("name");

				// 作者艺术家
				String artists = song.getString("artists");
				JSONArray artistsArray = JSONArray.parseArray(artists);
				JSONObject artistsObj = artistsArray.getJSONObject(0);
				if (i <= 0) {
					//获取歌曲详情
					JSONObject details= this.apiSongDetail(id);
					JSONArray detailSongs = JSONArray.parseArray(details.getString("songs"));
					if(null != detailSongs && detailSongs.size() >0){
						JSONObject detail = detailSongs.getJSONObject(0);
						JSONObject al = detail.getJSONObject("al");
						set("imgUrl", al.getString("picUrl"));
					}
				}
				// System.out.println("id="+id+",name="+name+",专辑="+albumName);

				String url = " https://music.163.com/song/media/outer/url?id=" + id + ".mp3";
				mp3.put("id", id);
				mp3.put("name", name);
				mp3.put("url", url);
				mp3.put("url", url);
				mp3.put("imgUrl", artistsObj.getString("img1v1Url"));
				mp3.put("albumName", albumName);
				mp3List.add(mp3);
			}
			log.info(mp3List);
			setAttr("mp3List", mp3List);
			return;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取歌曲基本信息
	 * @Title apiRequest
	 * @Description  
	 * @param name
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException 
	 * @since 2020年10月10日 下午4:22:50
	 */
	private JSONObject apiRequest(String name) throws ClientProtocolException, IOException {
		// https://autumnfish.cn/search?keywords= 海阔天空
		String url = API_URL + SEARCH_URL + name;
		String mp3Str = HttpUtil.sendGetData(url, "UTF-8");
		JSONObject obj = JSONObject.parseObject(mp3Str);
		return obj;
	}
	
	/**
	 * 获取歌曲详情
	 * @Title apiSongDetail
	 * @Description  
	 * @param ids
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException 
	 * @since 2020年10月10日 下午4:22:42
	 */
	private JSONObject apiSongDetail(String ids) throws ClientProtocolException, IOException{
		String url = API_URL + DETAIL_URL + ids;
		String mp3Str = HttpUtil.sendGetData(url, "UTF-8");
		JSONObject detail = JSONObject.parseObject(mp3Str);
		return detail;
	}

	public String download(String id) {
		String url = "https://music.163.com/song/media/outer/url?id=" + id + ".mp3";
		return url;
	}

	/**
	 * 获取歌曲下载地址 调用的https://api.imjad.cn/接口
	 * 
	 * @Title getDownloadUrl
	 * @Description
	 * @param id
	 * @return
	 * @since 2020年10月9日 下午4:40:57
	 */
	private String getDownloadUrl(String id) {

		/***
		 * br :( int 指定歌曲码率，可用值为 64000,128000,198000,320000,)默认=128000 raw:(bool
		 * 指定是否直接 302 到歌曲直链，可用值为 true 或 false) 默认=false
		 */
		String url = "https://api.imjad.cn/cloudmusic/?type=song&id=" + id + "&br=128000";

		String result = "";
		try {
			result = HttpUtil.sendGetData(url, "UTF-8");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
