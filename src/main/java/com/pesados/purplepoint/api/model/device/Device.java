package com.pesados.purplepoint.api.model.device;

import com.pesados.purplepoint.api.model.location.Location;
import com.pesados.purplepoint.api.model.user.User;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;

@Entity
@Table(name = "Devices")
public class Device {
    @Schema(description = "Id of the alarm.", required = true)
    private @Id @GeneratedValue Long deviceId;

    @Schema(description = "Firebase token.", required = true)
	@Column(unique = true)
    private String firebaseToken;

    @Schema(description = "Location of the device.", required = true)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="location")
    private Location location;

    @Schema(description = "Last active user.", required = true)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user")
    private User user;

    public Device() {}

    public Device(String firebaseToken, Location location, User user) {
        this.firebaseToken = firebaseToken;
        this.location = location;
        this.user = user;
    }

    public Long getDeviceId() { return deviceId; }

    public void setDeviceId(Long deviceId) { this.deviceId = deviceId; }

    public String getFirebaseToken() { return firebaseToken; }

    public void setFirebaseToken(String firebaseToken) { this.firebaseToken = firebaseToken; }

    public Location getLocation() { return location; }

    public void setLocation(Location location) { this.location = location; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }
}