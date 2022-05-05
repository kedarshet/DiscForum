package com.k3.discForum.controllers;

import com.k3.discForum.domain.Role;
import com.k3.discForum.domain.User;
import com.k3.discForum.repositories.UserRepository;
import com.k3.discForum.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.Map;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {
    final private UserRepository userRepository;
    final private UserService userService;

    @GetMapping
    public String main(Map<String, Object> model) {
        return "registration";
    }

    @PostMapping
    public String registerUser(User user, Map<String, Object> model) {
        if (userService.userWithThisUsernameAlreadyExists(user.getUsername())) {
            model.put("userWithThisUsernameAlreadyExistsMessage", true);
            return "registration";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);

        return "redirect:/login";
    }
}
