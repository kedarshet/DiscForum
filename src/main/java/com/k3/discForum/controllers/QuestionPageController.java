package com.k3.discForum.controllers;

import com.k3.discForum.domain.Question;
import com.k3.discForum.domain.User;
import com.k3.discForum.domain.views.QuestionView;
import com.k3.discForum.repositories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class QuestionPageController {
    final private QuestionRepository questionRepository;

    @GetMapping("/q")
    public String main(@AuthenticationPrincipal User user,
                       @RequestParam Integer id,
                       Map<String, Object> model) {
        Optional<Question> q = questionRepository.findById(id);

        if (!q.isPresent())
            return "redirect:/404";

        QuestionView qv = new QuestionView(q.get(), user);

        model.put("question", qv);
        model.put("authorized", (user != null));

        return "question";
    }
}
