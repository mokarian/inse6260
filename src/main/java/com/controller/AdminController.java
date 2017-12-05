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
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * A admin/advisor controller
 *
 * @author Freyja Jokulsdottir
 * @version 1.0
 * @since 03.12.2017
 */
@Controller
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private StudentService studentService;

    private Student student;
    private List<Course> courseListIterator = new ArrayList<>();

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
            this.student = studentService.findByEmail(email);
            return adminFoundStudentEnroll();
        } catch (NullPointerException e) {
            e.getMessage();
            return modelAndView;
        }
    }

    @RequestMapping(value = "admin/foundstudentenroll/{courseId}", method = RequestMethod.GET)
    public ModelAndView adminFoundStudentEnroll() {
        ModelAndView modelAndView = new ModelAndView();

        String name = this.student.getName() +" " +this.student.getLastName();
        modelAndView.addObject("studentName", name);

        List<String> list = new ArrayList<>();

        List<Course> listOfCourses = new ArrayList<>();
        listOfCourses.addAll(this.student.getCoursesForCurrentSemester());
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

    @RequestMapping(value = "admin/add/{courseId}", method = RequestMethod.GET)
    public ModelAndView adminAdd(@PathVariable String courseId) {
        ModelAndView modelAndView = new ModelAndView();

        String name = this.student.getName() +" " +this.student.getLastName();
        modelAndView.addObject("studentName", name);

        String message = "";

        if(!courseId.equals("")) {
            List<Course> courses = studentService.getCoursesOfferedThisSemester();

            for (Course course : courses) {
                if (course.getCourseName().equalsIgnoreCase(courseId) && noConflictsDetected(this.student.getCoursesForCurrentSemester(), course)) {
                    this.student.getCoursesForCurrentSemester().add(course);
                    studentService.saveStudent(this.student);
                    message = courseId + " added to " + name + "'s courses successfully";
                }
            }
        }

        modelAndView.addObject("message", message);

        List<String> list = new ArrayList<>();

        List<Course> listOfCourses = new ArrayList<>();
        listOfCourses.addAll(this.student.getCoursesForCurrentSemester());
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

    @RequestMapping(value = "admin/drop/{courseId}", method = RequestMethod.GET)
    public ModelAndView adminDrop(@PathVariable String courseId) {
        ModelAndView modelAndView = new ModelAndView();

        String message = "";
        List<Course> courseListIterator = new ArrayList();
        courseListIterator.addAll(this.student.getCoursesForCurrentSemester());
        ListIterator<Course> iterator = courseListIterator.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().getCourseName().equalsIgnoreCase(courseId)) {
                iterator.remove();
                this.student.setCoursesForCurrentSemester(new HashSet<>(courseListIterator));
                studentService.saveStudent(this.student);
                message = "course(" + courseId + ") dropped successfully ";
                break;
            } else {
                message = "course(" + courseId + ") was not found in the list of courses ";
            }
        }

        List<String> list = new ArrayList<>();
        for (Course course : courseListIterator) {
            list.add(course.getCourseName());
        }

        modelAndView.addObject("courses", list);

        modelAndView.addObject("message", message);
        List<String> listOfCoursesOffered = getCourseName(studentService.getCoursesOfferedThisSemester());
        List<Course> listOfCoursesOfferedFull = studentService.getCoursesOfferedThisSemester();

        modelAndView.addObject("coursesToAdd", listOfCoursesOffered);
        modelAndView.addObject("coursesToAddFull", listOfCoursesOfferedFull);

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

    @RequestMapping(value = "admin/modifycoursesection", method = RequestMethod.GET)
    public ModelAndView adminModifyCourseSection() {
        ModelAndView modelAndView = new ModelAndView();

        this.courseListIterator.addAll(studentService.getCoursesOfferedThisSemester());

        List<String> listOfCoursesOffered = getCourseName(studentService.getCoursesOfferedThisSemester());
        List<Course> listOfCoursesOfferedFull = studentService.getCoursesOfferedThisSemester();

        modelAndView.addObject("coursesToModify", listOfCoursesOffered);
        modelAndView.addObject("coursesToModifyFull", listOfCoursesOfferedFull);

        modelAndView.setViewName("admin/modifycoursesection");
        return modelAndView;
    }

    @RequestMapping(value = "admin/modifycoursesection/add/{courseId}", method = RequestMethod.GET)
    public ModelAndView adminAddCourseSection(@PathVariable String courseId) {
        ModelAndView modelAndView = new ModelAndView();
        String message = "";

        ListIterator<Course> iterator = this.courseListIterator.listIterator();
        while (iterator.hasNext()) {
            Course course = iterator.next();
            String name = course.getCourseName();
            if (name.equalsIgnoreCase(courseId)) {
                Course courseSection = course;
                iterator.add(courseSection);
                message = "course(" + courseId + ") section added successfully ";
                break;
            } else {
                message = "course(" + courseId + ") was not found in the list of courses ";
            }
        }

        List<String> list = new ArrayList<>();
        for (Course course : this.courseListIterator) {
            list.add(course.getCourseName());
        }

        modelAndView.addObject("message", message);

        modelAndView.addObject("coursesToModify", list);
        modelAndView.addObject("coursesToModifyFull", this.courseListIterator);

        modelAndView.setViewName("admin/modifycoursesection");
        return modelAndView;
    }

    @RequestMapping(value = "admin/modifycoursesection/remove/{courseId}", method = RequestMethod.GET)
    public ModelAndView adminRemoveCourseSection(@PathVariable String courseId) {
        ModelAndView modelAndView = new ModelAndView();
        String message = "";

        ListIterator<Course> iterator = this.courseListIterator.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().getCourseName().equalsIgnoreCase(courseId)) {
                iterator.remove();
                message = "course(" + courseId + ") section removed successfully ";
                break;
            } else {
                message = "course(" + courseId + ") was not found in the list of courses ";
            }
        }

        List<String> list = new ArrayList<>();
        for (Course course : this.courseListIterator) {
            list.add(course.getCourseName());
        }

        modelAndView.addObject("message", message);

        modelAndView.addObject("coursesToModify", list);
        modelAndView.addObject("coursesToModifyFull", this.courseListIterator);

        modelAndView.setViewName("admin/modifycoursesection");
        return modelAndView;
    }
}
