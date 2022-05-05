package com.k3.discForum.controllers;

import com.k3.discForum.domain.Answer;
import com.k3.discForum.domain.Question;
import com.k3.discForum.domain.User;
import com.k3.discForum.repositories.AnswerRepository;
import com.k3.discForum.repositories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AddAnswerController {
    final private QuestionRepository questionRepository;
    final private AnswerRepository answerRepository;

    @PostMapping("/q")
    public String postQuestion(@AuthenticationPrincipal User user,
                               @RequestParam Integer questionId,
                               @RequestParam String body,
                               Map<String, Object> model) {
        // Assuming that the question with given ID always exists.
        final Question q = questionRepository.findById(questionId).get();

        // Add new answer to the database.
        answerRepository.save(new Answer(body, new Date(), user, q));

        // Redirect to the question page.
        return "redirect:/q?id=" + questionId;
    }
}
