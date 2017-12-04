package com.model.request;

import com.model.sc.Student;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * A unit test for Student Email Request
 *
 * @author Freyja Jokulsdottir
 * @version 1.0
 * @since 03.12.2017
 */
public class StudentEmailRequestTest {
    private Student student = new Student();

    @Before
    public void setUp() throws Exception {
        student.setEmail("freyja.jokulsdottir@concordia.ca");
    }

    @After
    public void tearDown() throws Exception {
        this.student = null;
    }

    @Test
    public void getStudentEmail() throws Exception {
        StudentEmailRequest req = new StudentEmailRequest();
        req.setStudentEmail(student.getEmail());

        Assert.assertEquals(student.getEmail(), req.getStudentEmail());
    }

}