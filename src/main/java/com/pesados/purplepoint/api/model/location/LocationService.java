package com.pesados.purplepoint.api.model.location;

import java.util.List;
import java.util.Optional;

public interface LocationService {


	Optional<Location> getLocationById(Long id);
	
	List<Location> getAll();
	
	void deleteLocationById(Long id);

	Location saveLocation(Location newLocation);

}
