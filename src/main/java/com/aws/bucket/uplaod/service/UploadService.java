package com.aws.bucket.uplaod.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
	
	 public String uploadFile(MultipartFile file);
	 public byte[] downloadFile(String fileName);
	 public String deleteFile(String fileName);

}
