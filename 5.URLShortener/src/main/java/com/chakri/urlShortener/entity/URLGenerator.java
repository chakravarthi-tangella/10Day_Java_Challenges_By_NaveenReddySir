package com.chakri.urlShortener.entity;

import org.hibernate.annotations.NaturalId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class URLGenerator {
	@Id
	@GeneratedValue
	private Long id;
	
	@NaturalId
	@Column(unique = true, nullable = false)
	private String alias;
	
	@Column(nullable = false)
	private String url;

	public URLGenerator() {
		super();
	}

	public URLGenerator(final String alias, final String url) {
		super();
		this.alias = alias;
		this.url = url;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "URL [id=" + id + ", alias=" + alias + ", url=" + url + "]";
	}
}
