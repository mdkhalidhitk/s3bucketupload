/**
 * 
 */
package com.aws.bucket.uplaod.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.aws.bucket.uplaod.service.UploadService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author khalid
 *
 */
@Service
@Slf4j
public class UploadServiceImpl implements UploadService {

	@Value("${application.bucket.name}")
	private String bucketName;

	@Autowired
	private AmazonS3 s3Client;

	@Override
	public String uploadFile(MultipartFile file) {
		File fileObj = convertMultiPartFileToFile(file);
		String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
		s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
		fileObj.delete();
		return "File uploaded Successfully: " + fileName;
	}

	@Override
	public byte[] downloadFile(String fileName) {
		S3Object s3Object = s3Client.getObject(bucketName, fileName);
		S3ObjectInputStream inputStream = s3Object.getObjectContent();
		try {
			byte[] content = IOUtils.toByteArray(inputStream);
			return content;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String deleteFile(String fileName) {
		s3Client.deleteObject(bucketName, fileName);
		return fileName + " deleted Successfully ...";
	}

	private File convertMultiPartFileToFile(MultipartFile file) {
		File convertedFile = new File(file.getOriginalFilename());
		try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
			fos.write(file.getBytes());
		} catch (IOException e) {
			log.error("Error converting multipartFile to file", e);
		}
		return convertedFile;
	}

}
