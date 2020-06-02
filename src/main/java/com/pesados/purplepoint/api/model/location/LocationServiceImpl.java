package com.pesados.purplepoint.api.model.location;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl implements LocationService {
 
    @Autowired
    private LocationRepository reportRepository;

	@Override
	public Optional<Location> getLocationById(Long id) {
		return this.reportRepository.findById(id);
	}

	@Override
	public List<Location> getAll() {
		return this.reportRepository.findAll();
	}

	@Override
	public void deleteLocationById(Long id) {
		this.reportRepository.deleteById(id);
	}

	@Override
	public Location saveLocation(Location newLocation) {
		return this.reportRepository.save(newLocation);
	}

}