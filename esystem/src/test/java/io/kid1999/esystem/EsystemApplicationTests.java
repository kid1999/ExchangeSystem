//package io.kid1999.esystem;
//
//
//import io.kid1999.esystem.es.entry.GoodsEntry;
//import io.kid1999.esystem.es.repository.GoodsRepository;
//import io.kid1999.esystem.service.CommentService;
//import io.kid1999.esystem.task.MysqlToES;
//import io.kid1999.esystem.utils.EmailUtil;
//import io.kid1999.esystem.utils.TokenUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.client.indices.CreateIndexRequest;
//import org.elasticsearch.client.indices.CreateIndexResponse;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import javax.annotation.Resource;
//import java.io.IOException;
//import java.util.HashMap;
//
//@Slf4j
//@SpringBootTest
//class EsystemApplicationTests {
//
//	@Test
//	void contextLoads() {
//	}
//
//
//	@Autowired
//	private TokenUtil tokenUtil;
//
//	@Test
//	void Test(){
//		String token = tokenUtil.createToken("kid", 1L);
//		System.out.println(token);
//		tokenUtil.verify(token + "123");
//	}
//
//	@Autowired
//	private EmailUtil emailUtil;
//
//	@Test
//	void Test1(){
//		emailUtil.sendMailCode("1447250889@qq.com","test email","123das1d2a1d3a21d");
//	}
//
//
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//	@Test
//	void Test2(){
//		String encode = passwordEncoder.encode("1111");
//		System.out.println(encode);
//	}
//
//
//	@Autowired
//	private RestHighLevelClient restHighLevelClient;
//	@Test
//	void Test3() throws IOException {
//		HashMap<String,String> map = new HashMap<>();
//		map.put("name","kid");
//		map.put("password","dasdasd");
//		CreateIndexRequest request = new CreateIndexRequest("test");
//		CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
//		System.out.println(response);
//		System.out.println(response.index());
//	}
//
//
//	@Resource
//	private GoodsRepository goodsRepository;
//	@Test
//	void Tes5() throws IOException {
//		GoodsEntry g = new GoodsEntry();
//		g.setId(3L);
//		GoodsEntry entry = goodsRepository.save(g);
//		System.out.println(entry.toString());
//		System.out.println(entry.getId());
//	}
//
//
//	@Resource
//	private MysqlToES mysqlToES;
//	@Test
//	void Tes7() throws IOException {
//		mysqlToES.transDataToEs();
//	}
//
//	@Autowired
//	private MakeRandomUser makeRandomUser;
//
//	@Test
//	void makeuser(){
//		makeRandomUser.makeUser(100);
//	}
//
//
//	@Autowired
//	private MakeGoods makeGoods;
//
//	@Test
//	void makegoods(){
//		makeGoods.selectAll("dasda");
//	}
//
//	@Resource
//	private CommentService commentService;
//	@Test
//	void TestCommentByRedis(){
//		commentService.addMsg(5L);
//		log.error(commentService.getUsersMsg(5L) + "");
//		commentService.addMsg(5L);
//		commentService.addMsg(5L);
//		commentService.addMsg(5L);
//		commentService.addMsg(5L);
//		commentService.reduceMsg(5L,3);
//		log.error(commentService.getUsersMsg(5L) + "");
//
//	}
//
//}
