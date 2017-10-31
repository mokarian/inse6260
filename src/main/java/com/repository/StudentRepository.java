package com.repository;

import com.model.sc.Student;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by maysam.mokarian on 10/30/2017.
 */
@Repository("studentRepository")
@Qualifier("studentRepo")
public interface StudentRepository  extends JpaRepository<Student, Integer> {

}
