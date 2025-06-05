package com.example.IS216_Dlegent;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Is216DlegentApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void createOrder_amountNotZero(){
		Map<String, Object> orderRequest = new HashMap<>();
		orderRequest.put("amount",1000000);
		Long idDatPhong = 1L;
	}
}
