package com.model.sc;

import com.model.User;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by maysam.mokarian on 10/24/2017.
 */
public class Teacher extends User {

    @Column(name = "address")
    private String address;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "teacher_courseHistory", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "courseHistory_id"))
    private Set<CourseHistory> courseHistory;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "teacher_course", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course>  coursesForCurrentSemester;



}
