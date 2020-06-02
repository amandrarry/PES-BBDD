package com.pesados.purplepoint.api.model.image;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
	Optional<Image> findByImgname(String name);

	void deleteByImgname(String name);
}
