package com.model.sc;

import com.model.User;
import com.model.sc.Course;
import com.model.sc.CourseHistory;

import javax.persistence.*;
import java.math.BigDecimal;
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
    @Column(name = "phone")
    private String phone;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_coursehistory", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "coursehistory_id"))
    private Set<CourseHistory> courseHistory;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_course", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course> coursesForCurrentSemester;
    @Column(name = "tuition")
    private BigDecimal tuition;
 /*
    public Student makeStudent() {
        Student student = new Student();
        student.setEmail("test@concordia.com");
        student.setLastName("last");
        student.setName("first");
        student.setPassword("password");
        student.setUser_id(77);
        return student;
    }
    */

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

    public BigDecimal getTuition() {
        return tuition;
    }

    public void setTuition(BigDecimal tuition) {
        this.tuition = tuition;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
