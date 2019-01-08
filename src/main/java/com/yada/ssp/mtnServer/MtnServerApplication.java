package com.yada.ssp.mtnServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJms
@EnableScheduling
@EnableAsync
public class MtnServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MtnServerApplication.class, args);
	}

}
