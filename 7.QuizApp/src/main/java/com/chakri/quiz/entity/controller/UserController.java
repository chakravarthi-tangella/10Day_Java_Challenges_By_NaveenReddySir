package com.chakri.quiz.entity.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chakri.quiz.entity.Question;
import com.chakri.quiz.entity.Quiz;
import com.chakri.quiz.repository.QuestionRepository;
import com.chakri.quiz.repository.QuizRepository;


@Controller
@RequestMapping("/user")
public class UserController {
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public UserController(QuizRepository quizRepository, QuestionRepository questionRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }

    @GetMapping("/")
    public String showUserPage() {
        return "user";
    }

    @GetMapping("/play-quiz")
    public String playQuiz(@RequestParam("technology") String technology, Model model) {
        Optional<Quiz> optionalQuiz = quizRepository.findByTechnology(technology);
        if (optionalQuiz.isEmpty()) {
            return "redirect:/user/?error=QuizNotFound";
        }

        Quiz quiz = optionalQuiz.get();
        List<Question> questions = questionRepository.findByTechnology(technology);
        List<Question> questionsList = new ArrayList<>();
        List<Integer> numbers = new ArrayList<Integer>();
        int i=0;
        while(i<5)
        {
        	Random random = new Random();
        	int val = random.nextInt(5);
        	if(!numbers.contains(val))
        	{
        		numbers.add(val);
        		questionsList.add(questions.get(val));
        		i++;
        	}	
        }
        model.addAttribute("quiz", quiz);
        model.addAttribute("questions", questionsList);

        return "play-quiz";
    }

    @PostMapping("/submit-quiz")
    public String submitQuiz(@RequestParam Map<String, String> answers, Model model) {
        int correctAnswers = 0;
        for (Map.Entry<String, String> entry : answers.entrySet()) {
            Long questionId = Long.parseLong(entry.getKey());
            String selectedAnswer = entry.getValue();

            Optional<Question> optionalQuestion = questionRepository.findById(questionId);
            if (optionalQuestion.isPresent()) {
                Question question = optionalQuestion.get();
                if (question.getCorrectAnswer().equals(selectedAnswer)) {
                    correctAnswers++;
                }
            }
        }

        model.addAttribute("correctAnswers", correctAnswers);
        return "quiz-result";
    }
}