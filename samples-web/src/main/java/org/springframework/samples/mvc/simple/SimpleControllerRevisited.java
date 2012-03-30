package org.springframework.samples.mvc.simple;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SimpleControllerRevisited {

	@RequestMapping(value="/simple/revisited", method=RequestMethod.GET, headers="Accept=text/plain")
	public @ResponseBody String simple() {
		return "Hello world revisited!";
	}
	
	@RequestMapping(value="/simple/revisited2", method=RequestMethod.GET, headers="Content-Type=text/plain")
	public @ResponseBody String simple2() {
		return "Hello world 2 revisited!";
	}
	
	@RequestMapping(value="/simple/revisited3", method=RequestMethod.POST, headers="Content-Type=text/plain")
	public @ResponseBody String simple3() {
		return "Hello world 3 revisited!";
	}
	
	@RequestMapping(value="/simple/revisited4", method=RequestMethod.POST)
	public @ResponseBody String simple4() {
		return "Hello world 4 revisited!";
	}
	
	@RequestMapping(value="/simple/revisited5", method=RequestMethod.POST, headers="Content-Type=application/x-www-form-urlencoded")
	public @ResponseBody String simple5() {
		return "Hello world 5 revisited!";
	}
	
	/*
	@RequestMapping(
			value="/simple/revisited2", 
			method=RequestMethod.GET)
	public @ResponseBody String simple2(HttpEntity<byte[]> requestEntity) {
		 HttpHeaders requestHeaders = requestEntity.getHeaders();
		 List<MediaType> acceptedMediaTypes = requestHeaders.getAccept();
		 for (MediaType mediaType : acceptedMediaTypes) {
			 System.out.println("[" + mediaType + "]");
		 }
		 MediaType contentType = requestHeaders.getContentType();
		 System.out.println("contentType:[" + contentType + "]");
		return "Hello world 2 revisited!";
	}
	
	@RequestMapping(
			value="/simple/revisited3", 
			method=RequestMethod.GET,
			consumes="text/plain")
	public @ResponseBody String simple3(HttpEntity<byte[]> requestEntity) {
		 HttpHeaders requestHeaders = requestEntity.getHeaders();
		 List<MediaType> acceptedMediaTypes = requestHeaders.getAccept();
		 for (MediaType mediaType : acceptedMediaTypes) {
			 System.out.println("[" + mediaType + "]");
		 }
		return "Hello world 3 revisited!";
	}
	
	@RequestMapping(
			value="/simple/revisited4", 
			method=RequestMethod.GET,
			consumes="text/html")
	public @ResponseBody String simple4(HttpEntity<byte[]> requestEntity) {
		 HttpHeaders requestHeaders = requestEntity.getHeaders();
		 List<MediaType> acceptedMediaTypes = requestHeaders.getAccept();
		 for (MediaType mediaType : acceptedMediaTypes) {
			 System.out.println("[" + mediaType + "]");
		 }
		return "Hello world 4 revisited!";
	}
	
	@RequestMapping(
			value="/simple/revisited5", 
			method=RequestMethod.POST)
	public @ResponseBody String simple5(HttpEntity<byte[]> requestEntity) {
		 HttpHeaders requestHeaders = requestEntity.getHeaders();
		 System.out.println("contentType:[" + requestHeaders.getContentType() + "]");
		return "Hello world 5 revisited!";
	}
	
	@RequestMapping(
			value="/simple/revisited6", 
			method=RequestMethod.POST,
			consumes="application/x-www-form-urlencoded")
	public @ResponseBody String simple6(HttpEntity<byte[]> requestEntity) {
		 HttpHeaders requestHeaders = requestEntity.getHeaders();
		 System.out.println("contentType:[" + requestHeaders.getContentType() + "]");
		return "Hello world 5 revisited!";
	}
	*/

}
