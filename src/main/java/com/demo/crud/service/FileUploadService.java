package com.demo.crud.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.demo.config.ConfigProperty;

@Service
public class FileUploadService {

	@Autowired
	private ConfigProperty cs;

	public void uploadFile(MultipartFile file) throws IllegalStateException, IOException {
		file.transferTo(new File(cs.getUploadroot() + file.getOriginalFilename()));
	}

}
