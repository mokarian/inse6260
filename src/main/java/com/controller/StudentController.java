package com.controller;

import com.model.User;
import com.model.request.ContactInfoRequest;
import com.model.request.TuitionRequest;
import com.model.sc.Course;
import com.model.sc.Schedule;
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
import org.thymeleaf.expression.Strings;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.*;

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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student user = studentService.findByEmail(auth.getName());

        List<String> list = new ArrayList<>();
        List<Course> listOfCourses = new ArrayList<>();
        listOfCourses.addAll(user.getCoursesForCurrentSemester());
        for (Course course : listOfCourses) {
            list.add(course.getCourseName());
        }
        modelAndView.addObject("courses", list);
        modelAndView.setViewName("student/enroll");
        return modelAndView;
    }

    @RequestMapping(value = "/student/enroll", method = RequestMethod.POST)
    public ModelAndView studentEnrollPost(String course) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student user = studentService.findByEmail(auth.getName());

        modelAndView.setViewName("student/enroll");
        return modelAndView;
    }

    @RequestMapping(value = "/student/schedule", method = RequestMethod.GET)
    public ModelAndView studentSchedule() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student user = studentService.findByEmail(auth.getName());
        List<String> list = new ArrayList<>();
        List<String> listOfCourses = new ArrayList<>();

        modelAndView.addObject("courses", user.getCoursesForCurrentSemester());
        modelAndView.setViewName("student/schedule");
        return modelAndView;
    }

    @RequestMapping(value = "/student/grades", method = RequestMethod.GET)
    public ModelAndView studentGrades() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student user = studentService.findByEmail(auth.getName());

        List<String> list = new ArrayList<>();
        List<Course> listOfCourses = new ArrayList<>();
        listOfCourses.addAll(user.getCoursesForCurrentSemester());
        for (Course course : listOfCourses) {
            float grade = course.getGrade();
            list.add(course.getCourseName() + ", GRADE:"
                    + (grade != 0f ?
                    String.valueOf(course.getGrade()) : "Not Posted"));
        }
        modelAndView.addObject("grades", list);
        modelAndView.setViewName("student/grades");
        return modelAndView;
    }

    @RequestMapping(value = "/student/tuition", method = RequestMethod.GET)
    public ModelAndView studentTuition() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("tuitionRequest", new TuitionRequest());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student user = studentService.findByEmail(auth.getName());

        modelAndView.addObject("tuition",user.getTuition().toString());
        modelAndView.setViewName("student/tuition");
        return modelAndView;
    }


    @RequestMapping(value = "/student/tuition", method = RequestMethod.POST)
    public ModelAndView studentTuitionPost(TuitionRequest tuitionRequest) {
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student user = studentService.findByEmail(auth.getName());
        String message="";
        if(user.getTuition().subtract(new BigDecimal(tuitionRequest.getAmount())).signum() == -1){
            message = "the amount entered is more than your tuition fee";
        }
        else if(user.getTuition().subtract(new BigDecimal(tuitionRequest.getAmount())).signum() == +1){
            message = "the amount entered is not enough";
        }
        else{
            user.setTuition(new BigDecimal(0));
            studentService.saveStudent(user);
            message="you successfully payed your tuition fee";
        }
        modelAndView.addObject("tuition",user.getTuition().toString());
        modelAndView.addObject("message",message);
        modelAndView.setViewName("student/tuition");
        return modelAndView;
    }

    @RequestMapping(value = "/student/contactinfo", method = RequestMethod.POST)
    public ModelAndView studentContactInfo(ContactInfoRequest contactInfoRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student student = studentService.findByEmail(auth.getName());
        student.setPhone(contactInfoRequest.getPhone());
        student.setAddress(contactInfoRequest.getAddress());
        studentService.saveStudent(student);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("phoneToShow", student.getPhone());
        modelAndView.addObject("addressToShow", student.getAddress());
        modelAndView.addObject("successMessage","your Address and Phone changed successfully" );
        modelAndView.setViewName("student/contactinfo");
        return modelAndView;
    }

    @RequestMapping(value = "/student/contactinfo", method = RequestMethod.GET)
    public ModelAndView studentContactInfoGet() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("contactInfoRequest", new ContactInfoRequest());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student user = studentService.findByEmail(auth.getName());
        modelAndView.addObject("phoneToShow", user.getPhone());
        modelAndView.addObject("addressToShow", user.getAddress());
        modelAndView.setViewName("student/contactinfo");

        return modelAndView;
    }
}
