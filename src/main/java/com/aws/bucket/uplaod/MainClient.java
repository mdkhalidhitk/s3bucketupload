package com.aws.bucket.uplaod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

public class MainClient {

	private static AmazonS3 awsClient;

	public static void s3client(String[] args) {

		AWSCredentials credentials = new BasicAWSCredentials("KHAKIAYJ2OSN3ARIYYE3G2we",
				"s7p4ujijGOwhdZd0bbdIWGO+AI190inzYp+WZX1mkh");
		awsClient = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion("ap-south-1").build();

		S3Object s3Object = awsClient.getObject("khalids3-lambda", "test-me/TMobile_Service_20220207.csv");

		S3ObjectInputStream inputStream = s3Object.getObjectContent();
		ListObjectsV2Request req = new ListObjectsV2Request().withBucketName("khalids3-lambda"); 
		List<List<Object>> chunk = new ArrayList<List<Object>>();
		Map<Integer, List<Object>> map = new HashMap<Integer, List<Object>>();
		List<String> obj = new ArrayList<String>();
		try (final BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
			br.lines().skip(1).forEach(out -> {
				obj.add(out);

			});
			obj.stream().forEach(System.out::println);
		} catch (IOException e) {

		}
	}

}
