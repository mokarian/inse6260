package com.controller;

import com.model.User;
import com.model.request.GradeList;
import com.model.request.GradeRequest;
import com.model.sc.Course;
import com.model.sc.Student;
import com.model.sc.Teacher;
import com.repository.TeacherRepository;
import com.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private TeacherService teacherService;
    @Autowired
    private TeacherRepository teacherRepository;

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

        Teacher teacher = teacherService.findByEmail(auth.getName());
        GetList getList = new GetList(teacher).invoke();
        ModelAndView modelAndView = getList.getModelAndView();
        GradeList gradeList = getList.getGradeList();
        modelAndView.addObject("gradeList", gradeList);
        modelAndView.setViewName("professor/grades");
        modelAndView.addObject("gradeRequest", new GradeRequest());

        return modelAndView;
    }

    @RequestMapping(value = "/professor/grades", method = RequestMethod.POST)
    public ModelAndView professorGradesPost(GradeRequest gradeRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Teacher teacher = teacherService.findByEmail(auth.getName());

        List<Student> students = new ArrayList<>();
        students.addAll(teacher.getStudents());
        Student student = students.get(0);
        Set<Course> courseSet = student.getCoursesForCurrentSemester();
        List<Course> courses = new ArrayList<>();
        courses.addAll(courseSet);
        courses.get(0).setGrade(gradeRequest.getGrade());
        Set<Course> courses1 = new HashSet<>(courses);
        student.setCoursesForCurrentSemester(courses1);
        students.remove(0);
        students.add(student);

        teacher.setStudents(new HashSet<>(students));
        teacherRepository.save(teacher);
        GetList getList = new GetList(teacher).invoke();
        GradeList gradeList = getList.getGradeList();

        ModelAndView modelAndView = getList.getModelAndView();

        modelAndView.addObject("gradeList", gradeList);
        modelAndView.setViewName("professor/grades");
        modelAndView.addObject("gradeRequest", new GradeRequest());


        return modelAndView;
    }

    @RequestMapping(value = "/professor/contactinfo", method = RequestMethod.GET)
    public ModelAndView professorContactInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Teacher teacher = teacherService.findByEmail(auth.getName());
        GetList getList = new GetList(teacher).invoke();
        ModelAndView modelAndView = getList.getModelAndView();
        GradeList gradeList = getList.getGradeList();
        modelAndView.addObject("gradeList", gradeList);
        modelAndView.setViewName("professor/contactinfo");

        return modelAndView;
    }

    private class GetList {
        private Teacher teacher;
        private ModelAndView modelAndView;
        private GradeList gradeList;

        public GetList(Teacher teacher) {
            this.teacher = teacher;
        }

        public ModelAndView getModelAndView() {
            return modelAndView;
        }

        public GradeList getGradeList() {
            return gradeList;
        }

        public GetList invoke() {
            modelAndView = new ModelAndView();
            List<Student> students = new ArrayList<>();
            students.addAll(teacher.getStudents());
            gradeList = new GradeList();
            List<GradeRequest> gradeRequests = new ArrayList();
            for (Student student : students) {
                List<Course> courses = new ArrayList<>();
                courses.addAll(student.getCoursesForCurrentSemester());
                for (Course course : courses) {
                    GradeRequest gradeRequest = new GradeRequest();
                    gradeRequest.setCourse(course.getCourseName());
                    gradeRequest.setGrade(course.getGrade());
                    gradeRequest.setStudentEmail(student.getEmail());
                    gradeRequest.setContactInfo("ADDRESS:" + student.getAddress() + ", PHONE: " + student.getPhone());
                    gradeRequests.add(gradeRequest);
                }

            }
            gradeList.setGradeRequests(gradeRequests);
            return this;
        }
    }

    @RequestMapping(value = "/professor/contactinfo", method = RequestMethod.POST)
    public ModelAndView professorContactInfoPost() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Teacher teacher = teacherService.findByEmail(auth.getName());

        GetList getList = new GetList(teacher).invoke();
        GradeList gradeList = getList.getGradeList();
        ModelAndView modelAndView = getList.getModelAndView();

        modelAndView.addObject("gradeList", gradeList);
        modelAndView.setViewName("professor/contactinfo");

        return modelAndView;
    }
}
