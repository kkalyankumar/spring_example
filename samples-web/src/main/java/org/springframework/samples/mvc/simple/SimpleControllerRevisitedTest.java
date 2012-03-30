package org.springframework.samples.mvc.simple;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class SimpleControllerRevisitedTest {

	public static void main(String[] args) {
		testPost();
	}
	
	public static void testPost() {
		RestTemplate template = new RestTemplate();
		ResponseEntity<String> entity = template.postForEntity(
				"http://localhost:8080/web/simple/revisited5", null, String.class);
		String body = entity.getBody();
		MediaType contentType = entity.getHeaders().getContentType();
		System.out.println("contentType:[" + contentType + "]");
		HttpStatus statusCode = entity.getStatusCode();
		System.out.println("statusCode:[" + statusCode + "]");
	}
}
