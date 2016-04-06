package com.fan.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.speech.util.JsonParser;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

public class RollCall extends Thread {

	
	// 用HashMap存储听写结果
	private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
	private StringBuffer resultBuffer;
	
	private String line = null;
	
	private SpeechSynthesizer mTts;
	private SpeechRecognizer mIat;
	
	private static byte noAskCnt = 0;
	
	private byte studentTotalNum = 0;
	private byte studentAbsentNum = 0;
	
	
	private FileReader fr;
	private BufferedReader br;
	
	public RollCall(){
		this.mTts = SpeechSynthesizer.getSynthesizer();
		this.mIat = SpeechRecognizer.getRecognizer();
	}
	

	
	
	@Override
	public void run() {
		
		mTts.startSpeaking("好的，现在进入点名时间", null);
		
		//
		handler.postDelayed(runnable, 3000);
		
		try {
			
			fr = new FileReader(Environment.getExternalStorageDirectory().getPath() +"/"+ "test.txt");
			br = new BufferedReader(fr);
			
			
//			while ((line = br.readLine()) != null) {
//				
//				Thread.sleep(3000);
//				System.out.println(line);
//				mTts.startSpeaking(line, mTtsListener);
//				while (true){
//					System.out.println("loop");
//					if (nextFlag) {
//						break;
//					}
//				}
//			}
			
			
			
			
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	
	Handler handler=new Handler();
	Runnable runnable=new Runnable() {  
        @Override  
        public void run() { 
        	
        	System.out.println("run");
			try {
				
				if ((line = br.readLine()) != null) {
					studentTotalNum ++;
					System.out.println(line);
					mTts.startSpeaking(line, mTtsListener);
				}else {
					mTts.startSpeaking("点名结束，共有"+studentTotalNum+"名小朋友，"+"其中"+studentAbsentNum+"名小朋友没到！", null);
					handler.removeCallbacks(runnable);
					br.close();
					fr.close();
				}
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
        	
  
        }  
    };  
	
	/**
	 * 合成回调监听。
	 */
	private SynthesizerListener mTtsListener = new SynthesizerListener() {
		
		@Override
		public void onSpeakResumed() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onSpeakProgress(int arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onSpeakPaused() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onSpeakBegin() {
			System.out.println("It's me!");
			
		}
		
		@Override
		public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onCompleted(SpeechError arg0) {
			mIat.startListening(mRecognizerListener);
		}
		
		@Override
		public void onBufferProgress(int arg0, int arg1, int arg2, String arg3) {
			// TODO Auto-generated method stub
			
		}
	};
	

	/**
	 * 听写监听器。
	 */
	private RecognizerListener mRecognizerListener = new RecognizerListener() {
		
		@Override
		public void onVolumeChanged(int arg0, byte[] arg1) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onResult(RecognizerResult results, boolean isLast) {

			String text = JsonParser.parseIatResult(results.getResultString());
			String sn = null;
			
			// 读取json结果中的sn字段
			try {
				JSONObject resultJson = new JSONObject(results.getResultString());
				sn = resultJson.optString("sn");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			mIatResults.put(sn, text);

			
			resultBuffer = new StringBuffer();
			for (String key : mIatResults.keySet()) {
				resultBuffer.append(mIatResults.get(key));
			}
			
			
			if(isLast) {
				String resultString = resultBuffer.toString();
				System.out.println(resultString);
				if ("到。".equals(resultString)) {
					
					handler.post(runnable);
				}
			}
			
		}
		
		@Override
		public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onError(SpeechError error) {
			
			//没人应答
			if (error.getPlainDescription(true).contains("10118")) {
				
				if (noAskCnt < 2) {
					noAskCnt++;
					mTts.startSpeaking(line, mTtsListener);
				}else {
					noAskCnt = 0;
					studentAbsentNum++;
					handler.post(runnable);
				}
			}
			
		}
		
		@Override
		public void onEndOfSpeech() {
			System.out.println("结束说话");
			
		}
		
		@Override
		public void onBeginOfSpeech() {
			System.out.println("开始说话");
			
		}
	};
	
	
	
	
	
}
