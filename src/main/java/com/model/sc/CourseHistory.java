package com.model.sc;

import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

/**
 * Created by maysam.mokarian on 10/24/2017.
 */
@Entity
@Table(name = "coursehistory")
public class CourseHistory extends Course {

    private  String semester;
    private  float grade;

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

}