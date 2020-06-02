package com.pesados.purplepoint.api.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pesados.purplepoint.api.PurplePointApplication;
import com.pesados.purplepoint.api.exception.ImageNotFoundException;
import com.pesados.purplepoint.api.exception.UnauthorizedDeviceException;
import com.pesados.purplepoint.api.model.image.Image;
import com.pesados.purplepoint.api.model.image.ImageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1")
public class ImageController {
	
	private final ImageService imgService;
	private final LoginSystem loginSystem;
	private static final Logger logger = LoggerFactory.getLogger(PurplePointApplication.class);

	  
	public ImageController(ImageService imgService, LoginSystem  loginSystem) {
		this.imgService = imgService;
		this.loginSystem = loginSystem;
	}
	  
	// Visibilidad User
	@Operation(summary = "Add a new image", 
			description = "Adds a new image to the database with the information provided. ", 
			tags = { "images" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Image created",
					content = @Content(schema = @Schema(implementation = Image.class))),
			@ApiResponse(responseCode = "400", description = "Invalid input"),
			@ApiResponse(responseCode = "409", description = "Image already exists") })
	@PostMapping(value = "/images", consumes = { "multipart/form-data"})
	Image newPic(
	@RequestHeader("Authorization") String unformatedJWT,  
	@Parameter(description="New pic", required = true) 
	@RequestParam("file") MultipartFile file
	) throws IOException {
		if (this.loginSystem.checkLoggedIn(unformatedJWT)) {
			return imgService.saveImage(new Image(file.getOriginalFilename(), file.getContentType(), Base64.getEncoder().encodeToString(file.getBytes())));
		} else {
			throw new UnauthorizedDeviceException();
		}
		
	}
	  
	// Visibilidad User
	@Operation(summary = "Parse an image", 
		  description = "Parses an image provided. ", 
		  tags = { "images" })
	@ApiResponses(value = {
	   @ApiResponse(responseCode = "200", description = "This is fine", 
			   content = @Content(schema = @Schema(implementation = Image.class))),
	   @ApiResponse(responseCode = "400", description = "Invalid input")
	})
	@PostMapping(value = "/images/parser", consumes = { "multipart/form-data"})
	Image parsePic(
		@RequestHeader("Authorization") String unformatedJWT,
		@Parameter(description="New pic", required = true) @RequestParam("file") MultipartFile file) throws IOException {
		if (this.loginSystem.checkLoggedIn(unformatedJWT)) {
			return new Image(file.getName(), file.getContentType(), Base64.getEncoder().encodeToString(file.getBytes()));
		} else {
			throw new UnauthorizedDeviceException();
		}
	}  

	// Visibilidad User
	@Operation(summary = "Get All Images", description = "Get ", tags = {"images"})
	@ApiResponses(value = {
	          @ApiResponse(responseCode = "200", description = "successful operation",
	                  content = @Content(array = @ArraySchema(schema = @Schema(implementation = Image.class)))) })
	@GetMapping(value = "/images", produces = { "application/json", "application/xml"})
	List<Image> all(
		@RequestHeader("Authorization") String unformatedJWT
	) {
		if (this.loginSystem.checkLoggedIn(unformatedJWT)) {
			return imgService.getAll();
		} else {
			throw new UnauthorizedDeviceException();
		}
	}
	
	// Visibilidad User
	@Operation(summary = "Get an Image", description = "Get ", tags = {"images"})
	@ApiResponses(value = {
	          @ApiResponse(responseCode = "200", description = "successful operation",
	                  content = @Content(array = @ArraySchema(schema = @Schema(implementation = Image.class)))) })
	@GetMapping(value = "/images/{id}", produces = { "application/json", "application/xml"})
	Image getOne(
		@RequestHeader("Authorization") String unformatedJWT,
		@Parameter(description="id of the image.", required = true) @PathVariable long id) {
		if (this.loginSystem.checkLoggedIn(unformatedJWT)) {
			return imgService.getImageById(id).orElseThrow(() -> new ImageNotFoundException(id));
		} else {
			throw new UnauthorizedDeviceException();
		}
	}
	
	// Visibilidad User
	@Operation(summary = "Delete an Images", description = "Delete ", tags = {"images"})
	@ApiResponses(value = {
	          @ApiResponse(responseCode = "200", description = "successful operation",
	                  content = @Content(array = @ArraySchema(schema = @Schema(implementation = Image.class)))) })
	@DeleteMapping(value = "/images/{id}", produces = { "application/json", "application/xml"})
	void delOne(
		@RequestHeader("Authorization") String unformatedJWT,
		@Parameter(description="id of the image.", required = true) @PathVariable long id) {
		if (this.loginSystem.checkLoggedIn(unformatedJWT)) {
			imgService.deleteImageById(id);
		} else {
			throw new UnauthorizedDeviceException();
		}
	}
}
