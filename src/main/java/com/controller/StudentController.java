package com.controller;

import com.model.User;
import com.model.sc.Course;
import com.model.sc.Student;
import com.service.StudentService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by maysam.mokarian on 9/21/2017.
 */
@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/student/home", method = RequestMethod.GET)
    public ModelAndView studentHome() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
/*
    Student student = new Student().makeStudent();
        student.setPhone("322322323");
        student.setAddress("dasd");
    Set<Course> courses = new HashSet<>();
    Course course = new Course();
        course.setCourseName("ENCS 3121");
        courses.add(course);
        student.setCoursesForCurrentSemester(courses);
        studentService.saveStudent(student);
        */
       // User user = userService.findUserByEmail(auth.getName());
       // modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("studentMessage", "Content Available Only for Users with Student Role");
        modelAndView.setViewName("student/home");
        return modelAndView;
    }

    @RequestMapping(value = "/student/enroll", method = RequestMethod.GET)
    public ModelAndView studentEnroll() {
        ModelAndView modelAndView = new ModelAndView();
        // There are 74 courses which students can pick from
        int numCourses = 69;
        Course courseTable = new Course();
        String[] courses = new String[numCourses];

        // Get the course from database

        // Send the courses to the enroll page

        return modelAndView;
    }

    @RequestMapping(value = "/student/schedule", method = RequestMethod.GET)
    public ModelAndView studentSchedule() {
        ModelAndView modelAndView = new ModelAndView();

        return modelAndView;
    }

    @RequestMapping(value = "/student/grades", method = RequestMethod.GET)
    public ModelAndView studentGrades() {
        ModelAndView modelAndView = new ModelAndView();

        return modelAndView;
    }

    @RequestMapping(value = "/student/tuition", method = RequestMethod.GET)
    public ModelAndView studentTuition() {
        ModelAndView modelAndView = new ModelAndView();

        return modelAndView;
    }

    @RequestMapping(value = "/student/contactinfo", method = RequestMethod.GET)
    public ModelAndView studentContactInfo() {
        ModelAndView modelAndView = new ModelAndView();

        return modelAndView;
    }
}
