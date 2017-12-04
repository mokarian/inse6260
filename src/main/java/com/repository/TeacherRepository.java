package com.repository;

import com.model.sc.Student;
import com.model.sc.Teacher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by maysam.mokarian on 12/3/2017.
 */
@Repository("teacherRepository")
@Qualifier("teacherRepo")
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    Teacher findByEmail(String email);
}
