package com.fpoly.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fpoly.config.StorageProperties;
import com.fpoly.exception.StorageException;
import com.fpoly.exception.StorageFileNotFoundException;
import com.fpoly.service.StorageService;


@Service
public class FileSystemStorageServiceImpl implements StorageService{
	
	private final Path rootLocation;
	
	//định nghĩa và đặt tên file img
	@Override
	public String getStoredFileName(MultipartFile file, String id) {
		String ext = FilenameUtils.getExtension(file.getOriginalFilename());
		return "p" + id + "." + ext; 
	}
	
	//cấu hình cho phần lưu trữ
	public FileSystemStorageServiceImpl(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}
	
	@Override
	public void store(MultipartFile file, String storedFilename) {
		try {
			if(file.isEmpty()) {
				throw new StorageException("Failed to store empty file");
			}
			Path destinationFile = this.rootLocation.resolve(Paths.get(storedFilename))
					.normalize().toAbsolutePath();
			
			if(!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				throw new StorageException("Cannot store file outside current directory");
			}
			try(InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (Exception e) {
			throw new StorageException("failed to store file: " + e);
		}
	}
	
	//nạp nội dung file
	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if(resource.exists() || resource.isReadable()) {
				return resource;
			}
			throw new StorageFileNotFoundException("could not read file:" + filename);
		} catch (Exception e) {
			throw new StorageFileNotFoundException("could not read file:" + filename);	
		}
	}
	
	
	
	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}
	
	//xóa file trong folder khi k cần thiết
	@Override
	public void delete(String storedFilename) throws IOException{
		try {
			Path destinationFile = rootLocation.resolve(Paths.get(storedFilename)).normalize().toAbsolutePath();

			Files.delete(destinationFile);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	//khởi tạo thư mục
	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		} catch (Exception e) {
			throw new StorageException("could not initialize storage", e);
		}
	}
}
