package com.model.sc;


import com.model.sc.enums.CourseType;
import com.model.sc.enums.TimeLine;

import javax.persistence.*;
import java.util.*;

/**
 * A Course Object
 *
 * @author Maysam Mokarian
 * @version 2.0
 * @since 24.10.2017
 */
@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int course_id;
    @Column(name = "coursename")
    private String courseName;
    @Column(name = "dtype")
    private String dtype;
    @Column(name = "prerequisites")
    private String preRequisites;
    @Column (name = "section")
    private int section;
    private String schedules;
    private String semester;
    private float grade;

    /**
     * A getter for course ID
     * @return course ID
     */
    public int getCourse_id() {
        return course_id;
    }

    /**
     * A setter for course ID
     * @param course_id
     */
    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    /**
     * A getter for course name
     * @return the course name
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * A setter for course name
     * @param courseName
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * A getter for the prerequisite of a course
     * @return the prerequisite of the course
     */
    public String getPreRequisites() {

        return preRequisites;
    }

    /**
     * A setter for prerequisite of a course
     * @param preRequisites
     */
    public void setPreRequisites(String preRequisites) {
        this.preRequisites = preRequisites;
    }

    /**
     * A getter for the section of a course
     * @return the course section
     */
    public int getSection() {
        return section;
    }

    /**
     * A setter for a section of a course
     * @param section
     */
    public void setSection(int section) {
        this.section = section;
    }

    /**
     * A getter for the semester the course is taught
     * @return the semester
     */
    public String getSemester() {
        return semester;
    }

    /**
     * A setter for the semester the course is taught
     * @param semester
     */
    public void setSemester(String semester) {
        this.semester = semester;
    }

    /**
     * A getter for the grade a student has for a course
     * @return grade
     */
    public float getGrade() {
        return grade;
    }

    /**
     * A setter for a student's grade of a course
     * @param grade
     */
    public void setGrade(float grade) {
        this.grade = grade;
    }

    /**
     * A getter for the data type of a course
     * @return data type
     */
    public String getDtype() {
        return dtype;
    }

    /**
     * A setter for the data type of a course
     * @param dtype
     */
    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    /**
     * A getter for a set fo schedules
     * @return a set of schedules
     */
    public Set<Schedule> getSchedules() {
        Set<Schedule> schedulesSet = new HashSet<>();
        if (schedules == null) {
            return schedulesSet;
        }
        String[] scheduleArray = schedules.split("\\|");
        if (scheduleArray.length == 0) {
            return schedulesSet;
        }
        for (int i = 0; i < scheduleArray.length; i++) {
            Schedule schedule = new Schedule();
            String[] timeLineCourseTypeArray = scheduleArray[i].split(",");
            schedule.setTimeLine(Arrays.stream(TimeLine.values())
                    .filter(e -> e.name().equalsIgnoreCase(timeLineCourseTypeArray[0])).findAny().orElse(null));
            schedule.setCourseType(Arrays.stream(CourseType.values())
                    .filter(e -> e.name().equalsIgnoreCase(timeLineCourseTypeArray[1])).findAny().orElse(null));
            schedulesSet.add(schedule);
        }
        return schedulesSet;
    }

    /**
     * A setter for a set of schedules
     * @param scheduleSet
     */
    public void setSchedules(Set<Schedule> scheduleSet) {

        List<Schedule> schedules = new ArrayList<>(scheduleSet);
        List<String> listOfSchedulesINString = new ArrayList<>();
        for (Schedule schedule : schedules) {
            listOfSchedulesINString.add(schedule.getTimeLine().name() + "," + schedule.getCourseType().name());
        }
        this.schedules = String.join("|", listOfSchedulesINString);
    }

    /**
     * A setter for a time line set
     * @return a set of string time lines
     */
    public Set<String> getTimelineSet() {
        Set<String> schedulesSet = new HashSet<>();
        if (schedules == null) {
            return schedulesSet;
        }
        String[] scheduleArray = schedules.split("\\|");
        if (scheduleArray.length == 0) {
            return schedulesSet;
        }
        for (int i = 0; i < scheduleArray.length; i++) {
            String[] timeLineCourseTypeArray = scheduleArray[i].split(",");
            schedulesSet.add(scheduleArray[i].split(",")[0]);
        }
        return schedulesSet;
    }

    /**
     * A overriden equals method for course
     * @param o
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;

        Course course = (Course) o;

        if (getCourse_id() != course.getCourse_id()) return false;
        if (Float.compare(course.getGrade(), getGrade()) != 0) return false;
        if (getCourseName() != null ? !getCourseName().equals(course.getCourseName()) : course.getCourseName() != null)
            return false;
        if (getDtype() != null ? !getDtype().equals(course.getDtype()) : course.getDtype() != null) return false;
        if (getPreRequisites() != null ? !getPreRequisites().equals(course.getPreRequisites()) : course.getPreRequisites() != null)
            return false;
        if (getSchedules() != null ? !getSchedules().equals(course.getSchedules()) : course.getSchedules() != null)
            return false;
        return getSemester() != null ? getSemester().equals(course.getSemester()) : course.getSemester() == null;
    }

    /**
     * An overriden hashCode method for course
     * @return int hashcode
     */
    @Override
    public int hashCode() {
        int result = getCourse_id();
        result = 31 * result + (getCourseName() != null ? getCourseName().hashCode() : 0);
        result = 31 * result + (getDtype() != null ? getDtype().hashCode() : 0);
        result = 31 * result + (getPreRequisites() != null ? getPreRequisites().hashCode() : 0);
        result = 31 * result + (getSchedules() != null ? getSchedules().hashCode() : 0);
        result = 31 * result + (getSemester() != null ? getSemester().hashCode() : 0);
        result = 31 * result + (getGrade() != +0.0f ? Float.floatToIntBits(getGrade()) : 0);
        return result;
    }

    /**
     * An overriden toString method for course
     * @return String with course information
     */
    @Override
    public String toString() {
        String[] scheduleArray = schedules.split("\\|");
        String timeLine = "\n";
        if (scheduleArray.length != 0) {
            for (int i = 0; i < scheduleArray.length; i++) {
                timeLine = timeLine + "( "+String.join("  ) , (  ", scheduleArray) + " )";
            }
        }
        return
                courseName +" " +this.section + ":  " + timeLine;
    }
}
