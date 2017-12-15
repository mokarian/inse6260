package com.model.sc;

/**
 * A schedule object
 *
 * @author Maysam Mokarian
 * @version 1.0
 * @since 13.11.2017
 */
public class Schedule {

    private TimeLine timeLine;
    private CourseType courseType;

    public TimeLine getTimeLine() {
        return timeLine;
    }

    public void setTimeLine(TimeLine timeLine) {
        this.timeLine = timeLine;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }
}
