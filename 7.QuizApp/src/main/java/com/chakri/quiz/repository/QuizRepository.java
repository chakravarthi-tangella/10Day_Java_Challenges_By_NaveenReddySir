package com.chakri.quiz.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chakri.quiz.entity.Quiz;


@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
	// Custom query to find quiz by technology
	Optional<Quiz> findByTechnology(String technology);
}