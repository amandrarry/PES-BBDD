package com.pesados.purplepoint.api.tests.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.pesados.purplepoint.api.controller.AlarmController;
import com.pesados.purplepoint.api.controller.LoginSystem;
import com.pesados.purplepoint.api.model.alarm.Alarm;
import com.pesados.purplepoint.api.model.alarm.AlarmService;
import com.pesados.purplepoint.api.model.device.Device;
import com.pesados.purplepoint.api.model.device.DeviceService;
import com.pesados.purplepoint.api.model.firebase.PushNotificationService;
import com.pesados.purplepoint.api.model.location.Location;
import com.pesados.purplepoint.api.model.user.User;
import com.pesados.purplepoint.api.tests.utils.TestUtils;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AlarmControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private LoginSystem loginSystem;

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private AlarmService alarmService;

	@Autowired
	private PushNotificationService pushNotificationService;
	
	@Test
	public void shouldReturnNewLocation() throws Exception {
		JSONObject location = new JSONObject();
		location.put("latitude", 41.447612);
		location.put("longitude", 2.224417);
		location.put("accuracy", 100);
		location.put("timestamp", 0);

		JSONObject alarm = new JSONObject();

		alarm.put("username", "amandi");
		alarm.put("deviceToken", TestUtils.firebaseToken);
		alarm.put("location", location);

		String token = TestUtils.doLogin(this.mockMvc);

		this.mockMvc.perform(post("/api/v1/alarms/create").header("Authorization", token)
				.contentType("application/json")
				.header(TestUtils.firebaseHeaderName, TestUtils.firebaseToken)
				.content(alarm.toString()))
				.andDo(print())
				.andExpect(status().isOk());
	}


	@Test
	public void shouldReturnAlarmsinfo() throws Exception {
		JSONObject location = new JSONObject();
		location.put("latitude", 41.447612);
		location.put("longitude", 2.224417);
		location.put("accuracy", 100);
		location.put("timestamp", 0);

		JSONObject alarm = new JSONObject();

		alarm.put("username", "isma");
		alarm.put("deviceToken", TestUtils.firebaseToken);
		alarm.put("location", location);

		String token = TestUtils.doLogin(this.mockMvc);
		
		this.mockMvc.perform(post("/api/v1/alarms/create").header("Authorization", token)
				.contentType("application/json")
				.header(TestUtils.firebaseHeaderName, TestUtils.firebaseToken)
				.content(alarm.toString()))
				.andDo(print())
				.andExpect(status().isOk());

		/*
		 * tret del test perque falla amb cada nova feature y me tiene ...
		 * amandi: isma eres el amo
		 * isma: jajaja lots of love
				String user_bd = "{\"id\":1,\"name\":\"test\",\"username\":\"test1\",\"email\":\"isma@gmail.com\",\"password\":\"1234\",\"gender\":\"others\",\"token\":\"" +
					token +
					"\",\"helpedUsers\":0,\"markedSpots\":0}";
		*/
		this.mockMvc.perform(get("/api/v1/alarms")
				.header("Authorization",token)
				.header(TestUtils.firebaseHeaderName, TestUtils.firebaseToken))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().is(200));
	}

	@Test
	public void shouldReturnAlarmbyId() throws Exception {
		JSONObject location = new JSONObject();
		location.put("latitude", 41.447612);
		location.put("longitude", 2.224417);
		location.put("accuracy", 100);
		location.put("timestamp", 0);

		JSONObject alarm = new JSONObject();
		alarm.put("username", "franco");
		alarm.put("deviceToken", TestUtils.firebaseToken);
		alarm.put("location", location);
		
		String token = TestUtils.doLogin(this.mockMvc);

		MvcResult responsecreate = this.mockMvc.perform(post("/api/v1/alarms/create").header("Authorization", token)
				.contentType("application/json")
				.header(TestUtils.firebaseHeaderName, TestUtils.firebaseToken)
				.content(alarm.toString()))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();

		JSONObject respcreate = new JSONObject(responsecreate.getResponse().getContentAsString());
		String id = Integer.toString((int) respcreate.get("alarmId"));

		this.mockMvc.perform(get("/api/v1/alarms/"+id).header("Authorization", token)
				.contentType("application/json")
				.header(TestUtils.firebaseHeaderName, TestUtils.firebaseToken)
				.content(""))
				.andDo(print())
				.andExpect(status().isOk());
	}

	public void shouldReturnLocationbyUsername() throws Exception {
		JSONObject location = new JSONObject();
		location.put("latitude", 41.447612);
		location.put("longitude", 2.224417);
		location.put("accuracy", 100);
		location.put("timestamp", 0);

		JSONObject alarm = new JSONObject();

		alarm.put("username", "julia");
		alarm.put("deviceToken", TestUtils.firebaseToken);
		alarm.put("location", location);

		String token = TestUtils.doLogin(this.mockMvc);

		this.mockMvc.perform(post("/api/v1/alarms/create").header("Authorization", token)
				.contentType("application/json")
				.header(TestUtils.firebaseHeaderName, TestUtils.firebaseToken)
				.content(alarm.toString()))
				.andDo(print())
				.andExpect(status().isOk());

		this.mockMvc.perform(get("/api/v1/alarms/julia").header("Authorization", token)
				.contentType("application/json")
				.header(TestUtils.firebaseHeaderName, TestUtils.firebaseToken)
				.content(""))
				.andDo(print())
				.andExpect(status().isOk());


	}
	
	@Test
	public void shouldDeleteUser() throws Exception {
		JSONObject location = new JSONObject();
		location.put("latitude", 41.447612);
		location.put("longitude", 2.224417);
		location.put("accuracy", 100);
		location.put("timestamp", 0);

		JSONObject alarm = new JSONObject();
		alarm.put("username", "adri");
		alarm.put("deviceToken", TestUtils.firebaseToken);
		alarm.put("location", location);

		String token = TestUtils.doLogin(this.mockMvc);

		MvcResult responsecreate = this.mockMvc.perform(post("/api/v1/alarms/create").header("Authorization", token)
				.contentType("application/json")
				.header(TestUtils.firebaseHeaderName, TestUtils.firebaseToken)
				.content(alarm.toString()))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();

		JSONObject respcreate = new JSONObject(responsecreate.getResponse().getContentAsString());
		String id = Integer.toString((int) respcreate.get("alarmId"));

		this.mockMvc.perform(delete("/api/v1/alarms/delete/"+id).header("Authorization", token)
				.contentType("application/json")
				.header(TestUtils.firebaseHeaderName, TestUtils.firebaseToken)
				.content(""))
				.andDo(print())
				.andExpect(status().isOk());
	}

	// integration and findNearbyDevices (AlarmController) test
	@Test
	public void shouldReturnNearbyDevices() throws Exception {
		Location miCasa = new Location((float)41.447612, (float)2.224417, 100, 0);
		Location bar = new Location((float)41.447379, (float)2.226842, 100, 0);
		Location cancun = new Location((float)21.160510, (float)-86.842466, 100, 0);
		Location sarria = new Location((float)41.402899, (float)2.121561, 100, 0);

		User testUser = new User();
		Alarm stubAlarm = new Alarm("isma", "2", bar);

		List<Device> expectedResult = new ArrayList<Device>();
		Optional<Device> deviceOpt = deviceService.getDeviceByFirebaseToken(TestUtils.firebaseToken);
		expectedResult.add(deviceOpt.get());

		AlarmController alarmController = new AlarmController(this.alarmService, this.deviceService, this.pushNotificationService, this.loginSystem);

		List<Device> result = alarmController.findNearbyDevices(stubAlarm);

		Assert.assertEquals(expectedResult.get(0).getFirebaseToken(), result.get(0).getFirebaseToken());
	}
}
