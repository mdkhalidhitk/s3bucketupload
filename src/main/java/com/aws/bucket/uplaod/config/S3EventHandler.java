package com.aws.bucket.uplaod.config;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;

public class S3EventHandler implements RequestHandler<S3Event, Boolean> {

	@Override
	public Boolean handleRequest(S3Event input, Context context) {
		// TODO Auto-generated method stub
		return true;
	}

}
