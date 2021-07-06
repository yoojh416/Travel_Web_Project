package com.upload.files.controller;

import com.upload.files.entity.UploadFile;
import com.upload.files.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImageController { //메인 이미지(제품)용 컨트롤러

	@Autowired
	ImageService imageService;
	
	@Autowired
	ResourceLoader resourceLoader;
	
	@PostMapping("/image")
	public ResponseEntity<?> imageUpload(@RequestParam("file") MultipartFile file) {
		try {
			UploadFile uploadFile = imageService.store(file);
			return ResponseEntity.ok().body("/image/" + uploadFile.getId());
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping("/image/{fileId}") // DB에 저장(메인 이미지 - 제품용)
	public ResponseEntity<?> serveFile(@PathVariable Long fileId){
		try {
			UploadFile uploadFile = imageService.load(fileId);
			Resource resource = resourceLoader.getResource("file:" + uploadFile.getFilePath());
			return ResponseEntity.ok().body(resource);
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
		
	}
}
