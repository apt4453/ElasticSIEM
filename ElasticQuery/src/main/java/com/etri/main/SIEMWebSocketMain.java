package com.etri.main;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Scanner;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.MessageHandler;

import com.google.gson.Gson;




public class SIEMWebSocketMain {
	
	static Gson gson = new Gson();
	static SIEMClientEndpoint client;
	
	static DataW1 data_w1;
	static DataW6 data_w6;
	static DataW7 data_w7;
	static DataW8 data_w8;
	static DataW9 data_w9;
	static DataW11 data_w11;
	
	
	
	static String currentUser = new String();
	
	static MessageHandler handler = new MessageHandler() {
		
		public void handleMessage(String message) {
			// TODO Auto-generated method stub
			System.out.println("Received : " + message);
		}
		
		//current User Set
	};

	
	public SIEMWebSocketMain(){
		
		String addr;
		boolean isalive = true;
		
		Scanner sc = new Scanner(System.in);
		System.out.println("WebSocket 주소 입력");
		addr = sc.nextLine();
		
		if(addr == null || addr.length() == 0){
			addr = "ws://localhost:8080/vSiem/websocket";
		}
		
		System.out.println("Address : " + addr);
		
		//for test
		currentUser = "ZENTO";
		
		//try {
			//client = new SIEMClientEndpoint(new URI(addr));
			//client.addMessageHandler(handler);
			new DataW(client);
		
			/*
		} catch (URISyntaxException e2) {
			// TODO Auto-generated catch block
			 e2.printStackTrace();
		}*/
	}
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String addr;
		boolean isalive = true;
		
		Scanner sc = new Scanner(System.in);
		System.out.println("WebSocket 주소 입력");
		addr = sc.nextLine();
		
		if(addr == null || addr.length() == 0){
			addr = "ws://localhost:8080/vSiem/websocket";
		}
		
		System.out.println("Address : " + addr);
		
		//for test
		currentUser = "ZENTO";
		
		//try {
			//client = new SIEMClientEndpoint(new URI(addr));
			//client.addMessageHandler(handler);
			
			String msg;
			
			//w1 data
			data_w1 = new DataW1(client);
			data_w1.setUser(currentUser);
			data_w1.setInterval(5000);
			data_w1.start();
			
			//w6 data
			data_w6 = new DataW6(client);
			data_w6.setUser(currentUser);
			data_w6.setInterval(5000);
			data_w6.start();
			
			//w7 data
			data_w7 = new DataW7(client);
			data_w7.setUser(currentUser);
			data_w7.setInterval(5000);
			data_w7.start();
			
			
			//w8 data
			data_w8 = new DataW8(client);
			data_w8.setUser(currentUser);
			data_w8.setInterval(5000);
			data_w8.start();
			
			//w9  data
			data_w9 = new DataW9(client);
			data_w9.setUser(currentUser);
			data_w9.setInterval(5000);
			data_w9.start();
			
			//w11  data
			data_w11 = new DataW11(client);
			data_w11.setUser(currentUser);
			data_w11.setInterval(5000);
			data_w11.start();
			
			
			System.out.println();
			/*
			while(isalive){
				
				System.out.println("메시지를 입력하시오.");
				msg = sc.nextLine();
				
				if(msg == null || msg.length() == 0)
					continue;
				
				if(msg.equalsIgnoreCase("exit"))
					isalive = false;
				else{
					
					String json = "";	
					
					json = getMessage(msg);
										
					//System.out.println("전송 메시지 : " + json);
					
					client.sendMessage(json);
					
				}
				
			}*/
			
			//client.Close();
		
		/*
		} catch (URISyntaxException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}*/
		
		
	}
	
	
	private static String getMessage(String message) {
		WsMessage msg = new WsMessage();
		msg.setIsuser(false);
		msg.setMessageType(MessageType.FROMSERVER);
		msg.setMessage(message);
					
		String result = gson.toJson(msg);
		
		System.out.println("SEND DATA : "+result);
        return result;
    }

}
