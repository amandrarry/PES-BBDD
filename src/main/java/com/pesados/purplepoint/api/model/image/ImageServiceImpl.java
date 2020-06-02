package com.pesados.purplepoint.api.model.image;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {
 
    @Autowired
    private ImageRepository imageRepository;
 
	@Override
	public Optional<Image> getImageById(Long id) {
		return imageRepository.findById(id);
	}
	@Override
	public Optional<Image> getImageByImgname(String name) {
		return imageRepository.findByImgname(name);
	}
	@Override
	public Image saveImage(Image newImage) {
		return imageRepository.save(newImage);
	}
	@Override
	public void deleteImageById(Long id) {
		imageRepository.deleteById(id);
	}
	@Override
	public void deleteImageByImgname(String name) {
		imageRepository.deleteByImgname(name);		
	}
	@Override
	public List<Image> getAll() {
		return imageRepository.findAll();
	}
}