package com.model.sc;

import com.model.User;
import com.model.sc.enums.ProgramType;
import com.model.sc.enums.StudentStatus;

import javax.persistence.*;
import java.math.BigDecimal;
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
    @Column(name = "status")
    private StudentStatus stauts;

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

    /**
     * A method for setting the tuition of a student according to their status
     * @param status
     */
    public void setTuition(StudentStatus status) {
        double tuition = 0.0;
        double compulsuaryFeesPart = 371.23;
        double compulsuaryFeesFull = 489.26;
        double healthInsurance = 204.42;

        // Check the immigration status
        // Quebec Resident
        if(status.equals(StudentStatus.QCRESIDENT)) {
            // Find out if student is a full time or a part time student
            if(this.getCoursesForCurrentSemester().size() > 1)
                // Full time student
                tuition += 896.63;
            else if(this.getCoursesForCurrentSemester().size() == 1)
                // Part time student
                tuition += 597.75;
        }
        // Canadian and non-Quebec Resident
        else if(status.equals(StudentStatus.CANADIAN)) {
            // Find out if student is a full time or a part time student
            if(this.getCoursesForCurrentSemester().size() > 1)
                // Full time student
                tuition += 2776.05;
            else if(this.getCoursesForCurrentSemester().size() == 1)
                // Part time student
                tuition += 1850.70;
        }
        // International
        else if(status.equals(StudentStatus.INTERNATIONAL)) {
            healthInsurance = 732.64;
            // Find out if student is a full time or a part time student
            if(this.getCoursesForCurrentSemester().size() > 1)
                // Full time student
                tuition += 6140.03;
            else if(this.getCoursesForCurrentSemester().size() == 1)
                // Part time student
                tuition += 4093.35;
        }

        if(this.getCoursesForCurrentSemester().size() > 1)
            tuition += healthInsurance + compulsuaryFeesFull;
        else
            tuition += healthInsurance + compulsuaryFeesPart;

        this.tuition = new BigDecimal(tuition);
    }

    public void setNewTuition(BigDecimal tuition) {
        this.tuition = tuition;
    }

    public ProgramType getProgram() {return this.program;}

    public void setProgram(ProgramType program) {
        this.program = program;
    }

    public StudentStatus getStauts() {
        return stauts;
    }

    public void setStauts(StudentStatus stauts) {
        this.stauts = stauts;
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
