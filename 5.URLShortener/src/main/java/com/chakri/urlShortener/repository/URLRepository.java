package com.chakri.urlShortener.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.chakri.urlShortener.entity.URLGenerator;

@Repository
public interface URLRepository extends JpaRepository<URLGenerator, Long>{
	Optional<URLGenerator> findByAlias(String Alias);
	
	Boolean existsByAlias(String alias);
}
