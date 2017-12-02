package com.repository;

import com.model.Role;
import com.model.sc.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by maysam.mokarian on 11/28/2017.
 */
@Repository("courseRepository")
public interface CourseRepository extends JpaRepository<Course, Integer> {

}
