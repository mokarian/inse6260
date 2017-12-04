package com.service;

import com.model.sc.Course;
import com.model.sc.Student;
import com.model.sc.Teacher;
import com.repository.CourseRepository;
import com.repository.StudentRepository;
import com.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maysam.mokarian on 12/3/2017.
 */
@Service
public class TeacherService {

    @Autowired
    @Qualifier("teacherRepo")
    TeacherRepository teacherRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    @Qualifier("studentRepo")
    private StudentRepository studentRepository;

    public List<Student> getListOfStudents() {
        List<Student>  students = new ArrayList<>();

        return students;

    }

    public Teacher findByEmail(String email) {
        return teacherRepository.findByEmail(email);
    }
}
