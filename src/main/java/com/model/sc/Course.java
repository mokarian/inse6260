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
    @Column(name = "prerequisites")
    private String preRequisites;
    private int schedule_id;

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

    public List<String> getPreRequisites() {

        return Arrays.asList(preRequisites.split(","));
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
}
