package com.search;

import com.search.bean.ProductEs;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.InetAddress;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodSearchApplicationTests {

	@Autowired
	private JestClient jestClient;

	@Test
	public void contextLoads() {
	}





	@Test
	public void suggest() throws Exception {
		TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
				.addTransportAddress(new InetSocketTransportAddress(
				InetAddress.getByName("192.168.100.17"), 9300 ));
//		TransportClient client = client();
		//2.1增加，创建一个商品
//		addProdect(client);
//		2.2查询，获取商品
		getProduct(client);
		//2.3修改，修改商品
		//updateProduct(client);
		//2.4删除,删除商品
		//deleteProduct(client);
		//3.关闭连接
		client.close();

	}

	public void jest() throws Exception{

//		WildcardQueryBuilder queryBuilder1 = QueryBuilders.wildcardQuery(
//				"name", "*jack*");//搜索名字中含有jack的文档
//
//		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//		boolQueryBuilder.must(queryBuilder1);
//
//		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//		searchSourceBuilder.query(boolQueryBuilder);

		QueryBuilder queryBuilder = QueryBuilders.boolQuery()
				.must(QueryBuilders.termQuery("user", "kimchy"));


		Search search = new Search.Builder(queryBuilder.toString())
				.addIndex(ProductEs.INDEX_NAME).addType(ProductEs.TYPE)
				.setParameter("from", 0)
				.setParameter("size", 10)
				.build();
		JestResult result = jestClient.execute(search);
	}

	public TransportClient client() throws Exception{
		Settings settings = Settings.builder()
				.put("cluster.name", "elasticsearch")
				.build();
		TransportClient client = new PreBuiltTransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.100.17"),9300));
		return client;


	}


	/**
	 * 创建一个商品
	 * @param client
	 * @throws IOException
	 */
	private static void addProdect(TransportClient client) throws IOException {
		IndexResponse response = client.prepareIndex("shop", "product", "1")
				.setSource(XContentFactory.jsonBuilder()
						.startObject()
						.field("name", "xwq001美菱(MELING)569升 对开门冰箱 一级能效 变频节能 0.1度精准控温 风冷无霜 玫瑰金BCD-569WPCX")
						.field("price", "99")
						.field("author","jack")
						.field("country", "us")
						.endObject())
				.get();
		System.out.println(response.toString());
	}
	/**
	 * 查看商品信息
	 * @param client
	 */
	private static void getProduct(TransportClient client) {
		GetResponse response = client.prepareGet("shop","product","1").get();
		System.out.println("======"+response.getSourceAsString());
	}
	/**
	 * 修改商品
	 * @param client
	 * @throws IOException
	 */
	private static void updateProduct(TransportClient client) throws IOException {
		UpdateResponse response = client.prepareUpdate("shop","product","1")
				.setDoc(XContentFactory.jsonBuilder()
						.startObject()
						.field("price", "9.9")
						.endObject())
				.get();
		System.out.println(response.toString());
	}
	/**
	 * 删除 一个商品
	 * @param client
	 */
	private static void deleteProduct(TransportClient client) {
		DeleteResponse response = client.prepareDelete("shop", "product", "1").get();
		System.out.println(response.toString());
	}

}
