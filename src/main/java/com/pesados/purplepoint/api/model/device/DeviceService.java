package com.pesados.purplepoint.api.model.device;

import java.util.List;
import java.util.Optional;

public interface DeviceService {

    Optional<Device> getDeviceById(Long id);
    
    Optional<Device> getDeviceByFirebaseToken(String token);

    List<Device> getAll();

    void deleteDeviceById(Long id);

    Device saveDevice(Device newRep);

}
