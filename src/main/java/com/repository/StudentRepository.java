package com.repository;

import com.model.User;
import com.model.sc.Student;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A student repository interface
 *
 * @author Maysam Mokarian
 * @version 1.0
 * @since 30.10.2017
 */
@Repository("studentRepository")
@Qualifier("studentRepo")
public interface StudentRepository  extends JpaRepository<Student, Integer> {
    Student findByEmail(String email);
}
