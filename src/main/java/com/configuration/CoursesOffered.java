package com.configuration;

import com.model.sc.Course;
import com.model.sc.Schedule;
import com.model.sc.enums.CourseType;
import com.model.sc.enums.Semester;
import com.model.sc.enums.TimeLine;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CoursesOffered {
    public Set<Course> createCoursesOfferedThisSemester() {
        Set<Course> courseHistories = new HashSet<>();
        courseHistories.addAll(this.createCoursesFall2017());
        courseHistories.addAll(this.createCoursesWinter2018());
        courseHistories.addAll(this.createCoursesSummer2018());

        return courseHistories;
    }

    private Set<Schedule> getSchedules(CourseType courseType1, TimeLine timeline1, CourseType courseType2, TimeLine timeLine2) {
        Schedule schedule = new Schedule();
        schedule.setCourseType(courseType1);
        schedule.setTimeLine(timeline1);
        Schedule schedule1 = new Schedule();
        schedule1.setCourseType(courseType2);
        schedule1.setTimeLine(timeLine2);
        return new HashSet<>(Arrays.asList(schedule, schedule1));
    }

    private Set<Course> createCoursesWinter2018() {
        // Winter 2018 Courses
        Course courseHistory1 = new Course();
        courseHistory1.setCourseName("COMP 691");
        courseHistory1.setSemester(Semester.WINTER_2018);
        courseHistory1.setSection(1);
        courseHistory1.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.TUESDAY_5PM_TO_7PM, CourseType.LAB, TimeLine.TUESDAY_7PM_TO_9PM));

        Course courseHistory2 = new Course();
        courseHistory2.setCourseName("COMP 6331");
        courseHistory2.setSemester(Semester.WINTER_2018);
        courseHistory2.setSection(1);
        courseHistory2.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.TUESDAY_1PM_TO_3PM, CourseType.LAB, TimeLine.TUESDAY_7PM_TO_9PM));

        Course courseHistory3 = new Course();
        courseHistory3.setCourseName("COMP 6331");
        courseHistory3.setSemester(Semester.WINTER_2018);
        courseHistory3.setSection(2);
        courseHistory3.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.THURSDAY_1PM_TO_3PM, CourseType.LAB, TimeLine.THURSDAY_7PM_TO_9PM));

        Course courseHistory4 = new Course();
        courseHistory4.setCourseName("COMP 6331");
        courseHistory4.setSemester(Semester.WINTER_2018);
        courseHistory4.setSection(3);
        courseHistory4.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.THURSDAY_1PM_TO_3PM, CourseType.LAB, TimeLine.WEDNESDAY_7PM_TO_9PM));

        Course courseHistory5= new Course();
        courseHistory5.setCourseName("COMP 6421");
        courseHistory5.setSemester(Semester.WINTER_2018);
        courseHistory5.setSection(1);
        courseHistory5.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.MONDAY_5PM_TO_7PM, CourseType.LAB, TimeLine.MONDAY_7PM_TO_9PM));

        Course courseHistory6= new Course();
        courseHistory6.setCourseName("COMP 6421");
        courseHistory6.setSemester(Semester.WINTER_2018);
        courseHistory6.setSection(2);
        courseHistory6.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.MONDAY_5PM_TO_7PM, CourseType.LAB, TimeLine.MONDAY_3PM_TO_5PM));

        Course courseHistory7= new Course();
        courseHistory7.setCourseName("COMP 6521");
        courseHistory7.setSemester(Semester.WINTER_2018);
        courseHistory7.setSection(1);
        courseHistory7.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.WEDNESDAY_5PM_TO_7PM, CourseType.LAB, TimeLine.WEDNESDAY_3PM_TO_5PM));

        Course courseHistory8= new Course();
        courseHistory8.setCourseName("COMP 6521");
        courseHistory8.setSemester(Semester.WINTER_2018);
        courseHistory8.setSection(2);
        courseHistory8.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.WEDNESDAY_5PM_TO_7PM, CourseType.LAB, TimeLine.WEDNESDAY_7PM_TO_9PM));

        Course courseHistory9= new Course();
        courseHistory9.setCourseName("COMP 6721");
        courseHistory9.setSemester(Semester.WINTER_2018);
        courseHistory9.setSection(1);
        courseHistory9.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.THURSDAY_5PM_TO_7PM, CourseType.LAB, TimeLine.THURSDAY_7PM_TO_9PM));

        Course courseHistory10= new Course();
        courseHistory10.setCourseName("COMP 6741");
        courseHistory10.setSemester(Semester.WINTER_2018);
        courseHistory10.setSection(1);
        courseHistory10.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.WEDNESDAY_5PM_TO_7PM, CourseType.LAB, TimeLine.TUESDAY_3PM_TO_5PM));

        Course courseHistory11= new Course();
        courseHistory11.setCourseName("SOEN 691");
        courseHistory11.setSemester(Semester.WINTER_2018);
        courseHistory11.setSection(1);
        courseHistory11.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.WEDNESDAY_5PM_TO_7PM, CourseType.LAB, TimeLine.WEDNESDAY_3PM_TO_5PM));

        Course courseHistory12= new Course();
        courseHistory12.setCourseName("SOEN 6441");
        courseHistory12.setSemester(Semester.WINTER_2018);
        courseHistory12.setSection(1);
        courseHistory12.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.THURSDAY_5PM_TO_7PM, CourseType.LAB, TimeLine.THURSDAY_7PM_TO_9PM));

        Set<Course> courseHistories = new HashSet<>();
        courseHistories.add(courseHistory1);
        courseHistories.add(courseHistory2);
        courseHistories.add(courseHistory3);
        courseHistories.add(courseHistory4);
        courseHistories.add(courseHistory5);
        courseHistories.add(courseHistory6);
        courseHistories.add(courseHistory8);
        courseHistories.add(courseHistory9);
        courseHistories.add(courseHistory10);
        courseHistories.add(courseHistory11);
        courseHistories.add(courseHistory12);

        return courseHistories;
    }

    private Set<Course> createCoursesFall2017() {
        Course courseHistory = new Course();
        courseHistory.setCourseName("SOEN 1001");
        courseHistory.setPreRequisites("SOEN 1000");
        courseHistory.setSemester(Semester.FALL_2017);
        courseHistory.setSection(1);
        courseHistory.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.MONDAY_9AM_TO_12PM, CourseType.LAB, TimeLine.FRIDAY_9AM_TO_12PM));

        Course courseHistory1 = new Course();
        courseHistory1.setCourseName("COMP 5555");
        courseHistory1.setPreRequisites("COMP 5554");
        courseHistory1.setSemester(Semester.FALL_2017);
        courseHistory1.setSection(1);
        courseHistory1.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.MONDAY_3PM_TO_5PM, CourseType.LAB, TimeLine.FRIDAY_3PM_TO_5PM));

        Course courseHistory2 = new Course();
        courseHistory2.setCourseName("COMP 3232");
        courseHistory2.setPreRequisites("COMP 3231");
        courseHistory2.setSemester(Semester.FALL_2017);
        courseHistory2.setSection(1);
        courseHistory2.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.MONDAY_5PM_TO_7PM, CourseType.TUTORIAL, TimeLine.FRIDAY_5PM_TO_7PM));

        Course courseHistory3 = new Course();
        courseHistory3.setCourseName("ENCS 6260");
        courseHistory3.setPreRequisites("ENCS 6250");
        courseHistory3.setSemester(Semester.FALL_2017);
        courseHistory3.setSection(1);
        courseHistory3.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.TUESDAY_9AM_TO_12PM, CourseType.TUTORIAL, TimeLine.THURSDAY_9AM_TO_12PM));

        Course courseHistory4 = new Course();
        courseHistory4.setCourseName("ENCS 2222");
        courseHistory4.setPreRequisites("ENCS 2221");
        courseHistory4.setSemester(Semester.FALL_2017);
        courseHistory4.setSection(1);
        courseHistory4.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.THURSDAY_3PM_TO_5PM, CourseType.TUTORIAL, TimeLine.THURSDAY_5PM_TO_7PM));

        Course courseHistory5 = new Course();
        courseHistory5.setCourseName("ENCS 0001");
        courseHistory5.setPreRequisites("ENCS 0000");
        courseHistory5.setSemester(Semester.FALL_2017);
        courseHistory5.setSection(1);
        courseHistory5.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.WEDNESDAY_7PM_TO_9PM, CourseType.TUTORIAL, TimeLine.THURSDAY_7PM_TO_9PM));

        Course courseHistory6 = new Course();
        courseHistory6.setCourseName("ENCS 2221");
        courseHistory6.setPreRequisites("ENCS 2220");
        courseHistory6.setSemester(Semester.FALL_2017);
        courseHistory6.setSection(1);
        courseHistory6.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.WEDNESDAY_3PM_TO_5PM, CourseType.TUTORIAL, TimeLine.WEDNESDAY_9AM_TO_12PM));

        Course courseHistory7 = new Course();
        courseHistory7.setCourseName("ENCS 2221");
        courseHistory7.setPreRequisites("ENCS 2220");
        courseHistory7.setSemester(Semester.FALL_2017);
        courseHistory7.setSection(2);
        courseHistory7.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.MONDAY_3PM_TO_5PM, CourseType.TUTORIAL, TimeLine.MONDAY_9AM_TO_12PM));

        Set<Course> courseHistories = new HashSet<>();
        courseHistories.add(courseHistory);
        courseHistories.add(courseHistory1);
        courseHistories.add(courseHistory2);
        courseHistories.add(courseHistory3);
        courseHistories.add(courseHistory4);
        courseHistories.add(courseHistory5);
        courseHistories.add(courseHistory6);

        return courseHistories;
    }

    private Set<Course> createCoursesSummer2018() {
        // Summer 2018 Courses
        Course courseHistory2 = new Course();
        courseHistory2.setCourseName("COMP 6331");
        courseHistory2.setSemester(Semester.SUMMER_2018);
        courseHistory2.setSection(1);
        courseHistory2.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.TUESDAY_1PM_TO_3PM, CourseType.LAB, TimeLine.TUESDAY_7PM_TO_9PM));

        Course courseHistory5= new Course();
        courseHistory5.setCourseName("COMP 6421");
        courseHistory5.setSemester(Semester.SUMMER_2018);
        courseHistory5.setSection(1);
        courseHistory5.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.MONDAY_5PM_TO_7PM, CourseType.LAB, TimeLine.MONDAY_7PM_TO_9PM));

        Course courseHistory7= new Course();
        courseHistory7.setCourseName("COMP 6521");
        courseHistory7.setSemester(Semester.SUMMER_2018);
        courseHistory7.setSection(1);
        courseHistory7.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.WEDNESDAY_5PM_TO_7PM, CourseType.LAB, TimeLine.WEDNESDAY_3PM_TO_5PM));

        Course courseHistory12= new Course();
        courseHistory12.setCourseName("SOEN 6441");
        courseHistory12.setSemester(Semester.SUMMER_2018);
        courseHistory12.setSection(1);
        courseHistory12.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.THURSDAY_5PM_TO_7PM, CourseType.LAB, TimeLine.THURSDAY_7PM_TO_9PM));

        Set<Course> courseHistories = new HashSet<>();
        courseHistories.add(courseHistory2);
        courseHistories.add(courseHistory5);
        courseHistories.add(courseHistory7);
        courseHistories.add(courseHistory12);

        return courseHistories;
    }

    public Set<Course> createCourseHistory() {
        Course courseHistory = new Course();
        courseHistory.setCourseName("INSE 6260");
        courseHistory.setPreRequisites("INSE 6250");
        courseHistory.setGrade(3.4f);
        courseHistory.setSection(1);
        courseHistory.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.WEDNESDAY_1PM_TO_3PM, CourseType.LAB, TimeLine.THURSDAY_7PM_TO_9PM));
        courseHistory.setSemester(Semester.FALL_2016);

        Course courseHistory1 = new Course();
        courseHistory1.setCourseName("COMP 352");
        courseHistory1.setPreRequisites("COMP 249");
        courseHistory1.setGrade(4f);
        courseHistory1.setSection(1);
        courseHistory1.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.MONDAY_3PM_TO_5PM, CourseType.LAB, TimeLine.THURSDAY_7PM_TO_9PM));
        courseHistory1.setSemester(Semester.FALL_2016);

        Course courseHistory2 = new Course();
        courseHistory2.setCourseName("COMP 6260");
        courseHistory2.setPreRequisites("COMP 6250");
        courseHistory2.setGrade(3f);
        courseHistory2.setSection(1);
        courseHistory2.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.WEDNESDAY_1PM_TO_3PM, CourseType.TUTORIAL, TimeLine.TUESDAY_1PM_TO_3PM));
        courseHistory2.setSemester(Semester.WINTER_2017);

        Course courseHistory3 = new Course();
        courseHistory3.setCourseName("ENCS 6260");
        courseHistory3.setPreRequisites("ENCS 6250");
        courseHistory3.setGrade(2.9f);
        courseHistory3.setSection(1);
        courseHistory3.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.MONDAY_3PM_TO_5PM, CourseType.TUTORIAL, TimeLine.FRIDAY_9AM_TO_12PM));
        courseHistory3.setSemester(Semester.WINTER_2017);

        Course courseHistory4= new Course();
        courseHistory4.setCourseName("COMP 6741");
        courseHistory4.setSemester(Semester.WINTER_2018);
        courseHistory4.setSection(1);
        courseHistory4.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.WEDNESDAY_5PM_TO_7PM, CourseType.LAB, TimeLine.TUESDAY_3PM_TO_5PM));

        Set<Course> courseHistories = new HashSet<>();
        courseHistories.add(courseHistory);
        courseHistories.add(courseHistory1);
        courseHistories.add(courseHistory2);
        courseHistories.add(courseHistory3);
        courseHistories.add(courseHistory4);

        return courseHistories;
    }

    public Set<Course> getCourses() {
        Course course1 = new Course();
        course1.setCourseName("INSE 6260");
        course1.setPreRequisites("INSE 6250");

        Course course2 = new Course();
        course2.setCourseName("COMP 6761");
        course2.setPreRequisites("COMP 6311");
        Set<Course> courses = new HashSet<>();

        Set<Schedule> scheduleSetFirstCourse = getSchedules(CourseType.LECTURE, TimeLine.FRIDAY_5PM_TO_7PM, CourseType.LAB, TimeLine.MONDAY_1PM_TO_3PM);
        Set<Schedule> scheduleSetSecondCourse = getSchedules(CourseType.LECTURE, TimeLine.WEDNESDAY_1PM_TO_3PM, CourseType.LAB, TimeLine.THURSDAY_7PM_TO_9PM);

        course1.setSchedules(scheduleSetFirstCourse);
        course2.setSchedules(scheduleSetSecondCourse);

        course1.setSemester(Semester.FALL_2017);
        course2.setSemester(Semester.FALL_2017);

        courses.add(course1);
        courses.add(course2);

        return courses;
    }
}
