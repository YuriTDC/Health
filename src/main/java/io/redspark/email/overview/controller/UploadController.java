package io.redspark.email.overview.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/upload")
public class UploadController {

	private static Logger logger = LoggerFactory.getLogger(UploadController.class);
	
	@Autowired
	private Environment env;
	
	@RequestMapping(method = POST)
	public HashMap<String, String> upload(@RequestParam("file") MultipartFile file) {
		
		String name = file.getOriginalFilename();
		logger.info("[UPLOAD] Filename: {}", name);
		
		String path = "";
		
		try {
		
			File directory = new File(env.getRequiredProperty("path.images.folder"));
			File temp = File.createTempFile("avatar_", file.getOriginalFilename(), directory);
			
			OutputStream output = new FileOutputStream(temp);
			BufferedOutputStream stream = new BufferedOutputStream(output);
			
			byte[] bytes = file.getBytes();
			stream.write(bytes);
			stream.close();
			
			path = temp.getAbsolutePath();
			
		} catch (IOException e) {
			logger.error("[UPlOAD FAILED] Filename : {}", name);
		}
		
		HashMap<String, String> map = new HashMap<>();
		map.put("path", path);
		map.put("file_name", name);
		
		return map;
	}
}
