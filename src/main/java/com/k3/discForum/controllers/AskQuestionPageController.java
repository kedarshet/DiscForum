package com.k3.discForum.controllers;

import com.k3.discForum.domain.Question;
import com.k3.discForum.domain.Tag;
import com.k3.discForum.domain.User;
import com.k3.discForum.repositories.QuestionRepository;
import com.k3.discForum.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/askQuestion")
public class AskQuestionPageController {
    final private QuestionRepository questionRepository;
    final private TagService tagService;

    @GetMapping
    public String main(@AuthenticationPrincipal User user,
                       Map<String, Object> model) {
        model.put("authorizedUser", user);
        return "askQuestion";
    }

    @PostMapping
    public String postQuestion(@AuthenticationPrincipal User user,
                               @RequestParam String title,
                               @RequestParam String body,
                               @RequestParam("tag") String [] tagNames,
                               Map<String, Object> model) {
        // Create an empty set of tags.
        HashSet<Tag> tags = new HashSet<Tag>();

        // Fill this set with tags with given name from the database.
        // If the tag not exist create such new one.
        for (final String name : tagNames)
            tags.add(tagService.getTagCreateIfNotExists(name));

        // Create new question and save it in the database.
        final Question q = new Question(title, body, new Date(), user, tags);
        questionRepository.save(q);

        // Redirect to the new question's page.
        return "redirect:/q?id=" + q.getId();
    }
}
