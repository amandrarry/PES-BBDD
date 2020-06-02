package com.pesados.purplepoint.api.tests.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import com.pesados.purplepoint.api.tests.utils.TestUtils;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MapControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void shouldReturnAllReports() throws Exception {
		String token = TestUtils.doLogin(this.mockMvc);
		
		this.mockMvc.perform(get("/api/v1/map")
				.header("Authorization", token)
				.header(TestUtils.firebaseHeaderName, TestUtils.firebaseToken)
				.contentType("application/json"))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	public void shouldReturnOneReport() throws Exception {
		String token = TestUtils.doLogin(this.mockMvc);
		
		this.mockMvc.perform(get("/api/v1/map/1").header("Authorization", token)
				.contentType("application/json")
				.header(TestUtils.firebaseHeaderName, TestUtils.firebaseToken))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	public void shouldCreateOneReport() throws Exception {
		String token = TestUtils.doLogin(this.mockMvc);
		
		
		JSONObject loc = new JSONObject();
		loc.put("latitude", 1234);
		loc.put("longitude", 364);
		
		JSONObject user = new JSONObject();
		user.put("email", "ismaaaaa@gmail.com");
		user.put("password", "123aaaa4");
		
		JSONObject test = new JSONObject();
		test.put("location", loc);
		test.put("user",  user);
		
		
		this.mockMvc.perform(post("/api/v1/map").header("Authorization", token)
				.contentType("application/json")
				.header(TestUtils.firebaseHeaderName, TestUtils.firebaseToken)
				.content(test.toString()))
				.andDo(print())
				.andExpect(status().isOk());
	}

}
