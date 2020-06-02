package com.pesados.purplepoint.api.model.user;


import com.pesados.purplepoint.api.model.image.Image;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;

@Entity
@Table(name = "Users")
public class User {

	@Schema(description = "Id of the user.", required = true)
	private @Id @GeneratedValue Long id;
	@Schema(description = "Name of the user.", example = "Claudia/Isma", required = true)
	private String name;
	@Schema(description = "Username of the user.", example = "OhAmadoLider", required = true)
	@Column(unique = true)
	private String username;

	@Schema(description = "Email of the user.", example = "ohamadoslideres@gmail.com", required = true)
	@Column(unique = true)

  private String email;
	@Schema(description = "Password of the user.", required = true)
	private String password;
	@Schema(description = "Gender of the user.", required = true)
	private String gender;
	@Schema(description = "The user's token. (null before assignment)", required = false)
	@Column (length = 400)
	private String token;
	@Schema(description = "The number of users helped.", required = false)
	private int helpedUsers;
	@Schema(description = "The number of marked sports.", required = false)
	private int markedSpots;
	@Schema(description = "The profile picture of the User", required = false)
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "imageid") 
	private Image profilePic;

	public User() {}

	public User(String name, String username, String email, String password, String gender) {
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.gender = gender;
		this.token = null;
		this.helpedUsers = 0;
		this.markedSpots = 0;
		this.profilePic = null;
	}

	public User(String username, String email) {
		this.name = "mock";
		this.username = username;
		this.email = email;
		this.password = "1234";
		this.gender = null;
		this.token = null;
		this.helpedUsers = 0;
		this.markedSpots = 0;
		this.profilePic = null;
	}
	
	public Long getID() {
		return id; 
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getGender() {  
		return gender; 
	}

	public int getHelpedUsers() {
		return helpedUsers;
	}
	
	public void setHelpedUsers(int helpedUsers) {
		this.helpedUsers = helpedUsers;
	}

	public int getMarkedSpots() {
		return markedSpots;
	}
	
	public void setMarkedSpots(int markedSpots) {
		this.markedSpots = markedSpots;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username2) {
		this.username = username2;
	}

	public Image getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(Image profilePic) {
		this.profilePic = profilePic;
	}
}