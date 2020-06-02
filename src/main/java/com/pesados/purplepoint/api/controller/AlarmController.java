package com.pesados.purplepoint.api.controller;


import com.pesados.purplepoint.api.exception.AlarmNotFoundException;
import com.pesados.purplepoint.api.exception.UnauthorizedDeviceException;
import com.pesados.purplepoint.api.model.alarm.Alarm;
import com.pesados.purplepoint.api.model.alarm.AlarmService;
import com.pesados.purplepoint.api.model.device.Device;
import com.pesados.purplepoint.api.model.device.DeviceService;
import com.pesados.purplepoint.api.model.firebase.PushNotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class AlarmController {


	private final AlarmService alarmService;

	private final DeviceService deviceService;

	private final PushNotificationService pushNotificationService;

	private final LoginSystem loginSystem;

	@Autowired
	public AlarmController(AlarmService alarmService, 
		DeviceService deviceService, 
		PushNotificationService pushNotificationService,
		LoginSystem loginSystem) {
		this.alarmService = alarmService;
		this.deviceService = deviceService;
		this.pushNotificationService = pushNotificationService;
		this.loginSystem = loginSystem;
	}

	// Visibilidad Device
	// Creates a new alarm
	// Sends notifications to all nearby devices within a 500 meters radius
	@Operation(summary = "Create a new alarm",
			description = "Adds a new alarm to the database with the information provided. ", tags = { "alarms" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Alarm created",
					content = @Content(schema = @Schema(implementation = Alarm.class))),
			@ApiResponse(responseCode = "400", description = "Invalid input"),
			@ApiResponse(responseCode = "409", description = "Alarm already exists") })
	@PostMapping(value = "/alarms/create", consumes = { "application/json", "application/xml" })
	Alarm newAlarm(
			@Parameter(description="Alarm to add. Cannot null or empty.",
					required=true, schema=@Schema(implementation = Alarm.class))
			@Valid @RequestBody Alarm alarmNew
	) {	
		List<Device> nearbyDevices = findNearbyDevices(alarmNew);
		sendPushNotificationToDevices(nearbyDevices, alarmNew);
		return alarmService.saveAlarm(alarmNew);
	}

	private void sendPushNotificationToDevices(List<Device> devices, Alarm alarmNew) {
		/* franco es puta */
		List<String> registrationTokens = new ArrayList<>();

		for (int i = 0; i < devices.size(); ++i) {
			registrationTokens.add(devices.get(i).getFirebaseToken());
		}

		pushNotificationService.sendMulticastPushNotification(registrationTokens, alarmNew);
	}

	// Encuentra todos los devices que están cerca de la localización de la alarma
	// Se hace una query
	public List<Device> findNearbyDevices(Alarm alarm) {
		List<Device> allDevices = deviceService.getAll();
		List<Device> result = new ArrayList<>();
		// Parametros de la alarma
		String alarmToken = alarm.getDeviceToken();
		float alarmLatitude = alarm.getLocation().getLatitude();
		float alarmLongitude = alarm.getLocation().getLongitude();

		for (int i = 0; i < allDevices.size(); ++i) {
			// Parametros para el Device tratado
			float deviceLatitude = allDevices.get(i).getLocation().getLatitude();
			float deviceLongitude = allDevices.get(i).getLocation().getLongitude();
			String deviceToken = allDevices.get(i).getFirebaseToken();

			if (LocationController.isLocationInA500MeterRadius(alarmLatitude, alarmLongitude, deviceLatitude, deviceLongitude) && !alarmToken.equals(deviceToken))
			result.add(allDevices.get(i));
		}
		return result;
	}

	// Visibilidad User
	@Operation(summary = "Get All Alarms", description = "Get all the existing alarms", tags = {"alarms"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation",
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alarm.class))))})
	@GetMapping(value = "/alarms", produces = {"application/json", "application/xml"})
	List<Alarm> all(
		@RequestHeader("Authorization") String unformatedJWT
	) {
		if (this.loginSystem.checkLoggedIn(unformatedJWT)) {
			return alarmService.getAll();
		} else {
			throw new UnauthorizedDeviceException();
		}
	}

	// Visibilidad User
	@Operation(summary = "Get Alarm By ID", description = "Get an Alarm from an existing ID value you want to look up", tags = {"alarms"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation",
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alarm.class))))})
	@GetMapping(value = "/alarms/{id}", produces = {"application/json", "application/xml"})
	Alarm getbyId(
		@RequestHeader("Authorization") String unformatedJWT,
		@Parameter(description = "ID of the contact to search.", required = true)
		@PathVariable long id) {
		if (this.loginSystem.checkLoggedIn(unformatedJWT)) {
			return alarmService.getAlarmById(id).orElseThrow(() -> new AlarmNotFoundException(id));
		} else {
			throw new UnauthorizedDeviceException();
		}
	}

	// Visibilidad User
	@Operation(summary = "Get Alarm By Username", description = "Get an Alarm by its username", tags = {"alarms"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation",
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alarm.class))))})
	@GetMapping(value = "/alarms/username/{username}", produces = {"application/json", "application/xml"})
	Alarm getbyUsername(
		@RequestHeader("Authorization") String unformatedJWT,
		@Parameter(description = "username of the person who pressed the panic button.", required = true)
		@PathVariable String username){
		if (this.loginSystem.checkLoggedIn(unformatedJWT)) {
			return alarmService.getAlarmByUsername(username).orElseThrow(() -> new AlarmNotFoundException(username));
		} else {
			throw new UnauthorizedDeviceException();
		}
	}


	// Visibilidad User
	@Operation(summary = "Update an existing alarm by ID", description = "Update the username, Location, given the ID of an existing alarm", tags = {"alarms"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation"),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied"),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "404", description = "Alarm not found"),
			@ApiResponse(responseCode = "405", description = "Validation exception")})
	@PutMapping("/alarms/update/ {id}")
	Alarm replaceAlarmbyID(
		@RequestHeader("Authorization") String unformatedJWT,
		@Parameter(description = "New information for the alarm.", required = true)
		@RequestBody Alarm newAlarm,
		@Parameter(description = "id of the alarm to replace.", required = true)
		@PathVariable long id
	) {
		if (this.loginSystem.checkLoggedIn(unformatedJWT)) {
			return alarmService.getAlarmById(id)
				.map(alarm -> {
					alarm.setUsername(newAlarm.getUsername());
					alarm.setLocation(newAlarm.getLocation());
					return alarmService.saveAlarm((alarm));
				}).orElseGet(() -> {
					newAlarm.setAlarmId(id);
					return alarmService.saveAlarm(newAlarm);

				});	
		} else {
			throw new UnauthorizedDeviceException();
		}
	}

	// Visibilidad User
	@Operation(summary = "Delete an alarm", description = "Delete an existing alarm given its id", tags = { "alarms" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation"),
			@ApiResponse(responseCode = "404", description = "Alarm not found") })
	@DeleteMapping(path="/alarms/delete/{id}")
	void deleyeAlarm(
		@RequestHeader("Authorization") String unformatedJWT,
		@Parameter(description="Id of the alarm to be deleted. Cannot be empty.", required=true)
		@PathVariable long id
	) {
		if (this.loginSystem.checkLoggedIn(unformatedJWT)) {
			alarmService.deleteAlarmById(id);
		} else {
			throw new UnauthorizedDeviceException();
		}
	}
}