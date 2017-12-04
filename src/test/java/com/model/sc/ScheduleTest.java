package com.model.sc;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * A unit test for Schedule object
 *
 * @author Freyja Jokulsdottir
 * @version 1.0
 * @since 03.12.2017
 */
public class ScheduleTest {
    private Schedule schedule;

    @Before
    public void setUp() throws Exception {
        this.schedule = new Schedule();
    }

    @After
    public void tearDown() throws Exception {
        this.schedule = null;
    }

    @Test
    public void getTimeLine() throws Exception {
        this.schedule.setTimeLine(Arrays.stream(TimeLine.values()).filter(e -> e.name().equalsIgnoreCase(
                "MONDAY_9AM_TO_12PM")).findAny().orElse(null));

        Assert.assertNotNull(this.schedule.getTimeLine());
        Assert.assertEquals(Arrays.stream(TimeLine.values()).filter(e -> e.name().equalsIgnoreCase(
                "MONDAY_9AM_TO_12PM")).findAny().orElse(null), this.schedule.getTimeLine());
    }

    @Test
    public void getCourseType() throws Exception {
        this.schedule.setCourseType(Arrays.stream(CourseType.values()).filter(e -> e.name().equalsIgnoreCase(
                "LECTURE")).findAny().orElse(null));

        Assert.assertNotNull(this.schedule.getCourseType());
        Assert.assertEquals(Arrays.stream(CourseType.values()).filter(e -> e.name().equalsIgnoreCase(
                "LECTURE")).findAny().orElse(null), this.schedule.getCourseType());
    }

}