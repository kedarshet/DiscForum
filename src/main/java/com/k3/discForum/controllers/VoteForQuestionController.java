package com.k3.discForum.controllers;

import com.k3.discForum.domain.Question;
import com.k3.discForum.domain.User;
import com.k3.discForum.domain.Vote;
import com.k3.discForum.repositories.QuestionRepository;
import com.k3.discForum.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class VoteForQuestionController {
    final private QuestionRepository questionRepository;
    final private UserRepository userRepository;

    @PostMapping("/voteUpForQuestion")
    public String voteUpForQuestion(@AuthenticationPrincipal User user, @RequestParam Integer id) {
        return voteForQuestion(user, id, Vote.UP);
    }

    @PostMapping("/voteDownForQuestion")
    public String voteDownForQuestion(@AuthenticationPrincipal User user, @RequestParam Integer id) {
        return voteForQuestion(user, id, Vote.DOWN);
    }

    @PostMapping("/undoVoteForQuestion")
    public String undoVoteForQuestion(@AuthenticationPrincipal User user, @RequestParam Integer id) {
        return voteForQuestion(user, id, Vote.NONE);
    }

    private String voteForQuestion(User user, Integer id, Vote vote) {
        Optional<Question> questionOptional = questionRepository.findById(id);

        if (!questionOptional.isPresent())
            return "redirect:/404";

        Question question = questionOptional.get();

        question.voteByUser(user, vote);
        user.voteForQuestion(question, vote);

        questionRepository.save(question);
        userRepository.save(user);

        return "redirect:/q?id=" + id;
    }
}
