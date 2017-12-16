package com.model.sc;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * A unit test for Student object
 *
 * @author Freyja Jokulsdottir
 * @version 1.0
 * @since 03.12.2017
 */
public class StudentTest {
    private Student student;

    @Before
    public void setUp() throws Exception {
        this.student = new Student();
    }

    @After
    public void tearDown() throws Exception {
        this.student = null;
    }

    @Test
    public void getAddress() throws Exception {
        String expectedAddress = "91 University Avenue, Montreal, H3K 1Z2, Quebec";
        this.student.setAddress(expectedAddress);

        Assert.assertEquals(expectedAddress, this.student.getAddress());
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

        this.student.setCourseHistory(expectedCourseHistory);

        Assert.assertEquals(expectedCourseHistory, this.student.getCourseHistory());
    }

    @Test
    public void getCoursesForCurrentSemester() throws Exception {
    }

    @Test
    public void getTuition() throws Exception {
        BigDecimal expectedTuition = new BigDecimal(7500.00);
        this.student.setNewTuition(expectedTuition);

        Assert.assertEquals(expectedTuition, this.student.getTuition());
    }

    @Test
    public void getPhone() throws Exception {
        String expectedPhone = "(514)888-0000";
        this.student.setPhone(expectedPhone);

        Assert.assertEquals(expectedPhone, this.student.getPhone());
    }
}