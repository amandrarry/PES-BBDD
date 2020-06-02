package com.pesados.purplepoint.api.tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.pesados.purplepoint.api.controller.AlarmController;
import com.pesados.purplepoint.api.controller.ImageController;
import com.pesados.purplepoint.api.controller.MapController;
import com.pesados.purplepoint.api.controller.UserController;

@SpringBootTest
class PurplePointApplicationTests {

	@Autowired
	private UserController ctl1;
	
	@Autowired
	private AlarmController ctl2;
	
	@Autowired
	private ImageController ctl3;
		
	@Autowired
	private MapController ctl5;
	
	@Test
	void contextLoads() {
		assert(ctl1 != null);
		assert(ctl2 != null);
		assert(ctl3 != null);
		assert(ctl5 != null);
	}
}
