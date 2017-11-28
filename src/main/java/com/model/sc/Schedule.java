package com.model.sc;

/**
 * Created by maysam.mokarian on 11/13/2017.
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
