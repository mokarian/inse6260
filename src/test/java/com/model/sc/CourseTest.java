package com.model.sc;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * A unit test for Course object
 *
 * @author Freyja Jokulsdottir
 * @version 1.0
 * @since 03.12.2017
 */
public class CourseTest {
    private Course course;

    @Before
    public void setUp() throws Exception {
        this.course = new Course();
    }

    @After
    public void tearDown() throws Exception {
        this.course = null;
    }

    @Test
    public void getCourse_id() throws Exception {
        int expectedID = 1;
        this.course.setCourse_id(expectedID);

        Assert.assertEquals(expectedID, this.course.getCourse_id());
    }

    @Test
    public void getCourseName() throws Exception {
        String expectedName = "INSE 6261";
        this.course.setCourseName(expectedName);

        Assert.assertEquals(expectedName, this.course.getCourseName());
    }

    @Test
    public void getPreRequisites() throws Exception {
        String expectedPrereq = "INSE 6261";
        this.course.setPreRequisites(expectedPrereq);

        Assert.assertEquals(expectedPrereq, this.course.getPreRequisites());
    }

    @Test
    public void getSection() throws Exception {
        int expectedSection = 1;
        this.course.setSection(expectedSection);

        Assert.assertEquals(expectedSection, this.course.getSection());
    }

    @Test
    public void getSemester() throws Exception {
        String expectedSemester = "FALL 2017";
        this.course.setSemester(expectedSemester);

        Assert.assertEquals(expectedSemester, this.course.getSemester());
    }

    @Test
    public void getGrade() throws Exception {
        float expectedGrade = 4.3f;
        this.course.setGrade(expectedGrade);

        Assert.assertEquals(expectedGrade, this.course.getGrade(), 0.0);
    }

    @Test
    public void getDtype() throws Exception {
        String expectedDtype = "COMP";
        this.course.setDtype(expectedDtype);

        Assert.assertEquals(expectedDtype, this.course.getDtype());
    }

    @Test
    public void equals() throws Exception {
        Course expectedCourse = this.course;

        Assert.assertTrue(expectedCourse.equals(this.course));
    }
}