package com.repository;

import com.model.sc.Student;
import com.model.sc.Teacher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A teacher repository interface
 *
 * @author Maysam Mokarian
 * @version 1.0
 * @since 03.12.2017
 */
@Repository("teacherRepository")
@Qualifier("teacherRepo")
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    Teacher findByEmail(String email);
}
