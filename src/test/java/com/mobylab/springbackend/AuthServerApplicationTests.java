package com.mobylab.springbackend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
@MockBean(JavaMailSender.class)
class AuthServerApplicationTests {

	@Test
	void contextLoads() {
	}

}
