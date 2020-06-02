package com.pesados.purplepoint.api.model.device;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Optional<Device> findByFirebaseToken(String firebaseToken);
}
