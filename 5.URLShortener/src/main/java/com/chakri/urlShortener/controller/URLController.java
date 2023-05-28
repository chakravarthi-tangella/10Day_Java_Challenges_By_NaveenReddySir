package com.chakri.urlShortener.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chakri.urlShortener.entity.URLGenerator;
import com.chakri.urlShortener.service.URLService;

@RestController
public class URLController {
	
	
	private URLService service;
	
	@Autowired
	public URLController(URLService service) {
		this.service = service;
	}
	
	@GetMapping("/{alias}")
	public ResponseEntity<?> handleRedirect(@PathVariable String alias) throws URISyntaxException
	{
		URLGenerator uRLGenerator = service.getRedirect(alias);
		System.out.println("We are redirecting here : "+ uRLGenerator);
		URI uri = new URI(uRLGenerator.getUrl());
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(uri);
		return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
	}
	
	@PostMapping("/")
	public ResponseEntity<?> createRedirect(@Validated @RequestBody URLGenerator urlGenerator)
	{
		return ResponseEntity.ok(service.createRedirect(urlGenerator));
	}
}
