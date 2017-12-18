package com.repository;

import com.model.Role;
import com.model.sc.Course;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A course repository interface
 *
 * @author Maysam Mokarian
 * @version 1.0
 * @since 28.11.2017
 */
@Repository("courseRepository")
@Qualifier("courseRepo")
public interface CourseRepository extends JpaRepository<Course, Integer> {
}
