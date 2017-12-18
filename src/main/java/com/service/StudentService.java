package com.service;

import com.model.sc.Course;
import com.model.sc.Student;
import com.repository.CourseRepository;
import com.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * A service class for student
 *
 * @author Maysam Mokarian
 * @version 1.0
 * @since 30.10.2017
 */
@Service
public class StudentService {
    @Autowired
    @Qualifier("studentRepo")
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;

    public void saveStudent(Student student) {
       // studentRepository.save(student);
        studentRepository.saveAndFlush(student);
    }

    public List<Course> getCoursesOfferedThisSemester(String semester){
        List<Course> courses= courseRepository.findAll();
        List<Course> coursesThisSemester= new ArrayList<>();
        for(Course course:courses){
            if(semester.equalsIgnoreCase(course.getSemester())){
                coursesThisSemester.add(course);
            }
        }
        return coursesThisSemester;
    }

    public Student findByEmail(String email) {
       return studentRepository.findByEmail(email);
    }
}
