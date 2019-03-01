package com.kkwrite.demo.elasticsearch.client.rest.high;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Before;
import org.junit.Test;

/** 
 * 类说明 
 *
 * @author Soosky Wang
 * @date 2018年11月8日 下午5:47:35 
 * @version 1.0.0
 */
public class DemoCreateIndexTest {

	RestHighLevelClient client;

	@Before
	public void initClient() {
		client = new RestHighLevelClient(
				RestClient.builder(
						new HttpHost("172.18.231.21", 9200, "http"),
						new HttpHost("172.18.231.56", 9200, "http"),
						new HttpHost("172.18.231.63", 9200, "http")));
		
		assertNotNull(client);
	}
	
	@Test
	public void createIndex() {
		try {
			// 1、创建索引request
			CreateIndexRequest request = new CreateIndexRequest("demo-index");

			// 2、设置索引的settings
			request.settings(Settings.builder()
					.put("index.number_of_shards", 5) // 分片数
					.put("index.number_of_replicas", 2) // 副本数
					//.put("analysis.analyzer.default.tokenizer", "ik_smart") // 默认分词器
			);

			// 3、设置索引的mappings
			request.mapping("_doc",
					"  {\n" +
				    "    \"properties\": {\n" +
				    "        \"message\": {\n" +
				    "          \"type\": \"text\"\n" +
				    "        }\n" +
				    "    }\n" +
				    "  }",
					XContentType.JSON);

			// 4、 设置索引的别名
			request.alias(new Alias("demo"));

			// 5、 发送请求
			// 5.1 同步方式发送请求
			CreateIndexResponse createIndexResponse = client.indices().create(request);

			// 6、处理响应
			boolean acknowledged = createIndexResponse.isAcknowledged();
			//boolean shardsAcknowledged = createIndexResponse.isShardsAcknowledged();
			System.out.println("acknowledged = " + acknowledged);
			//System.out.println("shardsAcknowledged = " + shardsAcknowledged);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
