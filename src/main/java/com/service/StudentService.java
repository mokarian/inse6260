package com.service;

import com.model.Role;
import com.model.User;
import com.model.sc.Student;
import com.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by maysam.mokarian on 10/30/2017.
 */
@Service
public class StudentService {
    @Autowired
    @Qualifier("studentRepo")
    private StudentRepository studentRepository;

    public void saveStudent(Student student) {
       // studentRepository.save(student);
        studentRepository.saveAndFlush(student);
    }

    public Student findByEmail(String email) {
       return studentRepository.findByEmail(email);
    }

}
