package com.controller;

import com.model.User;
import com.model.request.StudentEmailRequest;
import com.model.request.TuitionRequest;
import com.model.sc.Course;
import com.model.sc.Student;
import com.service.UserService;
import com.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maysam.mokarian on 9/21/2017.
 */
@Controller
public class AdminController {


    @Autowired
    private UserService userService;
    @Autowired
    private StudentService studentService;
    @RequestMapping(value = "/admin/home", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/enroll", method = RequestMethod.GET)
    public ModelAndView adminEnroll() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("studentEmailRequest", new StudentEmailRequest());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        modelAndView.setViewName("admin/enroll");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/enroll", method = RequestMethod.POST)
    public ModelAndView adminEnrollPost(StudentEmailRequest emailRequest) {
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        String email ="";
        email = emailRequest.getStudentEmail();
        Student student = studentService.findByEmail(email);

        List<String> list = new ArrayList<>();

        List<Course> listOfCourses = new ArrayList<>();
        listOfCourses.addAll(student.getCoursesForCurrentSemester());
        for (Course course : listOfCourses) {
            list.add(course.getCourseName());
        }
        modelAndView.addObject("courses", list);
        List<String> listOfCoursesOffered = getCourseName(studentService.getCoursesOfferedThisSemester());
        List<Course> listOfCoursesOfferedFull = studentService.getCoursesOfferedThisSemester();

        modelAndView.addObject("coursesToAdd", listOfCoursesOffered);
        modelAndView.addObject("coursesToAddFull", listOfCoursesOfferedFull);

        modelAndView.setViewName("admin/enroll");

        return modelAndView;
    }

    private List<String> getCourseName(List<Course> coursesOfferedThisSemester) {
        List<String> courses = new ArrayList<>();
        for(Course course:coursesOfferedThisSemester){
            courses.add(course.getCourseName());
        }
        return courses;
    }
}
