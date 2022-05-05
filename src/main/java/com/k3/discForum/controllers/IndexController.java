package com.k3.discForum.controllers;

import com.k3.discForum.domain.Question;
import com.k3.discForum.domain.User;
import com.k3.discForum.domain.views.QuestionView;
import com.k3.discForum.repositories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Controller
@RequiredArgsConstructor
public class IndexController {
    final private QuestionRepository questionRepository;

    @GetMapping
    public String main(@AuthenticationPrincipal User user,
                       Map<String, Object> model) {
        final List<Question> questions = questionRepository.findAllByOrderByCreationDateTimeDesc();
        List<QuestionView> questionViews = new ArrayList<>();

        for (final Question q : questions)
            questionViews.add(new QuestionView(q, user));

        model.put("questions", questionViews);
        model.put("authorized", (user != null));

        return "index";
    }
}
