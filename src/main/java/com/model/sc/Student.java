package com.model.sc;

import com.model.User;
import com.model.sc.enums.ProgramType;
import com.model.sc.enums.Semester;
import com.model.sc.enums.StudentStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;
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
    @Column(name = "program")
    private ProgramType program;
    @Column(name = "status")
    private StudentStatus status;

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

    public void setCoursesForSemester(Course course, String semester) {
        if(semester.equalsIgnoreCase("FALL_2017")) {
            Set<Course> courseToAdd = new HashSet<>();
            courseToAdd.addAll(coursesForCurrentSemester);
            courseToAdd.add(course);
            setCoursesForCurrentSemester(courseToAdd);
        }
        else {
            this.courseHistory.add(course);
        }
    }

    public void removeCoursesForSemester(Course course, String semester) {
        if(semester.equalsIgnoreCase("FALL_2017")) {
            this.coursesForCurrentSemester.remove(course);
        }
        this.courseHistory.remove(course);
    }

    public Set<Course> getCoursesForSemester(String semester) {
        if(semester.equalsIgnoreCase("FALL_2017"))
            return getCoursesForCurrentSemester();

        ArrayList<Course> courses = new ArrayList<>(this.courseHistory);
        Set<Course> semesterCourses = new HashSet<>();

        for(int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            if(course.getSemester().equalsIgnoreCase(semester)) {
                semesterCourses.add(course);
            }
        }

        return semesterCourses;
    }

    public BigDecimal getTuition() {
        return tuition;
    }

    /**
     * A method for setting the tuition of a student according to their status
     * @param status
     */
    public void setTuition(StudentStatus status, String semester) {
        double tuition = 0.0;
        double compulsoryFeesPart = 371.23;
        double compulsoryFeesFull = 489.26;
        double healthInsurance = 204.42;

        // Check the immigration status
        // Quebec Resident
        if(status.equals(StudentStatus.QCRESIDENT)) {
            // Find out if student is a full time or a part time student
            if(this.getCoursesForSemester(semester).size() > 1)
                // Full time student
                tuition += 896.63;
            else if(this.getCoursesForSemester(semester).size() == 1)
                // Part time student
                tuition += 597.75;
        }
        // Canadian and non-Quebec Resident
        else if(status.equals(StudentStatus.CANADIAN)) {
            // Find out if student is a full time or a part time student
            if(this.getCoursesForSemester(semester).size() > 1)
                // Full time student
                tuition += 2776.05;
            else if(this.getCoursesForSemester(semester).size() == 1)
                // Part time student
                tuition += 1850.70;
        }
        // International
        else if(status.equals(StudentStatus.INTERNATIONAL)) {
            healthInsurance = 732.64;
            // Find out if student is a full time or a part time student
            if(this.getCoursesForSemester(semester).size() > 1)
                // Full time student
                tuition += 6140.03;
            else if(this.getCoursesForSemester(semester).size() == 1)
                // Part time student
                tuition += 4093.35;
        }

        if(this.getCoursesForCurrentSemester().size() > 1)
            tuition += healthInsurance + compulsoryFeesFull;
        else
            tuition += healthInsurance + compulsoryFeesPart;

        this.tuition = new BigDecimal(tuition);
    }

    public double getFutureTuition() {
        double tuition = 0.0;
        double compulsoryFeesPart = 371.23;
        double compulsoryFeesFull = 489.26;
        double healthInsurance = 204.42;

        // Check the immigration status
        // Quebec Resident
        if(this.status.equals(StudentStatus.QCRESIDENT)) {
            // Find out if student is a full time or a part time student
            if(this.getCoursesForSemester(Semester.WINTER_2018.name()).size() > 1)
                // Full time student
                tuition += 896.63;
            else if(this.getCoursesForSemester(Semester.WINTER_2018.name()).size() == 1)
                // Part time student
                tuition += 597.75;
        }
        // Canadian and non-Quebec Resident
        else if(status.equals(StudentStatus.CANADIAN)) {
            // Find out if student is a full time or a part time student
            if(this.getCoursesForSemester(Semester.WINTER_2018.name()).size() > 1)
                // Full time student
                tuition += 2776.05;
            else if(this.getCoursesForSemester(Semester.WINTER_2018.name()).size() == 1)
                // Part time student
                tuition += 1850.70;
        }
        // International
        else if(status.equals(StudentStatus.INTERNATIONAL)) {
            healthInsurance = 732.64;
            // Find out if student is a full time or a part time student
            if(this.getCoursesForSemester(Semester.WINTER_2018.name()).size() > 1)
                // Full time student
                tuition += 6140.03;
            else if(this.getCoursesForSemester(Semester.WINTER_2018.name()).size() == 1)
                // Part time student
                tuition += 4093.35;
        }

        if(this.getCoursesForCurrentSemester().size() > 1)
            tuition += healthInsurance + compulsoryFeesFull;
        else
            tuition += healthInsurance + compulsoryFeesPart;

        return tuition;
    }

    public void setNewTuition(BigDecimal tuition) {
        this.tuition = tuition;
    }

    public ProgramType getProgram() {return this.program;}

    public void setProgram(ProgramType program) {
        this.program = program;
    }

    public StudentStatus getStatus() {
        return status;
    }

    public void setStatus(StudentStatus status) {
        this.status = status;
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
