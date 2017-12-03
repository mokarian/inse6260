package com.controller;

import com.model.User;
import com.model.request.StudentEmailRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by freyja.jokulsdottir on 9/21/2017.
 */
@Controller
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private StudentService studentService;
    @RequestMapping(value = "admin/home", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

    @RequestMapping(value = "admin/enroll", method = RequestMethod.GET)
    public ModelAndView adminEnroll() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("studentEmailRequest", new StudentEmailRequest());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        modelAndView.setViewName("admin/enroll");
        return modelAndView;
    }

    @RequestMapping(value = "admin/enroll", method = RequestMethod.POST)
    public ModelAndView adminEnrollPost(StudentEmailRequest emailRequest) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/enroll");

        String email ="";
        email = emailRequest.getStudentEmail();

        try {
            Student student = studentService.findByEmail(email);
            return adminFoundStudentEnroll(student);
        } catch (NullPointerException e) {
            e.getMessage();
            return modelAndView;
        }
    }

    @RequestMapping(value = "admin/foundstudentenroll}", method = RequestMethod.GET)
    public ModelAndView adminFoundStudentEnroll(Student student) {
        ModelAndView modelAndView = new ModelAndView();

        String name = student.getName() +" " +student.getLastName();
        modelAndView.addObject("studentName", name);

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

        modelAndView.setViewName("admin/foundstudentenroll");
        return modelAndView;
    }

    @RequestMapping(value = "admin/foundstudentenroll", method = RequestMethod.POST)
    public ModelAndView adminFoundStudentEnrollPost(Student student) {
        ModelAndView modelAndView = new ModelAndView();
        String courseId = "";
        String message = "";

        String name = student.getName() +" " +student.getLastName();

        if(!courseId.equals("")) {
            List<Course> courses = studentService.getCoursesOfferedThisSemester();

            for (Course course : courses) {
                if (course.getCourseName().equalsIgnoreCase(courseId) && noConflictsDetected(student.getCoursesForCurrentSemester(), course)) {
                    student.getCoursesForCurrentSemester().add(course);
                    studentService.saveStudent(student);
                    message = courseId + " added to " + name + "'s courses successfully";
                }
            }
        }

        modelAndView.addObject("message", message);
        modelAndView.setViewName("admin/foundstudentenroll");
        return modelAndView;
    }

    private List<String> getCourseName(List<Course> coursesOfferedThisSemester) {
        List<String> courses = new ArrayList<>();
        for(Course course:coursesOfferedThisSemester){
            courses.add(course.getCourseName());
        }
        return courses;
    }

    private boolean noConflictsDetected(Set<Course> coursesForCurrentSemester, Course course) {
        List<Course> courses = new ArrayList<>(coursesForCurrentSemester);
        for(Course c:courses){
            if(c.getSchedules().contains(course.getSchedules())){
                return false;
            }
        }
        return true;
    }
}
