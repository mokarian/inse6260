package com.service;

import com.model.sc.Course;
import com.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * A service for courses to update capacity and waitlist
 *
 * @author Freyja Jokulsdottir
 * @version 1.0
 * @since 18.12.2017
 */
@Service
public class CourseService {
    @Autowired
    @Qualifier("courseRepo")
    private CourseRepository courseRepository;

    public void saveCourse(Course course) {
        courseRepository.saveAndFlush(course);
    }
}
