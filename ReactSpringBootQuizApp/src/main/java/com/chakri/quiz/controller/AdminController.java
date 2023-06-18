package com.chakri.quiz.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chakri.quiz.entity.Question;
import com.chakri.quiz.entity.Quiz;
import com.chakri.quiz.repository.QuestionRepository;
import com.chakri.quiz.repository.QuizRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private final QuestionRepository questionRepository;
	private final QuizRepository quizRepository;

	public AdminController(QuestionRepository questionRepository, QuizRepository quizRepository) {
		this.questionRepository = questionRepository;
		this.quizRepository = quizRepository;
	}

////	showing Admin page
//	@GetMapping("/")
//	public String showAdminPage(Model model) {
//		Question question = new Question();
//		Quiz quiz = new Quiz();
//
//		model.addAttribute("question", question);
//		model.addAttribute("quiz", quiz);
//
//		return "admin";
//	}
//
//	// Display all questions
//	@GetMapping("/display-questions")
//	public String getAllQuestions(Model model) {
//		List<Question> questions = questionRepository.findAll();
//		model.addAttribute("questions", questions);
//		return "display-questions";
//	}
//
//	// adding question to database
//
//	@PostMapping("/add-question")
//	public String addQuestion(@ModelAttribute Question question) {
//		questionRepository.save(question);
//		return "redirect:/display-questions/?success=QuestionCreated";
//	}
//
////	creating quiz
//	@PostMapping("/create-quiz")
//	public String createQuiz(@RequestParam("technology") String technology) {
//		List<Question> questions = questionRepository.findByTechnology(technology);
//
//		if (questions.size() < 5) {
//			return "redirect://admin/?error=InSufficientQuestions";
//		}
//
//		Quiz quiz = new Quiz();
//		quiz.setTechnology(technology);
//		quiz.setQuestions(questions);
//		quizRepository.save(quiz);
//		return "redirect:/admin/?success=QuizCreated";
//	}

}
