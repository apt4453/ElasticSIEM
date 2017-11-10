package com.etri.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Timer {

	public Timer() {
		// TODO Auto-generated constructor stub
	}
	
	public static String getOneMinuteAgoTime(){
		
		Date date = new Date();
		
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Calendar cal = Calendar.getInstance();
		   
		cal.setTime(date);
		cal.add(Calendar.MINUTE, -60);
		
		String today = sdformat.format(cal.getTime());  
		System.out.println("10분 : " + today);
		
		return today;
	}
	
	public static String getTenMinuteAgoTime(){
		
		Date date = new Date();
		
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Calendar cal = Calendar.getInstance();
		   
		cal.setTime(date);
		cal.add(Calendar.MINUTE, -60);
		
		String today = sdformat.format(cal.getTime());  
		System.out.println("10분 : " + today);
		
		return today;
	}
	
	
	public static String getToday(){
		Date date = new Date();
		
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyyMMdd"); 
		Calendar cal = Calendar.getInstance();
		   
		cal.setTime(date);
		String today = sdformat.format(cal.getTime());  
		System.out.println("Today : " + today);
		
		return today;
		
	}
	
	public static String getYesterday(){
		Date date = new Date();
		
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyyMMdd"); 
		Calendar cal = Calendar.getInstance();
		   
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);
		String today = sdformat.format(cal.getTime());  
		System.out.println("Today : " + today);
		
		return today;
		
	}
	
	public static String convertFormat(String date) throws ParseException{
		
		SimpleDateFormat og = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd_HHmmss");
		Date d = og.parse(date);
		String res = f.format(d);
		
		return res;
			
		
	}
	
	public static String convertFormat2(String date) throws ParseException{
		
		SimpleDateFormat og = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd_HHmmss");
		Date d = f.parse(date);
		String res = og.format(d);
		
		return res;
			
		
	}
	
	public static String getBaseTime(int y, int m, int d, int h, int mm, int ss, int gap){
		Date date = new Date();
		
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Calendar cal = Calendar.getInstance();
		   
		cal.set(y,m-1,d,h,mm,ss);
		cal.add(Calendar.MINUTE, gap);
		
		String today = sdformat.format(cal.getTime());  
		System.out.println(today);
		
		return today;
	}
	
	public static void main(String args[]){
		
		//Timer.getOneMinuteAgoTime();
		
		//Timer.getToday();
		
		Timer.getBaseTime(2017,11,7,0,0,0, 10);
		
	}


}
