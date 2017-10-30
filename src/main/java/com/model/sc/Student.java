package com.model.sc;

import com.model.User;
import com.model.sc.Course;
import com.model.sc.CourseHistory;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by maysam.mokarian on 10/24/2017.
 */
@Entity
@Table(name = "student")
public class Student extends User {

    @Column(name = "address")
    private String address;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "student_courseHistory", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "courseHistory_id"))
    private Set<CourseHistory> courseHistory;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_course", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course>  coursesForCurrentSemester;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<CourseHistory> getCourseHistory() {
        return courseHistory;
    }

    public void setCourseHistory(Set<CourseHistory> courseHistory) {
        this.courseHistory = courseHistory;
    }

    public Set<Course> getCoursesForCurrentSemester() {
        return coursesForCurrentSemester;
    }

    public void setCoursesForCurrentSemester(Set<Course> coursesForCurrentSemester) {
        this.coursesForCurrentSemester = coursesForCurrentSemester;
    }
}
