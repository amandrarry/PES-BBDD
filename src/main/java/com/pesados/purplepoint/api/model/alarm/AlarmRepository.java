package com.pesados.purplepoint.api.model.alarm;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
	double r_earth = 6378.137;
	double pi = 3.14159265359;

	public Optional<Alarm> findByUsername(String email);


	// public List<Alarm> findByNearbyLocation(float latitude, float longitude);
}