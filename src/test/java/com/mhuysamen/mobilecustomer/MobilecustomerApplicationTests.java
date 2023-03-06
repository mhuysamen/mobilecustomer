package com.mhuysamen.mobilecustomer;

import javax.activation.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class MobilecustomerApplicationTests {

	@MockBean 
	private DataSource dataSource;

	@Test
	void contextLoads() {
	}


}
