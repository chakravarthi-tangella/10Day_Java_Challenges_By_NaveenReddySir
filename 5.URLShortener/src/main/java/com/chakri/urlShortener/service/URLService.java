package com.chakri.urlShortener.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chakri.urlShortener.entity.URLGenerator;
import com.chakri.urlShortener.exception.BadRequestException;
import com.chakri.urlShortener.exception.NotFoundException;
import com.chakri.urlShortener.repository.URLRepository;

@Service
public class URLService {
	
	private URLRepository repository;
	
	@Autowired
	public URLService(URLRepository repository) {
		super();
		this.repository = repository;
	}
	
	public Optional<URLGenerator> createRedirect(URLGenerator urlGenerator)
	{
		if(repository.existsByAlias(urlGenerator.getAlias()))
		{
			throw new BadRequestException("Alias already exists");
		}
		
		System.out.println("Redirect Request : " + urlGenerator.toString());
		URLGenerator uRLGenerator = repository.save(new URLGenerator(urlGenerator.getAlias(), urlGenerator.getUrl()));
		System.out.println("Url : " + uRLGenerator);
		
		return Optional.ofNullable(uRLGenerator);
	}
	
	public URLGenerator getRedirect(String alias)
	{
		URLGenerator uRLGenerator = repository.findByAlias(alias)
					.orElseThrow(() -> new NotFoundException("Hey we don't have that alias ! try making it"));
		return uRLGenerator;
	}
}
