package io.kid1999.esystem;


import io.kid1999.esystem.es.entry.GoodsEntry;
import io.kid1999.esystem.es.repository.GoodsRepository;
import io.kid1999.esystem.utils.ESUtil;
import io.kid1999.esystem.utils.EmailUtil;
import io.kid1999.esystem.utils.TokenUtil;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class EsystemApplicationTests {

	@Test
	void contextLoads() {
	}


	@Autowired
	private TokenUtil tokenUtil;

	@Test
	void Test(){
		String token = tokenUtil.createToken("kid", 1L);
		System.out.println(token);
		tokenUtil.verify(token + "123");
	}

	@Autowired
	private EmailUtil emailUtil;

	@Test
	void Test1(){
		emailUtil.sendMailCode("1447250889@qq.com","test email","123das1d2a1d3a21d");
	}


	@Autowired
	private PasswordEncoder passwordEncoder;
	@Test
	void Test2(){
		String encode = passwordEncoder.encode("1111");
		System.out.println(encode);
	}


	@Autowired
	private RestHighLevelClient ESClient;
	@Test
	void Test3() throws IOException {
		HashMap<String,String> map = new HashMap<>();
		map.put("name","kid");
		map.put("password","dasdasd");
		CreateIndexRequest request = new CreateIndexRequest("test");
		CreateIndexResponse response = ESClient.indices().create(request, RequestOptions.DEFAULT);
		System.out.println(response);
		System.out.println(response.index());
	}


	@Resource
	private ESUtil esUtil;
	@Test
	void Tes4() throws IOException {
		HashMap<String,String> map = new HashMap<>();
		map.put("name","kid");
		map.put("password","dasda");
		esUtil.createDocument("test",map);
	}


	@Resource
	private GoodsRepository goodsRepository;
	@Test
	void Tes5() throws IOException {
		GoodsEntry g = new GoodsEntry();
		g.setId(3L);
		g.setName("kid123");
		g.setPassword("aaaaa");
		GoodsEntry entry = goodsRepository.save(g);
		System.out.println(entry.toString());
		System.out.println(entry.getId());
	}

	@Test
	void Tes6() throws IOException {
		List<GoodsEntry> kid = goodsRepository.findAllByNameLike("kid");
		System.out.println(kid);
	}


}
