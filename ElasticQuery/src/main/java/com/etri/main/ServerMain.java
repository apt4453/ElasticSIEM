package com.etri.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.SearchHits;

public class ServerMain {
	
	ElasticSearchConnector connector;
	
	TransportClient client;
	
	HashMap<String, Integer> eventIdList;
	ArrayList<String> eventList;
	
	public ServerMain(){
		try {
			//pageNum, pageSize, indexName, type
			this.connector = new ElasticSearchConnector(1, 2000, "etri_"+Timer.getToday()+"_log", "ips");
			//this.connector = new ElasticSearchConnector(1, 2000, "etri_"+Timer.getYesterday()+"_log", "ips");


			//clusterName
			this.client = connector.connect("etri-cluster");
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		

		eventIdList = new HashMap<String, Integer>();
		eventList = new ArrayList<String>();
	}
	
	public ServerMain(String indexName) {
		try {
			//pageNum, pageSize, indexName, type
			//this.connector = new ElasticSearchConnector(1, 2000, "etri_"+Timer.getToday()+"_log", "ips");
			this.connector = new ElasticSearchConnector(1, 2000, indexName, null);


			//clusterName
			this.client = connector.connect("etri-cluster");
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		

		eventIdList = new HashMap<String, Integer>();
		eventList = new ArrayList<String>();
		
	}
	
	public void queryStatement(String baseTime){
		
		try{
			SearchResponse response = connector.searchData(client, baseTime);
			//SearchResponse response = connector.searchData(client, "srcip", "8.8.8.8");
			//Search Result
			SearchHits hits = response.getHits();
			
			System.out.println("RESPONSE : "+response.toString());
			System.out.println("RESPONSE hits: "+response.getHits().totalHits);
			for (SearchHit searchHit : hits) {
				Map<String, Object> fields = searchHit.getSource();
				//searchHit.
				/*
				System.out.println("LENGTH : "+fields.size());
				Iterator<String> keys = fields.keySet().iterator();
				
				//System.out.println(keys.);
				while(keys.hasNext()){
					
					System.out.println(keys.next());
				}
				*/
				String time = fields.get("indexTime").toString();
				String event = fields.get("msg").toString();
				String srcip = fields.get("srcip").toString();
				System.out.println(time +"  ,  "+event+"  ,  "+srcip);
		}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void getStatGroup(String baseTime){
		
		HashMap<Integer, Integer> currentStat = new HashMap<Integer,Integer>();
		
		try{
			SearchResponse response = connector.searchData(client, baseTime);
			//SearchResponse response = connector.searchData(client, "srcip", "8.8.8.8");
			//Search Result
			SearchHits hits = response.getHits();
			
			System.out.println("RESPONSE : "+response.toString());
			System.out.println("RESPONSE hits: "+response.getHits().totalHits);
			for (SearchHit searchHit : hits) {
				Map<String, Object> fields = searchHit.getSource();
				//searchHit.
				/*
				System.out.println("LENGTH : "+fields.size());
				Iterator<String> keys = fields.keySet().iterator();
				
				//System.out.println(keys.);
				while(keys.hasNext()){
					
					System.out.println(keys.next());
				}
				*/
				String time = fields.get("indexTime").toString();
				String event = fields.get("msg").toString();
				String srcip = fields.get("srcip").toString();
				System.out.println(time +"  ,  "+event+"  ,  "+srcip);
				
				//new event
				if(!eventIdList.containsKey(event)){
					Integer e_id = eventIdList.size()+1;
					eventIdList.put(event, e_id);
					
				}
				
				if(!currentStat.containsKey(eventIdList.get(event))){
					
					currentStat.put(eventIdList.get(event), 1);
				}
				
				else {
					currentStat.put(eventIdList.get(event), currentStat.get(eventIdList.get(event))+1);
				}
				
				
				
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		Set<Integer> keys = currentStat.keySet();  //get all keys
		for(Integer i: keys)
		{
		    System.out.println("e-"+i+ " = "+currentStat.get(i));
		}
		
		System.out.println("Total Event List : "+eventIdList.size());
	}
	

	public void queryStatement(String baseTime, String name, String value){
	
		HashMap<Integer, Integer> currentStat = new HashMap<Integer,Integer>();
		
		
		try{
			//SearchResponse response = connector.searchData(client);
			SearchResponse response = connector.searchData(client, baseTime, name, value);
			//Search Result
			SearchHits hits = response.getHits();
			
			System.out.println("RESPONSE : "+response.toString());
			System.out.println("RESPONSE hits: "+response.getHits().totalHits);
			for (SearchHit searchHit : hits) {
				Map<String, Object> fields = searchHit.getSource();
				//searchHit.
				/*
				System.out.println("LENGTH : "+fields.size());
				Iterator<String> keys = fields.keySet().iterator();
				
				//System.out.println(keys.);
				while(keys.hasNext()){
					
					System.out.println(keys.next());
				}
				*/
				String time = fields.get("indexTime").toString();
				String event = fields.get("msg").toString();
				String srcip = fields.get("srcip").toString();
				System.out.println(time +"  ,  "+event+"  ,  "+srcip);
				
				//new event
				if(!eventIdList.containsKey(event)){
					Integer e_id = eventIdList.size()+1;
					eventIdList.put(event, e_id);
					
				}
				
				if(!currentStat.containsKey(eventIdList.get(event))){
					
					currentStat.put(eventIdList.get(event), 1);
				}
				
				else {
					currentStat.put(eventIdList.get(event), currentStat.get(eventIdList.get(event))+1);
				}
				
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Set<Integer> keys = currentStat.keySet();  //get all keys
		for(Integer i: keys)
		{
		    System.out.println("e-"+i+"= "+currentStat.get(i));
		}
	}
	
	
	public void queryStatementPeriod(String startTime, String endTime){
		
		HashMap<Integer, Integer> currentStat = new HashMap<Integer,Integer>();
		
		
		try{
			//SearchResponse response = connector.searchData(client);
			SearchResponse response = connector.searchDataPeriod(client, startTime, endTime);
			//Search Result
			SearchHits hits = response.getHits();
			
			//System.out.println("RESPONSE : "+response.toString());
			System.out.println("RESPONSE hits: "+response.getHits().totalHits);
			for (SearchHit searchHit : hits) {
				Map<String, Object> fields = searchHit.getSource();
				//searchHit.
				/*
				System.out.println("LENGTH : "+fields.size());
				Iterator<String> keys = fields.keySet().iterator();
				
				//System.out.println(keys.);
				while(keys.hasNext()){
					
					System.out.println(keys.next());
				}
				*/
				String time = fields.get("indexTime").toString();
				String event = fields.get("msg").toString();
				String srcip = fields.get("srcip").toString();
				System.out.println(time +"  ,  "+event+"  ,  "+srcip);
				
				
				//new event
				if(!eventIdList.containsKey(event)){
					Integer e_id = eventIdList.size()+1;
					eventIdList.put(event, e_id);
					
				}
				
				if(!currentStat.containsKey(eventIdList.get(event))){
					
					currentStat.put(eventIdList.get(event), 1);
				}
				
				else {
					currentStat.put(eventIdList.get(event), currentStat.get(eventIdList.get(event))+1);
				}
				
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Set<Integer> keys = currentStat.keySet();  //get all keys
		for(Integer i: keys)
		{
		    System.out.println("e-"+i+ " = "+currentStat.get(i));
		}
		
		System.out.println("Total Event List : "+eventIdList.size());
	
	}
	
	
	public void queryStatementLongterm(String startTime, String endTime){
		
		HashMap<Integer, Integer> currentStat = new HashMap<Integer,Integer>();
		
		HashMap<String, Integer> srclist = new HashMap<String, Integer>();
		HashMap<String, Integer> dstlist = new HashMap<String, Integer>();
		
		String time = new String();
		StringBuilder elist = new StringBuilder();
		
		StringBuilder srcipList = new StringBuilder();
		StringBuilder dstipList = new StringBuilder();
		
		StringBuilder vsiem_msg = new StringBuilder();
		
		long hitcount = 0;
		
		try{
			//SearchResponse response = connector.searchData(client);
			SearchResponse response = connector.searchDataLongterm(client, startTime, endTime);
			//Search Result
			SearchHits hits = response.getHits();
			
			//System.out.println("RESPONSE : "+response.toString());
			hitcount = response.getHits().totalHits;
			
			System.out.println("RESPONSE hits: "+hitcount);
			
			for (SearchHit searchHit : hits) {
				Map<String, Object> fields = searchHit.getSource();
				//searchHit.
				/*
				System.out.println("LENGTH : "+fields.size());
				Iterator<String> keys = fields.keySet().iterator();
				
				//System.out.println(keys.);
				while(keys.hasNext()){
					
					System.out.println(keys.next());
				}
				*/
				if(time.length() == 0){
					time = fields.get("date").toString();
				}
				String event = fields.get("Event_name").toString();
				String srcip = fields.get("Src_ip").toString();
				String dstip = fields.get("Dst_ip").toString();
				//System.out.println(time +"  ,  "+event+"  ,  "+srcip+"  ,  "+dstip);
				
				
				//new event
				if(!eventIdList.containsKey(event)){
					Integer e_id = eventIdList.size()+1;
					eventIdList.put(event, e_id);
					
				}
				
				if(!currentStat.containsKey(eventIdList.get(event))){
					
					currentStat.put(eventIdList.get(event), 1);
				}
				
				else {
					currentStat.put(eventIdList.get(event), currentStat.get(eventIdList.get(event))+1);
				}
				
				if(!srclist.containsKey(srcip)){
					srclist.put(srcip, 1);
				}else{
					srclist.put(srcip, srclist.get(srcip)+1);
				}
				
				if(!dstlist.containsKey(dstip)){
					dstlist.put(dstip, 1);
				}else{
					dstlist.put(dstip, dstlist.get(dstip)+1);
				}
				
				//srcipList.append("s"+srcip+"=")
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Set<Integer> keys = currentStat.keySet();  //get all keys
		for(Integer i: keys)
		{
		    System.out.print("e-"+i+ " = "+currentStat.get(i)+":");
		    elist.append("e"+i+"="+currentStat.get(i)+":");
		}
		
		
		System.out.println("");
		System.out.println("Total Event List : "+eventIdList.size());
	
		Set<String> skey = srclist.keySet();
		for(String i: skey){
			srcipList.append("s"+i+"="+srclist.get(i)+":");
			//vsiem_msg.append(
		}
		
	
		System.out.println(srcipList);
		
		Set<String> dkey = dstlist.keySet();
		for(String i: dkey){
			dstipList.append("d"+i+"="+dstlist.get(i)+":");
		}
		//System.out.println(dstipList);
		
		if(elist.length() > 0){
			try {
				vsiem_msg.append("1,"+Timer.convertFormat(time)+",");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			vsiem_msg.append(elist.substring(0, elist.length()-1));
			vsiem_msg.append(",");
			
			vsiem_msg.append(srcipList.substring(0, srcipList.length()-1));
			vsiem_msg.append(",");
			
			
			vsiem_msg.append(dstipList.substring(0, dstipList.length()-1));
			vsiem_msg.append(",");
			
			vsiem_msg.append(hitcount);
			vSiemConnector.send(vsiem_msg.toString());
			
			String result = null;
			try {
				result = vSiemConnector.in.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("vSiem: Receiving...");
			System.out.println("<RESULT> "+result);
			
			String w1 = VisualData.makeDataW_1(time, result, hitcount);
			DataW.sendWSocket(w1);
			
			String w7 = VisualData.makeDataW_7(time, srcipList.substring(0, srcipList.length()-1)+":"+dstipList.substring(0, dstipList.length()-1));
			DataW.sendWSocket(w7);
			
			String w8 = VisualData.makeDataW_8(result, hitcount);
			DataW.sendWSocket(w8);
			
			
		}
		
	}
	
	
	private void initEventList(String FILE_PATH) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		BufferedReader in_file = new BufferedReader(new FileReader(FILE_PATH));
		
        int t_count = 0; 
        String dataline;
        
        while ((dataline = in_file.readLine()) != null)
        {
           	String[] str = dataline.split(",");
			t_count++;
			eventIdList.put(str[1].substring(1, str[1].length()-1), Integer.parseInt(str[0]));
			System.out.println("["+str[0]+"]"+str[1].substring(1, str[1].length()-1));
        	
        	
        }
	}
	
	public static void main(String[] args) {
		
		System.out.println("Hello. Connect to ElasticSearch Node");
		
		String event_list_file = new String("/home/securator/vSiem/euk2_event_table.csv");
		
			
		ServerMain es = new ServerMain("etri_20171108_test");
		
		new vSiemConnector(new String("129.254.186.32"),9099);
		
		new SIEMWebSocketMain();
		
		try {
			es.initEventList(event_list_file);
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for(int i=0 ; i<8640 ; i++){
			System.out.println("QUERY TIME  : " + Timer.getTenMinuteAgoTime());
			//es.queryStatement(Timer.getOneMinuteAgoTime());
			//es.getStatGroup(Timer.getTenMinuteAgoTime());
			
			String start = Timer.getBaseTime(2016, 12, 1, 0, 0, 0, i * 10);
			
			String end = Timer.getBaseTime(2016, 12, 1, 0, 0, 0 , (i+1)*10);
			
			//es.queryStatementPeriod(start, end);
			es.queryStatementLongterm(start, end);
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	
}


