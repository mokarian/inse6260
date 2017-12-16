package com.model.sc;

import com.model.User;
import com.model.sc.Course;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

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

    public Map<String, Float> getAnnualGPA() {
        Map<String, Float> gpa = new HashMap();
        String year = "";
        for (Course course : courseHistory) {
            year = (course.getSemester().substring(course.getSemester().length() - 4, course.getSemester().length()));
            if (gpa.containsKey(year)) {
                Float previousGrade = gpa.get(year);
                gpa.put(year, (previousGrade + course.getGrade()) / 2);
            } else {
                gpa.put(year, course.getGrade());
            }

        }
        List<Course> courses = new ArrayList<>(coursesForCurrentSemester);

        for (Course course : courses) {
            if (course.getGrade() != 0.0f) {
                if (gpa.containsKey(year)) {
                    Float previousGrade = gpa.get(year);
                    gpa.put(year, (previousGrade + course.getGrade()) / 2);
                } else {
                    gpa.put(year, course.getGrade());
                }
            }
        }
        return gpa;
    }

    public String getCumulativeGPA() {

        float sum = 0;
        int counter = 0;
        for (Course course : courseHistory) {
            sum = sum + course.getGrade();
            counter++;
        }
        List<Course> courses = new ArrayList<>(coursesForCurrentSemester);
        for (Course course : courses) {
            if (course.getGrade() != 0.0f) {
                sum = sum + course.getGrade();
                counter++;
            }
        }
        return String.format("%.2f", sum / counter);
    }
}
