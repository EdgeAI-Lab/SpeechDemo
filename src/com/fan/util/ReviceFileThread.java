package com.fan.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.Socket;

import android.os.Environment;

public class ReviceFileThread extends Thread {

	
	private Socket socket;
	
	public ReviceFileThread(Socket socket){
		this.socket = socket;
	}
	
	@Override
	public void run() {
		receiveFile(socket);
	}
	
	
	public void receiveFile(Socket socket) {

        byte[] inputByte = null;
        int length = 0;
        DataInputStream dis = null;
        FileOutputStream fos = null;
        try {
            try {

                dis = new DataInputStream(socket.getInputStream());
                String fileName = dis.readUTF();
                fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory().getPath() +"/"+ fileName));
                inputByte = new byte[1024*4];
                System.out.println("开始接收数据...");
                while ((length = dis.read(inputByte, 0, inputByte.length)) > 0) {
                    fos.write(inputByte, 0, length);
                    fos.flush();
                }
                System.out.println("完成接收");
            } finally {
                if (fos != null)
                    fos.close();
                if (dis != null)
                    dis.close();
                if (socket != null)
                    socket.close();
            }
        } catch (Exception e) {

        }

    }
	
}
