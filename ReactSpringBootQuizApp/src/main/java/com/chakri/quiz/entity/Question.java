package com.chakri.quiz.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

//@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
@Entity
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String question;
	private String opt1;
	private String opt2;
	private String opt3;
	private String opt4;
	private String correctAnswer;
	private String technology;

	public Question() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Question(Long id, String question, String opt1, String opt2, String opt3, String opt4, String correctAnswer,
			String technology) {
		super();
		this.id = id;
		this.question = question;
		this.opt1 = opt1;
		this.opt2 = opt2;
		this.opt3 = opt3;
		this.opt4 = opt4;
		this.correctAnswer = correctAnswer;
		this.technology = technology;
	}

}
