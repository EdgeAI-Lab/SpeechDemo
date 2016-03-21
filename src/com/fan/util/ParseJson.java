package com.fan.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.iflytek.voicedemo.Player;

import android.text.TextUtils;


public class ParseJson {


	private Player player;
	private String urlString = null;
	private String service;
	
	public ParseJson() {
		
		player = new Player();
	}
	
	
	public void parse(String jsonString){
		
		
		try {
			JSONObject root = new JSONObject(jsonString);
			service = root.getString("service");
			
			
			
			
			JSONObject data = root.getJSONObject("data");
			JSONArray result = data.getJSONArray("result");
			JSONObject url = result.getJSONObject(0);
			urlString = url.getString("downloadUrl");
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		switch () {
//		
//		case "music":
//			
//			break;
//
//		default:
//			break;
//		}
		

		if (!TextUtils.isEmpty(urlString)) {
			
			new Thread(new Runnable() {

				@Override
				public void run() {
						
					player.playUrl(urlString);
					
				}
			}).start();
		}
		
	}
	
	public void releasePlayer(){
		
		//音乐播放器
				if (player != null) {
					player.stop();
					player = null;
				}
	}
	
	
	public void stop(){
		
		player.pause();
	}
	
	
	
}
