package com.model.sc;

import com.model.User;
import com.model.sc.Course;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * A student object
 *
 * @author Maysam Mokarian
 * @version 1.0
 * @since 24.10.2017
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
    private Set<Course> courseHistory;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_course", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course> coursesForCurrentSemester;
    @Column(name = "tuition")
    private BigDecimal tuition;
    @Column(name = "program")
    private ProgramType program;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Course> getCourseHistory() {
        return courseHistory;
    }

    public void setCourseHistory(Set<Course> courseHistory) {
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

    public ProgramType getProgram() {return this.program;}

    public void setProgram(ProgramType program) {
        this.program = program;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Student{" +
                "address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", courseHistory=" + courseHistory +
                ", coursesForCurrentSemester=" + coursesForCurrentSemester +
                ", tuition=" + tuition +
                '}';
    }
}
