package com.shasha.blog.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shasha.blog.service.IFileService;

@Service
public class FileServiceImpl implements IFileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		String filename = file.getOriginalFilename();
		String randomId = UUID.randomUUID().toString();
		String fileName1 = randomId.concat(filename.substring(filename.lastIndexOf(".")));
		String filePathName = path + File.separator + fileName1;

		File fileObj = new File(path);
		if (!fileObj.exists()) {
			fileObj.mkdir();
		}
		Files.copy(file.getInputStream(), Paths.get(filePathName));

		return fileName1;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullPath = path + File.separator + fileName;
		InputStream inputStream = new FileInputStream(fullPath);

		return inputStream;
	}

}
