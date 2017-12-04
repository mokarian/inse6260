package com.model.sc;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * A unit test for Teacher object
 *
 * @author Freyja Jokulsdottir
 * @version 1.0
 * @since 03.12.2017
 */
public class TeacherTest {
    private Teacher prof;

    @Before
    public void setUp() throws Exception {
        this.prof = new Teacher();
    }

    @After
    public void tearDown() throws Exception {
        this.prof = null;
    }

    @Test
    public void getCourseHistory() throws Exception {
        Course course1 = new Course();
        course1.setCourseName("INSE 6261");
        Course course2 = new Course();
        course2.setCourseName("COMP 6651");
        Set<Course> expectedCourseHistory = new HashSet<>();
        expectedCourseHistory.add(course1);
        expectedCourseHistory.add(course2);

        this.prof.setCourseHistory(expectedCourseHistory);

        Assert.assertEquals(expectedCourseHistory, this.prof.getCourseHistory());
    }

    @Test
    public void getCoursesForCurrentSemester() throws Exception {
    }
}