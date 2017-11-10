package com.etri.main;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
 
public class vSiemConnector {


	static Socket socket;
	 
	static BufferedReader in;
	
	static DataOutputStream out;
	
	static Socket client;
	
	static ServerSocket serverSocket;
	
	static BufferedReader insiem;
	

	public vSiemConnector(String dash_address, int port) {
		// TODO Auto-generated constructor stub
		try {
			socket = new Socket(dash_address, port);
			 
			// 입력 스트림
			// 서버에서 보낸 데이터를 받음
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	
			// 출력 스트림
			// 서버에 데이터를 송신
			out = new DataOutputStream(socket.getOutputStream());
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
 
		/*
		try {
			serverSocket = new ServerSocket(9199);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Socket socket = listener.accept();
		try {
			client = serverSocket.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			insiem = new BufferedReader(new InputStreamReader(client.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
	
	public static boolean isAlive(){
		
		if(out == null){
			
			return false;
		}
		
		else return true;
		
	}
	
	public static void send(String str){
		
		str = str+"\n";
		
		
		try {
			
			if(out != null){
				out.writeBytes(str);
				out.flush();
				System.out.println("TO SIEM MSG : "+str);
				
			}
			else {
				System.out.println("OUT SOCKET IS NULL");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
 
	}

}
