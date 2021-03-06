package com.controller;

import com.model.Role;
import com.model.User;
import com.model.request.SemesterRequest;
import com.model.request.StudentEmailRequest;
import com.model.sc.Course;
import com.model.sc.Student;
import com.model.sc.enums.Semester;
import com.repository.CourseRepository;
import com.repository.RoleRepository;
import com.service.CourseService;
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
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CourseService courseService;

    private Student student;
    private List<Course> courseListIterator = new ArrayList<>();
    private String semesterToEnroll;

    @RequestMapping(value = "admin/home", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findUserByEmail(auth.getName());
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
            Role userRole = roleRepository.findByRole("STUDENT");
            this.student.setRoles(new HashSet<>(Arrays.asList(userRole)));
            return adminChooseTerm();
        } catch (NullPointerException e) {
            e.getMessage();
            return modelAndView;
        }
    }

    @RequestMapping(value = "/admin/chooseterm", method = RequestMethod.GET)
    public ModelAndView adminChooseTerm() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("semesterRequest", new SemesterRequest());

        modelAndView.setViewName("admin/chooseterm");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/chooseterm", method = RequestMethod.POST)
    public ModelAndView adminChooseTermPost(SemesterRequest semesterRequest) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/chooseterm");

        String semester ="";
        semester = semesterRequest.getSemester();
        if(semester.contains("F")) {
            semester = Semester.FALL_2017.name();
            this.semesterToEnroll = semester;
            return adminFoundStudentEnroll(semester);
        }
        if(semester.contains("W")) {
            semester = Semester.WINTER_2018.name();
            this.semesterToEnroll = semester;
            return adminFoundStudentEnroll(semester);
        }
        if(semester.contains("S")) {
            semester = Semester.SUMMER_2018.name();
            this.semesterToEnroll = semester;
            return adminFoundStudentEnroll(semester);
        }

        return modelAndView;
    }

    @RequestMapping(value = "admin/foundstudentenroll", method = RequestMethod.GET)
    public ModelAndView adminFoundStudentEnroll(String semester) {
        ModelAndView modelAndView = new ModelAndView();

        String name = this.student.getName() +" " +this.student.getLastName();
        modelAndView.addObject("studentName", name);

        List<String> list = new ArrayList<>();

        List<Course> listOfCourses = new ArrayList<>();
        listOfCourses.addAll(this.student.getCoursesForSemester(this.semesterToEnroll));
        for (Course course : listOfCourses) {
            list.add(course.getCourseName());
        }
        modelAndView.addObject("courses", list);

        List<String> listOfCoursesOffered = getCourseName(studentService.getCoursesOfferedThisSemester(semester));
        List<Course> listOfCoursesOfferedFull = studentService.getCoursesOfferedThisSemester(semester);

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
            List<Course> courses = studentService.getCoursesOfferedThisSemester(this.semesterToEnroll);

            for (Course course : courses) {
                if (course.getCourseName().equalsIgnoreCase(courseId)) {
                    if (noConflictsDetected(this.student.getCoursesForSemester(this.semesterToEnroll), course)) {
                        this.student.setCoursesForSemester(course, this.semesterToEnroll);
                        studentService.saveStudent(this.student);
                        if(course.getEnrollementTotal() >= course.getCapacity()) {
                            course.setWaitlistTotal(course.getWaitlistTotal() + 1);
                            message = name +" has been added to the waitlist of " +courseId + " successfully";
                        }
                        else {
                            course.setEnrollementTotal(course.getEnrollementTotal() + 1);
                            message = courseId + " added to " + name + "'s courses successfully";
                        }
                        courseService.saveCourse(course);
                    } else
                        message = courseId + " could not be added to " + name + "'s courses";
                }
            }
        }

        modelAndView.addObject("message", message);

        List<String> list = new ArrayList<>();

        List<Course> listOfCourses = new ArrayList<>();
        listOfCourses.addAll(this.student.getCoursesForSemester(this.semesterToEnroll));
        for (Course course : listOfCourses) {
            list.add(course.getCourseName());
        }
        modelAndView.addObject("courses", list);

        List<String> listOfCoursesOffered = getCourseName(studentService.getCoursesOfferedThisSemester(this.semesterToEnroll));
        List<Course> listOfCoursesOfferedFull = studentService.getCoursesOfferedThisSemester(this.semesterToEnroll);

        modelAndView.addObject("coursesToAdd", listOfCoursesOffered);
        modelAndView.addObject("coursesToAddFull", listOfCoursesOfferedFull);

        modelAndView.setViewName("admin/foundstudentenroll");
        return modelAndView;
    }

    @RequestMapping(value = "admin/drop/{courseId}", method = RequestMethod.GET)
    public ModelAndView adminDrop(@PathVariable String courseId) {
        ModelAndView modelAndView = new ModelAndView();

        String name = this.student.getName() +" " +this.student.getLastName();
        modelAndView.addObject("studentName", name);

        String message = "";
        List<Course> courseListIterator = new ArrayList();
        courseListIterator.addAll(this.student.getCoursesForSemester(this.semesterToEnroll));
        ListIterator<Course> iterator = courseListIterator.listIterator();
        while (iterator.hasNext()) {
            Course course = iterator.next();
            if (course.getCourseName().equalsIgnoreCase(courseId)) {
                iterator.remove();
                this.student.removeCoursesForSemester(course, this.semesterToEnroll);
                studentService.saveStudent(this.student);
                if(course.getEnrollementTotal() >= course.getCapacity())
                    course.setWaitlistTotal(course.getWaitlistTotal()-1);
                else
                    course.setEnrollementTotal(course.getEnrollementTotal()-1);
                courseService.saveCourse(course);
                message = "course(" + courseId + ") dropped successfully ";
                break;
            } else {
                message = "course(" + courseId + ") was not found in the list of courses ";
            }
        }

        List<String> list = new ArrayList<>();
        for (Course course : student.getCoursesForSemester(this.semesterToEnroll)) {
            list.add(course.getCourseName());
        }

        modelAndView.addObject("courses", list);

        modelAndView.addObject("message", message);
        List<String> listOfCoursesOffered = getCourseName(studentService.getCoursesOfferedThisSemester(this.semesterToEnroll));
        List<Course> listOfCoursesOfferedFull = studentService.getCoursesOfferedThisSemester(this.semesterToEnroll);

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
        if(course.getWaitlistTotal() >= course.getWaitlistCapacity())
            return false;
        for(Course c:courses){
            if(c.getSchedules().contains(course.getSchedules())){
                return false;
            }
            if(c.getCourseName().equals(course.getCourseName()))
                return false;
        }
        return true;
    }

    @RequestMapping(value = "admin/modifycoursesection", method = RequestMethod.GET)
    public ModelAndView adminModifyCourseSection() {
        ModelAndView modelAndView = new ModelAndView();

        this.courseListIterator.addAll(studentService.getCoursesOfferedThisSemester(this.semesterToEnroll));

        List<String> listOfCoursesOffered = getCourseName(studentService.getCoursesOfferedThisSemester(this.semesterToEnroll));
        List<Course> listOfCoursesOfferedFull = studentService.getCoursesOfferedThisSemester(this.semesterToEnroll);

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
