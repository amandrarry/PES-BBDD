package com.pesados.purplepoint.api.controller;

import com.pesados.purplepoint.api.exception.DeviceNotFoundException;
import com.pesados.purplepoint.api.model.device.Device;
import com.pesados.purplepoint.api.model.device.DeviceService;
import com.pesados.purplepoint.api.model.firebase.PushNotificationService;
import com.pesados.purplepoint.api.model.location.Location;
import com.pesados.purplepoint.api.model.user.User;
import com.pesados.purplepoint.api.model.user.UserService;
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
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DeviceController {

	@Autowired
    private DeviceService deviceService;
	
	@Autowired
    private UserService userService;
    
	@Autowired
	private PushNotificationService pushNotificationService;

    // Visibilidad Device
    @Operation(summary = "Add a new device",
            description = "Adds a new device to the database with the information provided.", tags = { "devices" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "device created",
                    content = @Content(schema = @Schema(implementation = Device.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "device already exists") })
    @PostMapping(value = "/devices", consumes = { "application/json", "application/xml" })
    Device newdevice(
            @Parameter(description="device to add. Cannot null or empty.",
                    required=true, schema=@Schema(implementation = Device.class))
            @Valid @RequestBody Device newRep, @RequestHeader("X-Authorization-Firebase") String firebaseToken
    ) {
        try {
        	Device dev = this.deviceService.getDeviceByFirebaseToken(firebaseToken)
        		.orElseThrow( () -> new DeviceNotFoundException(firebaseToken));
        	dev.setFirebaseToken(firebaseToken);
        	return this.deviceService.saveDevice(dev);
        } catch (DeviceNotFoundException e) {
        	return this.deviceService.saveDevice(newRep);
        }
    }

    // Visibilidad Device
    @Operation(summary = "Get All devices", description = "Get all devices created.", tags = {"devices"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Device.class)))) })
    @GetMapping(value = "/devices", produces = { "application/json", "application/xml"})
    List<Device> all() {
        return deviceService.getAll();
    }

    // Visibilidad Device
    @Operation(summary = "Get a device", description = "Get ", tags = {"devices"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Device.class)))) })
    @GetMapping(value = "/devices/{id}", produces = { "application/json", "application/xml"})
    Device getOne(
            @Parameter(description="id of the device.", required = true) @PathVariable Long id) {
        return deviceService.getDeviceById(id).orElseThrow(() -> new DeviceNotFoundException(id));
    }

    // Visibilidad Device
    @Operation(summary = "Delete a device", description = "Delete ", tags = {"devices"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Device.class)))) })
    @DeleteMapping(value = "/devices/{id}", produces = { "application/json", "application/xml"})
    void delOne(
            @Parameter(description="id of the device.", required = true) @PathVariable Long id) {
        deviceService.deleteDeviceById(id);
    }

    // Visibilidad Device
        @Operation(summary = "Update an existing Device by token", 
    		description = "Update the token, user and/or location given the token of an existing device.", tags = { "devices" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid username supplied"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "405", description = "Validation exception")})
    @PutMapping("/devices/{token}" )
    Device updateDeviceByToken(@Parameter(description = "New information for the device.", required = true)
                            @RequestBody Device newDevice,
                            @Parameter(description = "Token of the device to replace.", required = true)
    						@PathVariable String token
    ) {
      return deviceService.getDeviceByFirebaseToken(token)
	      .map(device -> {
	          device.setFirebaseToken(newDevice.getFirebaseToken());
	          device.setLocation(newDevice.getLocation());
	          device.setUser(newDevice.getUser());
	          return deviceService.saveDevice(device);
	      })
	      .orElseGet(() -> {
	          return deviceService.saveDevice(newDevice);
	      });

    }
     // Visibilidad Device   
    @Operation(summary = "Update an existing Device Location by token", 
    		description = "Update the location given the token of an existing device.", tags = { "devices" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid username supplied"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "405", description = "Validation exception")})
    @PutMapping(value = "/devices/{token}/location", consumes = {"application/json", "application/xml"})
    Device updateDeviceLocationByToken(@Parameter(description = "New information for the device.", required = true)
                            @RequestBody Location newLocation,
                            @Parameter(description = "Token of the device to replace.", required = true)
    						@PathVariable String token
    ) {
        Device dev = deviceService.getDeviceByFirebaseToken(token).orElseThrow(() -> new DeviceNotFoundException(token));
        dev.setLocation(newLocation);
        return deviceService.saveDevice(dev);
    }

    // Visibilidad Device
    @Operation(summary = "Sends a notification to the user", description = "Notify the user who requested help that someone is coming.", tags = {"devices"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping(path = "/devices/notifyuser/{firebasetoken}")
    void notifyUser(
            @Parameter(description = "Username of the user who is going to receive the notification.", required = true)
            @RequestBody User user,
            @Parameter(description = "Token of the device that must recieve notification.", required = true)
            @PathVariable String firebasetoken
    ) {
        if (user.getUsername() == "")
            pushNotificationService.sendNotification(firebasetoken, "Someone");
        else
            pushNotificationService.sendNotification(firebasetoken, user.getUsername());
    }
}
