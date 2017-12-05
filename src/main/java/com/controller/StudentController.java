package com.controller;

import com.model.request.ContactInfoRequest;
import com.model.request.TuitionRequest;
import com.model.sc.Course;
import com.model.sc.Student;
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

import java.math.BigDecimal;
import java.util.*;

/**
 * A student controller
 *
 * @author Maysam Mokarian
 * @version 2.0
 * @since 21.09.2017
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
        List<String> listOfCoursesOffered = getCourseName(studentService.getCoursesOfferedThisSemester());
        List<Course> listOfCoursesOfferedFull = studentService.getCoursesOfferedThisSemester();

        modelAndView.addObject("coursesToAdd", listOfCoursesOffered);
        modelAndView.addObject("coursesToAddFull", listOfCoursesOfferedFull);


        modelAndView.setViewName("student/enroll");
        return modelAndView;
    }

    private List<String> getCourseName(List<Course> coursesOfferedThisSemester) {
        List<String> courses = new ArrayList<>();
        for(Course course:coursesOfferedThisSemester){
            courses.add(course.getCourseName());
        }
        return courses;
    }

    @RequestMapping(value = "student/drop/{courseId}", method = RequestMethod.GET)
    public ModelAndView studentDropPost(@PathVariable String courseId) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student user = studentService.findByEmail(auth.getName());
        String message = "";
        List<Course> courseListIterator = new ArrayList();
        courseListIterator.addAll(user.getCoursesForCurrentSemester());
        ListIterator<Course> iterator = courseListIterator.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().getCourseName().equalsIgnoreCase(courseId)) {
                iterator.remove();
                user.setCoursesForCurrentSemester(new HashSet<>(courseListIterator));
                studentService.saveStudent(user);
                message = "course(" + courseId + ") dropped successfully ";
                break;
            } else {
                message = "course(" + courseId + ") was not find in the list of courses ";
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
        modelAndView.setViewName("student/enroll");
        return modelAndView;
    }

    @RequestMapping(value = "student/enroll/{courseId}", method = RequestMethod.GET)
    public ModelAndView studentEnrollPost(@PathVariable String courseId) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student user = studentService.findByEmail(auth.getName());
        List<Course> courses =studentService.getCoursesOfferedThisSemester();
        String message = "";
        for(Course course:courses){
            if(course.getCourseName().equalsIgnoreCase(courseId) && noConflictsDetected( user.getCoursesForCurrentSemester(),course)){
                user.getCoursesForCurrentSemester().add(course);
                studentService.saveStudent(user);
                message = courseId + " added to your courses successfully";
            }
        }

        List<String> list = new ArrayList<>();
        for (Course course : user.getCoursesForCurrentSemester()) {
            list.add(course.getCourseName());
        }

        modelAndView.addObject("courses", list);

        modelAndView.addObject("message", message);
        List<String> listOfCoursesOffered = getCourseName(studentService.getCoursesOfferedThisSemester());
        List<Course> listOfCoursesOfferedFull = studentService.getCoursesOfferedThisSemester();

        modelAndView.addObject("coursesToAdd", listOfCoursesOffered);
        modelAndView.addObject("coursesToAddFull", listOfCoursesOfferedFull);
        modelAndView.setViewName("student/enroll");
        return modelAndView;
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

    @RequestMapping(value = "/student/schedule", method = RequestMethod.GET)
    public ModelAndView studentSchedule() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student user = studentService.findByEmail(auth.getName());

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

        modelAndView.addObject("tuition", user.getTuition().toString());
        modelAndView.setViewName("student/tuition");
        return modelAndView;
    }


    @RequestMapping(value = "/student/tuition", method = RequestMethod.POST)
    public ModelAndView studentTuitionPost(TuitionRequest stringRequest) {
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student user = studentService.findByEmail(auth.getName());
        String message = "";
        if (user.getTuition().subtract(new BigDecimal(stringRequest.getAmount())).signum() == -1) {
            message = "the amount entered is more than your tuition fee";
        } else if (user.getTuition().subtract(new BigDecimal(stringRequest.getAmount())).signum() == +1) {
            message = "the amount entered is not enough";
            user.setTuition(user.getTuition().subtract(new BigDecimal(stringRequest.getAmount())));
            studentService.saveStudent(user);
        } else {
            user.setTuition(new BigDecimal(0));
            studentService.saveStudent(user);
            message = "you successfully payed your tuition fee";
        }
        modelAndView.addObject("tuition", user.getTuition().toString());
        modelAndView.addObject("message", message);
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
        modelAndView.addObject("successMessage", "your Address and Phone changed successfully");
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
