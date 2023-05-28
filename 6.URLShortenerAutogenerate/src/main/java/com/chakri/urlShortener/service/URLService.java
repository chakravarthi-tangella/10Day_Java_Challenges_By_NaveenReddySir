package com.chakri.urlShortener.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chakri.urlShortener.entity.URLGenerator;
import com.chakri.urlShortener.exception.NotFoundException;
import com.chakri.urlShortener.repository.URLRepository;

@Service
public class URLService {
	
	@Autowired
	private URLRepository repository;
	
	public List<URLGenerator> getAllUrls()
	{
		List<URLGenerator> list = repository.findAll();
		
		if(list.isEmpty())
		{
			return new ArrayList<URLGenerator>();
		}
		return repository.findAll();
	}
	
	public Optional<URLGenerator> createRedirect(URLGenerator urlGenerator)
	{
		String url = urlGenerator.getUrl();
		if(repository.existsByUrl(url))
		{
			urlGenerator.setAlias(repository.findByUrl(url).get().getAlias());
		}
		else
		{
			String alias = generateSurl(url);
			urlGenerator.setAlias(alias);
		}
		
		if(repository.existsByAlias(urlGenerator.getAlias()))
		{
			return repository.findByAlias(urlGenerator.getAlias());
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
	
	public URLGenerator getRedirectUrl(String url)
	{
		URLGenerator uRLGenerator = repository.findByUrl(url)
					.orElseThrow(() -> new NotFoundException("Hey we don't have that alias ! try making it"));
		return uRLGenerator;
	}
	
	public String generateSurl(String s)
	{
		String str = removeSpecialChar(s);
		int length = str.length();
		String out = "";
		Random random = new Random();
		for(int i=0; i<6; i++)
		{
			out = out + str.charAt(random.nextInt(length));
		}
		return out;
	}
	
	public String removeSpecialChar(String str)
	{
		String out = "";
		for(int i=0; i<str.length(); i++)
		{
			char c = str.charAt(i);
			if((c>=65 && c<=90) || (c>=97 && c<=122))
			{
				out += c;
			}
		}
		return out;
	}
}
