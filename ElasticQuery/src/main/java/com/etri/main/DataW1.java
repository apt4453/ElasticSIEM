package com.etri.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;

public class DataW1 extends Thread{

	SIEMClientEndpoint client;
	Gson gson = new Gson();
	long interval;
	
	String user = new String();
	
	String FILE_HOME = new String(Config.FILE_HOME);
	String FILE_PATH = new String(FILE_HOME + "WIN_1/win1_sample.csv");
	
	
	public DataW1(SIEMClientEndpoint c) {
		// TODO Auto-generated constructor stub
		client = c;
	}

	public void run(){
		
		String dataline;
	
		BufferedReader in_file = null;
		try {
			in_file = new BufferedReader(new FileReader(FILE_PATH));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        try {
			while ((dataline = in_file.readLine()) != null){
				
				String json = "";	
				
				//check tenant
				if(dataline.contains(user)){
					json = getMessage(dataline);
				}
				
				//check socket
				if(client != null){
					client.sendMessage(json);
				}
				
				Thread.sleep(interval);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setInterval(long t){
		
		interval = t;
	}
	
	public void setSocket(SIEMClientEndpoint c) {
		// TODO Auto-generated method stub
		
		client = c;
		
	}
	
	private String getMessage(String message) {
		WsMessage msg = new WsMessage();
		msg.setIsuser(false);
		msg.setMessageType(MessageType.FROMSERVER);
		msg.setMessage(message);
					
		String result = gson.toJson(msg);
		
		System.out.println("SEND DATA : "+result);
        return result;
    }

	public void setUser(String cu) {
		// TODO Auto-generated method stub
		
		user = cu;
		
	}

}
