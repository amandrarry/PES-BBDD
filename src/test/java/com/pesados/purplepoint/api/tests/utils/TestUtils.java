package com.pesados.purplepoint.api.tests.utils;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TestUtils {
	
	//May be done using this @Value("${firebase.firebaseHeaderName}")
	public static String firebaseHeaderName = "X-Authorization-Firebase";
	
	// My be done using this @Value("${firebase.debugToken}")
	public static String firebaseToken = "f2EJYEQeYyYq-v2ubvL7x5:APA91bFam-no_lk9-kryCZol_dXDEtRjyd_iyAORuLDuLgLmyblUhYE9sYV1Prj4ohxnt6-EM_tDBVOkhnV08e2szqCGjNBRap5vnRwzBVf0iCMzlCphZiAWCkRWiDx0pB71dZEj2Ej5";
	
	@Test
	static public String doLogin(MockMvc mockMvc) throws Exception {
		JSONObject user = new JSONObject();
		user.put("email", "isma@gmail.com");
		user.put("password", "1234");

		MvcResult response = mockMvc.perform(MockMvcRequestBuilders
				.post("/api/v1/users/login")
				.contentType("application/json")
				.header(TestUtils.firebaseHeaderName, TestUtils.firebaseToken)
				.content(user.toString()))
				.andExpect(status().isOk())
				.andReturn();

		JSONObject respUser = new JSONObject(response.getResponse().getContentAsString());
		return ((String) respUser.get("token"));
	}
}
