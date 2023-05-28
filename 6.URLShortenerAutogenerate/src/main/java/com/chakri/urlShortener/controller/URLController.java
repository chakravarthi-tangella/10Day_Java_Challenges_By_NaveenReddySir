package com.chakri.urlShortener.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.chakri.urlShortener.entity.URLGenerator;
import com.chakri.urlShortener.service.URLService;

@Controller
public class URLController {
	
	@Autowired
	private URLService service;
	
	@GetMapping("/")
	public String homePage() {
		return "home";
	}
	

	@GetMapping("/available_urls")
	public ModelAndView getAllUrls()
	{
		List<URLGenerator> list = service.getAllUrls();
		ModelAndView mv = new ModelAndView();
		mv.setViewName("urlList");
		mv.addObject("urls", list);
		return mv;
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
	
	
	@PostMapping("/save")
    public String generateAlias(@ModelAttribute URLGenerator urlGenerator, Model model) {
		Optional<URLGenerator> generatedUrl = service.createRedirect(urlGenerator);
        String generatedAlias = generatedUrl.get().getAlias();
        model.addAttribute("alias", generatedAlias);
        return "generateUrl";
    }
}
