package io.kid1999.esystem;

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

	@Test
	void Test1(){

	}


}
