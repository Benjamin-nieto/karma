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

	public void uploadFile(File dir, MultipartFile file) throws IllegalStateException, IOException {
		String aux = cs.getUploadroot().concat(dir.getName());
		System.out.println(aux);
		File nfile = new File(aux);
		if (!nfile.exists()) {
			nfile.mkdir();
		}
		
		file.transferTo(new File(aux +File.separator+ file.getOriginalFilename()));
	}

}
