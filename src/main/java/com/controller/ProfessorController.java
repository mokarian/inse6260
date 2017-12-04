package com.controller;

import com.model.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * A professor controller
 *
 * @author Maysam Mokarian
 * @version 1.0
 * @since 21.09.2017
 */
@Controller
public class ProfessorController {
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/professor/home", method = RequestMethod.GET)
    public ModelAndView professorHome() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("professorMessage", "Content Available Only for Users with Professor Role");
        modelAndView.setViewName("professor/home");
        return modelAndView;
    }

    @RequestMapping(value = "/professor/grades", method = RequestMethod.GET)
    public ModelAndView professorGrades() {
        ModelAndView modelAndView = new ModelAndView();

        return modelAndView;
    }

    @RequestMapping(value = "/professor/contactinfo", method = RequestMethod.GET)
    public ModelAndView professorContactInfo() {
        ModelAndView modelAndView = new ModelAndView();

        return modelAndView;
    }
}
