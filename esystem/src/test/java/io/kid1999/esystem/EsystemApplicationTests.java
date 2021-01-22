package io.kid1999.esystem;

import io.kid1999.esystem.utils.EmailUtil;
import io.kid1999.esystem.utils.TokenUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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


}
