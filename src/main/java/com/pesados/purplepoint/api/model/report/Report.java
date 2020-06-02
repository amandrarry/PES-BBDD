package com.pesados.purplepoint.api.model.report;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.pesados.purplepoint.api.model.location.Location;
import com.pesados.purplepoint.api.model.user.User;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "Report")
public class Report {
	
	
	@Schema(description = "Id of the report.", required = true)
	@Id
	@Column(name = "reportid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reportid;	
	@Column(name = "report_desc")
	private String description;
	@Schema(description = "Location of the report", required = true)
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "report_loc")
	private Location location;	
	@Schema(description = "Reporter", required = true)
	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "report_usr") 	
	private User user;	
	
	
	public Report() {
		super();
	}
	
	public Report(Location loc, User usr) {
		this.location = loc;
		this.user = usr;
	}
	
	public Report(String desc, Location loc, User usr) {
		this.description =desc;
		this.location = loc;
		this.user = usr;
	}
	
	public Long getId() {
		return this.reportid;
	}
	
	public void setId(Long id) {
		this.reportid = id;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public void setUser(User usr) {
		this.user = usr;
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public void setLocation(Location loc) {
		this.location = loc;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
