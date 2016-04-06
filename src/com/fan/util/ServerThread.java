package com.fan.util;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {

	@Override
	public void run() {
		try {
			

			//循环侦听是否有客户端连接
			while (true) {
				
				ServerSocket serverSocket = new ServerSocket(3000);
				System.out.println("服务器Socket创建成功");
				
				System.out.println("等待客户机的连接");
				Socket socket = serverSocket.accept();
				
				//开启接收文件线程
			    new ReviceFileThread(socket).start();
			    
			    
			    //关闭serverSocket
				if (serverSocket != null) {
					serverSocket.close();
				}
					
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	 

}
