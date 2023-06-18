package com.chakri.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chakri.quiz.entity.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long>{

}
