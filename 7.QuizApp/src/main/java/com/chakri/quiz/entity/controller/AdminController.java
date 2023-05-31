package com.chakri.quiz.entity.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.chakri.quiz.entity.Question;
import com.chakri.quiz.entity.QuestionDTO;
import com.chakri.quiz.entity.Quiz;
import com.chakri.quiz.repository.QuestionRepository;
import com.chakri.quiz.repository.QuizRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private final QuestionRepository questionRepository;

	private final QuizRepository quizRepository;

	@Autowired
	public AdminController(QuestionRepository questionRepository, QuizRepository quizRepository) {
		this.questionRepository = questionRepository;
		this.quizRepository = quizRepository;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showAdminPage(Model model) {
		model.addAttribute("question", new Question());
		model.addAttribute("quiz", new Quiz());
		return "admin";
	}

	// Display all questions
	@RequestMapping(value = "/display-questions", method = RequestMethod.GET)
	public String displayQuestions(Model model) {
		List<Question> questions = questionRepository.findAll();
		model.addAttribute("questions", questions);
		return "display-questions";
	}

	@PostMapping("/add-question")
	public String addQuestion(@ModelAttribute("question") QuestionDTO questionDTO) {
		// Convert the QuestionDTO to a Question entity and save it to the database
		Question question = new Question();
		question.setQuestion(questionDTO.getQuestion());
		question.setOpt1(questionDTO.getOpt1());
		question.setOpt2(questionDTO.getOpt2());
		question.setOpt3(questionDTO.getOpt3());
		question.setOpt4(questionDTO.getOpt4());
		question.setCorrectAnswer(questionDTO.getCorrectAnswer());
		question.setTechnology(questionDTO.getTechnology());

		// Save the question to the database or perform any necessary operations
		questionRepository.save(question);

		// Redirect to the desired page after successful processing
		return "redirect:/admin/?success=QuestionCreated"; // Replace with the appropriate redirect URL
	}

	// Edit question
	@RequestMapping(value = "/edit-question/{id}")
	public String editQuestion(@PathVariable("id") Long id, Model model) {
		Optional<Question> optionalQuestion = questionRepository.findById(id);
		if (optionalQuestion.isEmpty()) {
			return "redirect:/admin/display-questions";
		}

		Question question = optionalQuestion.get();
		model.addAttribute("question", question);
		return "edit-question";
	}

	@PostMapping("/save")
	public String saveQuesion(@ModelAttribute("question") QuestionDTO questionDTO) {
		// Convert the QuestionDTO to a Question entity and save it to the database
		Question question = new Question();
		question.setId(questionDTO.getId());
		question.setQuestion(questionDTO.getQuestion());
		question.setOpt1(questionDTO.getOpt1());
		question.setOpt2(questionDTO.getOpt2());
		question.setOpt3(questionDTO.getOpt3());
		question.setOpt4(questionDTO.getOpt4());
		question.setCorrectAnswer(questionDTO.getCorrectAnswer());
		question.setTechnology(questionDTO.getTechnology());

		// Save the question to the database or perform any necessary operations
		questionRepository.save(question);

		// Redirect to the desired page after successful processing
		return "redirect:/admin/?success=QuestionCreated"; // Replace with the appropriate redirect URL
	}

	// Delete question
	@RequestMapping("/delete-question")
	public String deleteQuestion(@PathVariable("id") Long id) {

		questionRepository.deleteById(id);
		return "redirect:/admin/display-questions";
	}

	@RequestMapping("/create-quiz")
	public String createQuiz(@RequestParam("technology") String technology) {
		List<Question> questions = questionRepository.findTop5ByTechnology(technology);
		if (questions.size() < 5) {
			return "redirect:/admin/?error=InsufficientQuestions";
		}

		Quiz quiz = new Quiz();
		quiz.setTechnology(technology);
		quiz.setQuestions(questions);
		quizRepository.save(quiz);

		return "redirect:/admin/?success=QuizCreated";
	}

}
