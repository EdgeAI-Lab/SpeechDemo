package com.iflytek.voicedemo;

import java.io.IOException;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.util.Log;

public class Player implements OnBufferingUpdateListener, OnCompletionListener , OnPreparedListener {

	public MediaPlayer mediaPlayer; //媒体播放器

	
	// 初始化播放器
	public Player() {
		super();

		try {
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// 设置媒体流类型
			mediaPlayer.setOnBufferingUpdateListener(this);
			mediaPlayer.setOnPreparedListener(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	

	public void play() {
		mediaPlayer.start();
	}
	
	
	public Boolean isPlaying(){
		return mediaPlayer.isPlaying();
	}

	/**
	 * 
	 * @param url 地址
	 *       
	 */
	public void playUrl(String url) {
		try {
			
			mediaPlayer.reset();
			mediaPlayer.setDataSource(url); // 设置数据源
			mediaPlayer.prepare(); // prepare自动播放
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 暂停
	public void pause() {
		mediaPlayer.pause();
	}
	
	public void seekTo(int millisecond){
		mediaPlayer.seekTo(millisecond);
	}
	
	public int getCurrentPosition(){
		int millisecond = mediaPlayer.getCurrentPosition();
		return millisecond;
	}

	// 停止
	public void stop() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}

	
	@Override
	public void onPrepared(MediaPlayer mp) {
		mp.start();
		Log.e("mediaPlayer", "onPrepared");
		
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		Log.e("mediaPlayer", "onCompletion");
	}

	/**
	 * 缓冲更新
	 */
	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		// TODO Auto-generated method stub
		
	}



	

	

}
