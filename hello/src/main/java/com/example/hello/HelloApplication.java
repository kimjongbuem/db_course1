package com.example.hello;

import java.sql.SQLException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.example.hello.domain.Member;
import com.example.hello.provider.ApplicationContextProvider;
import com.example.hello.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
@ComponentScan(basePackages = "com.example.hello")
public class HelloApplication {

	public static void main(String[] args) {
		/* Crtl + F5 START */
		System.out.println("-----HelloApplication start-----");

		SpringApplication.run(HelloApplication.class, args);

		ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();

		MemberService memberService = (MemberService) applicationContext.getBean("memberService");

		try {
			log.info("member size : [{}] es", memberService.find().size());
			memberService.save(Member.builder().memberId("created1").build());
			memberService.save(Member.builder().memberId("created1").build());
			memberService.accountTransfer("newId1", "newId2", 1000);
			memberService.accountTransfer("newId1", "newId2", 5000);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
