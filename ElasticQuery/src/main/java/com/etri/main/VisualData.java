package com.etri.main;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class VisualData {

	public static String makeDataW_1(String time, String str, long count) {
		// TODO Auto-generated method stub
		String[] elem = str.split(",");
		String _event = elem[1];
		String decision = elem[6];
		//_time+","+"ZENTO"+","+"W_1"+","+"vIDS"+","+"10"+","+gline+","+_totalevent+","+"NORMAL"
		
		HashMap<Integer, Integer> pool = new HashMap<Integer, Integer>();
		
		String[] list = _event.split(":");
		String result = new String();
		
		for(int i = 0; i< 10; i++){
			pool.put(i, 0);
		}
		
		for(int i=0 ; i< list.length ;i++){
			
			String[] val = list[i].split("=");
			Integer g = Integer.parseInt(val[0].substring(1)) % 10;
			Integer v = Integer.parseInt(val[1]);
			Integer p = pool.get(g);
		
			pool.put(g, p+v);
			
		}
		
		
		Iterator<Integer> iterator = pool.keySet().iterator();
		
		while(iterator.hasNext()){	
			
			int i = iterator.next();
			result = result.concat("G_"+i+"="+pool.get(i));
			if(i<9) result = result.concat(":");
				
		}
		
		StringBuilder w1 = new StringBuilder();
		
		w1.append(time+",");
		w1.append(SIEMWebSocketMain.currentUser+",W8,vIPS,10");
		w1.append(result+","+count+","+decision);
		
		System.out.println("<W1> "+w1.toString());
		return w1.toString();
		
		//return result;
	}
		
		
	
	public static String makeDataW_8(String str, long count){
		
		String[] elem = str.split(",");
		String date = null;
		try {
			date = Timer.convertFormat2(elem[0]);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String payload = elem[1];
		
		StringBuilder w8 = new StringBuilder();
		
		w8.append(date+",");
		w8.append(SIEMWebSocketMain.currentUser+",W8,vIPS,");
		w8.append(payload+","+count);
		
		System.out.println("<W8> "+w8.toString());
		return w8.toString();
		
	}
	
	public static String makeDataW_7(String date, String _iplist) {
		// TODO Auto-generated method stub
		HashMap<String, String> session = new HashMap<String, String>();
		String result = new String();
		
		ArrayList<String> red_ip = new ArrayList<String>();
		
		String[] list = _iplist.split(":");
		int half = list.length / 2;
		
		for(int i = 0 ; i<half ; i++){
			String[] src = list[i].split("=");
			String[] dest = list[half+i].split("=");
			//if(!src[0].substring(1).startsWith("1.1.1") && !src[0].substring(1).startsWith("2.2.2")
			//		&& !dest[0].substring(1).startsWith("1.1.1") && !dest[0].substring(1).startsWith("2.2.2")){
				session.put(src[0].substring(1)+"-"+dest[0].substring(1), src[1]);
			//}
				
			
			if(Integer.parseInt(src[0].substring(src[0].length()-1)) < 2){
				if(!red_ip.contains(src[0].substring(1)))
					red_ip.add(src[0].substring(1));
			}
		}
		
		Iterator<String> iterator = session.keySet().iterator();
		
		while(iterator.hasNext()){	
			
			String cc = iterator.next();
			//String cc2 = session.get(cc);
			result = result.concat(cc+"="+session.get(cc)+":");
			//if(i<9) result = result.concat(":");
				
		}
		
		
		
		String red = new String();
		
		Iterator<String> iterator2 =red_ip.iterator();
		int i = 0;
		while(iterator2.hasNext()){
			
			String ip = iterator2.next();
			red = red.concat(red_ip.get(i)+":");
			i++;
		}
		
		StringBuilder w7 = new StringBuilder();
		
		w7.append(date+",");
		w7.append(SIEMWebSocketMain.currentUser+",W7,vIPS,");
		w7.append(result.substring(0, result.length()-1)+","+"RED="+red);
		
		/*
		if(result.length() > 0){
			return result.substring(0, result.length()-1)+","+"RED="+red;
		}
		
		else return result;
		*/
		System.out.println("<W7> "+w7.toString());
		return w7.toString();
	}
	
	
}
