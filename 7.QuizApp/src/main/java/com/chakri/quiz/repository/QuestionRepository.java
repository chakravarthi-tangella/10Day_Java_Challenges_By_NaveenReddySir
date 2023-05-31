package com.chakri.quiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chakri.quiz.entity.Question;


@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    // Custom query to find questions by technology
    List<Question> findByTechnology(String technology);

	List<Question> findTop5ByTechnology(String technology);

}
