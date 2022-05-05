package com.k3.discForum.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class PageNotFoundController implements ErrorController {
    @RequestMapping("/404")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String main() {
        return "404";
    }

    @Override
    @RequestMapping("/error")
    public String getErrorPath() {
        return "redirect:/404";
    }
}
