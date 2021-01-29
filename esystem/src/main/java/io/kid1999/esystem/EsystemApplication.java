package io.kid1999.esystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author kid1999
 */
@EnableScheduling
@SpringBootApplication
@EnableCaching
public class EsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EsystemApplication.class, args);
	}

}
