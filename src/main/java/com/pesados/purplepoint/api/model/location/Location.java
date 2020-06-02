package com.pesados.purplepoint.api.model.location;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.*;

@Entity
@Table(name = "Locations")
public class Location {
    @Schema(description = "Id of the location.", required = true)
    @Column(name = "locationId")
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long locationId;

    @Schema(description = "Latitude.", required = true)
    @Column(name = "latitude")
    private float latitude;

    @Column(name = "longitude")
    @Schema(description = "Longitude.", required = true)
    private float longitude;

    @Column(name = "accuracy")
    @Schema(description = "Accuracy.", required = true)
    private float accuracy;

    @Column(name = "timestamp")
    @Schema(description = "Timestamp of the moment the location was created.", required = true)
    private float timestamp;

    public Location() {
    }

    public Location(float latitude, float longitude, float accuracy, float timestamp) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.accuracy = accuracy;
        this.timestamp = timestamp;
    }

    public Long getLocationId() { return locationId; }
    public void setLocationId(Long locationId) { this.locationId = locationId; }

    public float getLatitude() { return latitude; }
    public void setLatitude(float latitude) { this.latitude = latitude; }

    public float getAccuracy() { return accuracy; }
    public void setAccuracy(float accuracy) { this.accuracy = accuracy; }

    public float getTimestamp() { return timestamp; }
    public void setTimestamp(float timestamp) { this.timestamp = timestamp; }

    public float getLongitude() { return longitude; }
    public void setLongitude(float longitude) { this.longitude = longitude; }
}