package com.model.sc;


import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by maysam.mokarian on 10/24/2017.
 */
@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int course_id;
    @Column(name = "coursename")
    private String courseName;
    @Column(name="dtype")
    private String dtype;
    @Column(name = "prerequisites")
    private String preRequisites;
    private int schedule_id;
    private String semester;
    private float grade;

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getPreRequisites() {

        return preRequisites;
    }

    public void setPreRequisites(String preRequisites) {
        this.preRequisites = preRequisites;
    }

    public int getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(int schedule_id) {
        this.schedule_id = schedule_id;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;

        Course course = (Course) o;

        if (getCourse_id() != course.getCourse_id()) return false;
        if (getSchedule_id() != course.getSchedule_id()) return false;
        if (Float.compare(course.getGrade(), getGrade()) != 0) return false;
        if (getCourseName() != null ? !getCourseName().equals(course.getCourseName()) : course.getCourseName() != null)
            return false;
        if (getPreRequisites() != null ? !getPreRequisites().equals(course.getPreRequisites()) : course.getPreRequisites() != null)
            return false;
        if (getSemester() != null ? !getSemester().equals(course.getSemester()) : course.getSemester() != null)
            return false;
        return getDtype() != null ? getDtype().equals(course.getDtype()) : course.getDtype() == null;
    }

    @Override
    public int hashCode() {
        int result = getCourse_id();
        result = 31 * result + (getCourseName() != null ? getCourseName().hashCode() : 0);
        result = 31 * result + (getPreRequisites() != null ? getPreRequisites().hashCode() : 0);
        result = 31 * result + getSchedule_id();
        result = 31 * result + (getSemester() != null ? getSemester().hashCode() : 0);
        result = 31 * result + (getGrade() != +0.0f ? Float.floatToIntBits(getGrade()) : 0);
        result = 31 * result + (getDtype() != null ? getDtype().hashCode() : 0);
        return result;
    }
}
