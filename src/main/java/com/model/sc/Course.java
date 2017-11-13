package com.model.sc;


import javax.persistence.*;
import java.lang.reflect.Array;
import java.util.*;

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
    @Column(name = "dtype")
    private String dtype;
    @Column(name = "prerequisites")
    private String preRequisites;
    private String schedules;
    private String semester;
    private float grade;

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

    public String getPreRequisites() {

        return preRequisites;
    }

    public void setPreRequisites(String preRequisites) {
        this.preRequisites = preRequisites;
    }


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

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    public Set<Schedule> getSchedules() {
        Set<Schedule> schedulesSet = new HashSet<>();
        if (schedules == null) {
            return schedulesSet;
        }
        String[] scheduleArray = schedules.split("\\|");
        if (scheduleArray.length == 0) {
            return schedulesSet;
        }
        for (int i = 0; i < scheduleArray.length; i++) {
            Schedule schedule = new Schedule();
            String[] timeLineCourseTypeArray = scheduleArray[i].split(",");
            schedule.setTimeLine(Arrays.stream(TimeLine.values())
                    .filter(e -> e.name().equalsIgnoreCase(timeLineCourseTypeArray[0])).findAny().orElse(null));
            schedule.setCourseType(Arrays.stream(CourseType.values())
                    .filter(e -> e.name().equalsIgnoreCase(timeLineCourseTypeArray[1])).findAny().orElse(null));
            schedulesSet.add(schedule);
        }
        return schedulesSet;
    }

    public void setSchedules(Set<Schedule> scheduleSet) {

        List<Schedule> schedules = new ArrayList<>(scheduleSet);
        List<String> listOfSchedulesINString = new ArrayList<>();
        for (Schedule schedule : schedules) {
            listOfSchedulesINString.add(schedule.getTimeLine().name() + "," + schedule.getCourseType().name());
        }
        this.schedules = String.join("|", listOfSchedulesINString);
    }

    public Set<String> getTimelineSet() {
        Set<String> schedulesSet = new HashSet<>();
        if (schedules == null) {
            return schedulesSet;
        }
        String[] scheduleArray = schedules.split("\\|");
        if (scheduleArray.length == 0) {
            return schedulesSet;
        }
        for (int i = 0; i < scheduleArray.length; i++) {
            String[] timeLineCourseTypeArray = scheduleArray[i].split(",");
            schedulesSet.add(scheduleArray[i].split(",")[0]);
        }
        return schedulesSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;

        Course course = (Course) o;

        if (getCourse_id() != course.getCourse_id()) return false;
        if (Float.compare(course.getGrade(), getGrade()) != 0) return false;
        if (getCourseName() != null ? !getCourseName().equals(course.getCourseName()) : course.getCourseName() != null)
            return false;
        if (getDtype() != null ? !getDtype().equals(course.getDtype()) : course.getDtype() != null) return false;
        if (getPreRequisites() != null ? !getPreRequisites().equals(course.getPreRequisites()) : course.getPreRequisites() != null)
            return false;
        return getSemester() != null ? getSemester().equals(course.getSemester()) : course.getSemester() == null;
    }

    @Override
    public int hashCode() {
        int result = getCourse_id();
        result = 31 * result + (getCourseName() != null ? getCourseName().hashCode() : 0);
        result = 31 * result + (getDtype() != null ? getDtype().hashCode() : 0);
        result = 31 * result + (getPreRequisites() != null ? getPreRequisites().hashCode() : 0);
        result = 31 * result + (getSchedules() != null ? getSchedules().hashCode() : 0);
        result = 31 * result + (getSemester() != null ? getSemester().hashCode() : 0);
        result = 31 * result + (getGrade() != +0.0f ? Float.floatToIntBits(getGrade()) : 0);
        return result;
    }

    @Override
    public String toString() {
        String[] scheduleArray = schedules.split("\\|");
        String timeLine = "\n";
        if (scheduleArray.length != 0) {
            for (int i = 0; i < scheduleArray.length; i++) {
                timeLine = timeLine + "( "+String.join("  ) , (  ", scheduleArray) + " )";
            }
        }
        return
                courseName + ":  " + timeLine;
    }

//
//    @Override
//    public String toString() {
//        return "Course{" +
//                "course_id=" + course_id +
//                ", courseName='" + courseName + '\'' +
//                ", dtype='" + dtype + '\'' +
//                ", preRequisites='" + preRequisites + '\'' +
//                ", schedules='" + schedules + '\'' +
//                ", semester='" + semester + '\'' +
//                ", grade=" + grade +
//                '}';
//    }
}
