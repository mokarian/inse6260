package com.controller;

import com.model.User;
import com.model.sc.Student;
import com.model.sc.Teacher;
import com.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by maysam.mokarian on 9/21/2017.
 */
@Controller
public class ProfessorController {
    @Autowired
    private TeacherService teacherService;
    @RequestMapping(value = "/professor/home", method = RequestMethod.GET)
    public ModelAndView professorHome() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = teacherService.findByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("professorMessage", "Content Available Only for Users with Professor Role");
        modelAndView.setViewName("professor/home");
        return modelAndView;
    }

    @RequestMapping(value = "/professor/grades", method = RequestMethod.GET)
    public ModelAndView professorGrades() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Teacher teacher = teacherService.findByEmail(auth.getName());        ModelAndView modelAndView = new ModelAndView();

        return modelAndView;
    }

    @RequestMapping(value = "/professor/contactinfo", method = RequestMethod.GET)
    public ModelAndView professorContactInfo() {
        ModelAndView modelAndView = new ModelAndView();

        return modelAndView;
    }
}
