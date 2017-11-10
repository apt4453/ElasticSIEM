package com.etri.main;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.ConstantScoreQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class ElasticSearchConnector {
	
	private int pageNum;
	private int pageSize;
	private String type;
	private String index;
	
	public ElasticSearchConnector(int pageNum, int pageSize, String index, String type) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.index = index;
		this.type = type;
		
	
	}
	
	//Create Connection	
	public TransportClient connect(String clusterName) throws UnknownHostException {
		Settings settings = Settings.builder().put("cluster.name", clusterName).build();
		
		 TransportClient client = new PreBuiltTransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("172.16.202.104"), 9301))
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("172.16.202.104"), 9301))
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("172.16.202.104"), 9302))
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("172.16.202.104"), 9303))
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("172.16.202.104"), 9304))
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("172.16.202.105"), 9300))
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("172.16.202.105"), 9301))
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("172.16.202.105"), 9302))
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("172.16.202.105"), 9303))
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("172.16.202.105"), 9304));
		
							
		
		return client;
	}
	
	
	//Search Statistics Data
	public SearchResponse searchData(TransportClient client, String time_period) throws UnknownHostException, ParseException {
		//Make Query
		BoolQueryBuilder bq = QueryBuilders.boolQuery();
		ConstantScoreQueryBuilder constantScoreQuery = QueryBuilders.constantScoreQuery(bq);
		// List<Map> 형식으로 결과 저장
		Map resultMap = new HashMap<String, String>();
		SearchRequestBuilder searchBuilder = client.prepareSearch(index)
				.setTypes(type)
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(constantScoreQuery)
				.setFrom(((pageNum - 1) * pageSize)) 	
				.setSize(pageSize) // 한번에 보여줄 데이터의 양
				.setExplain(true);
		
		bq.must(QueryBuilders.rangeQuery("log_init_date").from(time_period).format("yyyy-MM-dd HH:mm:ss").includeLower(true).includeUpper(true));
		SearchResponse response = searchBuilder.execute().actionGet();
		return response;
	}
	
	//Search Statistics Data
	public SearchResponse searchData(TransportClient client, String time_period, String name, String value) throws UnknownHostException, ParseException {
		//Make Query
		BoolQueryBuilder bq = QueryBuilders.boolQuery();
		ConstantScoreQueryBuilder constantScoreQuery = QueryBuilders.constantScoreQuery(bq);			
		
		// List<Map> 형식으로 결과 저장
		Map resultMap = new HashMap<String, String>();
		SearchRequestBuilder searchBuilder = client.prepareSearch(index)
				.setTypes(type)
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(constantScoreQuery)
				.setFrom(((pageNum - 1) * pageSize)) 	
				.setSize(pageSize) // 한번에 보여줄 데이터의 양
				.setExplain(true);
			
		bq.must(QueryBuilders.rangeQuery("log_init_date").from(time_period).format("yyyy-MM-dd HH:mm:ss").includeLower(true).includeUpper(true));
		bq.must(QueryBuilders.termQuery(name, value));
		SearchResponse response = searchBuilder.execute().actionGet();
		return response;
	}

	public SearchResponse searchDataPeriod(TransportClient client, String startTime, String endTime) throws UnknownHostException, ParseException {
		// TODO Auto-generated method stub
		//Make Query
		BoolQueryBuilder bq = QueryBuilders.boolQuery();
		ConstantScoreQueryBuilder constantScoreQuery = QueryBuilders.constantScoreQuery(bq);
		// List<Map> 형식으로 결과 저장
		Map resultMap = new HashMap<String, String>();
		SearchRequestBuilder searchBuilder = client.prepareSearch(index)
				.setTypes(type)
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(constantScoreQuery)
				.setFrom(((pageNum - 1) * pageSize)) 	
				.setSize(pageSize) // 한번에 보여줄 데이터의 양
				.setExplain(true);
		
		bq.must(QueryBuilders.rangeQuery("log_init_date").from(startTime).format("yyyy-MM-dd HH:mm:ss").includeLower(true).includeUpper(true).to(endTime).format("yyyy-MM-dd HH:mm:ss").includeLower(true).includeUpper(true));
		//bq.must(QueryBuilders.rangeQuery("log_init_date").to(endTime).format("yyyy-MM-dd HH:mm:ss").includeLower(true).includeUpper(true));
		
		SearchResponse response = searchBuilder.execute().actionGet();
		return response;
	}
	
	
	public SearchResponse searchDataLongterm(TransportClient client, String startTime, String endTime) throws UnknownHostException, ParseException {
		// TODO Auto-generated method stub
		//Make Query
		BoolQueryBuilder bq = QueryBuilders.boolQuery();
		ConstantScoreQueryBuilder constantScoreQuery = QueryBuilders.constantScoreQuery(bq);
		// List<Map> 형식으로 결과 저장
		Map resultMap = new HashMap<String, String>();
		SearchRequestBuilder searchBuilder = client.prepareSearch(index)
				//.setTypes(type)
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(constantScoreQuery)
				.setFrom(((pageNum - 1) * pageSize)) 	
				.setSize(pageSize) // 한번에 보여줄 데이터의 양
				.setExplain(true);
		
		bq.must(QueryBuilders.rangeQuery("date").from(startTime).format("yyyy-MM-dd HH:mm:ss").includeLower(true).includeUpper(true).to(endTime).format("yyyy-MM-dd HH:mm:ss").includeLower(true).includeUpper(true));
		//bq.must(QueryBuilders.rangeQuery("log_init_date").to(endTime).format("yyyy-MM-dd HH:mm:ss").includeLower(true).includeUpper(true));
		
		SearchResponse response = searchBuilder.execute().actionGet();
		return response;
	}

}
