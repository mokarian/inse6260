package com.model.sc;

import com.model.Role;
import com.model.User;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * A teacher object
 *
 * @author Maysam Mokarian
 * @version 1.0
 * @since 24.10.2017
 */
@Entity
@Table(name = "teacher")
public class Teacher extends User {

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_coursehistory", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "coursehistory_id"))
    private Set<Course> courseHistory;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_course", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course>  coursesForCurrentSemester;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_student", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> students;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "teacher_role", joinColumns = @JoinColumn(name = "teacher_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;



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

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
    @Override
    public Set<Role> getRoles() {
        return roles;
    }
    @Override
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}
