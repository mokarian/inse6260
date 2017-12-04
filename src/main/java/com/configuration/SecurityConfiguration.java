package com.configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import com.model.Role;
import com.model.User;
import com.model.sc.*;
import com.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    public static final String STUDENT = "STUDENT";
    public static final String TEACHER = "PROFESSOR";
    public static final String ADMIN = "ADMIN";
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherRepository  teacherRepository;

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.
                jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.
                authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/professor/**").hasAnyAuthority("PROFESSOR")
                .antMatchers("/registration").permitAll()
                .antMatchers("/admin/**").hasAuthority("ADMIN").anyRequest()
                .authenticated().and().csrf().disable().formLogin()
                .loginPage("/login").failureUrl("/login?error=true")
                .defaultSuccessUrl("/default")
                .usernameParameter("email")
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").and().exceptionHandling()
                .accessDeniedPage("/access-denied");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }


    @PostConstruct
    public void initDatabase() {
        Role admin = new Role();
        admin.setRole(ADMIN);
        admin.setId(1);
        Role student = new Role();
        student.setRole(STUDENT);
        student.setId(2);
        Role professor = new Role();
        professor.setRole(TEACHER);
        professor.setId(3);
        roleRepository.save(admin);
        roleRepository.save(student);
        roleRepository.save(professor);
        storeUserToDatabase("Maysam", "Mokarian", "maysam@concordia.ca", STUDENT, 1);
        storeStudentToDatabase("Maysam", "Mokarian", "maysam@concordia.ca", STUDENT, 1);
//        storeUserToDatabase("Freyja", "Jökulsdóttir", "freyja@concordia.ca", STUDENT, 2);
//        storeStudentToDatabase("Freyja", "Jökulsdóttir", "freyja@concordia.ca", STUDENT, 2);
        storeUserToDatabase("rachida", "dssouli", "rachida.dssouli@concordia.ca", TEACHER, 3);
        storeUserToDatabase("Salvatore", "Colavita", "Salvatore.Colavita@concordia.ca", ADMIN, 4);
        createCoursesOfferedThisSemester();
    }

    public void storeUserToDatabase(String firstName, String lastName, String email, String role, int i) {
        User user = new User();
        user.setName(firstName);
        user.setUser_id(i);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode("123456"));
        user.setActive(1);
        Role userRole = roleRepository.findByRole(role);
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    public Student storeStudentToDatabase(String firstName, String lastName, String email, String role, int i) {
        Student student = new Student();
        student.setName(firstName);
        student.setUser_id(i);
        student.setLastName(lastName);
        student.setEmail(email);
        student.setPassword(bCryptPasswordEncoder.encode("123456"));
        student.setActive(1);
        Role userRole = roleRepository.findByRole(role);
        student.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        student.setCoursesForCurrentSemester(getCourses());
        student.setCourseHistory(createCourseHistory());
        student.setAddress("45 Forden Crescent, Westmount, Quebec H3Y 3H2 Canada");
        student.setPhone("(514)226-0101");
        student.setTuition(new BigDecimal(2300));
        studentRepository.save(student);
        return student;
    }

    private Set<Course> getCourses() {
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
        courses.add(course1);
        courses.add(course2);
        return courses;
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

    private Set<Course> createCourseHistory() {
        Course courseHistory = new Course();
        courseHistory.setCourseName("INSE 6260");
//        courseHistory.setCourse_id(1);
        courseHistory.setPreRequisites("INSE 6250");
        courseHistory.setGrade(3.4f);
        courseHistory.setSemester("FALL 2016");
        courseHistory.setSection(1);
//        courseHistory.setCourse_id(1);
        courseHistory.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.WEDNESDAY_1PM_TO_3PM, CourseType.LAB, TimeLine.THURSDAY_7PM_TO_9PM));
        Course courseHistory1 = new Course();
        courseHistory1.setCourseName("COMP 352");
//        courseHistory.setCourse_id(2);
        courseHistory1.setPreRequisites("COMP 249");
        courseHistory1.setGrade(4f);
        courseHistory1.setSemester("SUMMER 2016");
        courseHistory1.setSection(1);
        courseHistory1.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.MONDAY_3PM_TO_5PM, CourseType.LAB, TimeLine.THURSDAY_7PM_TO_9PM));
        Course courseHistory2 = new Course();
        courseHistory2.setCourseName("COMP 6260");
        courseHistory2.setPreRequisites("COMP 6250");
        courseHistory2.setGrade(3f);
        courseHistory2.setSemester("WINTER 2016");
        courseHistory2.setSection(1);
        courseHistory2.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.WEDNESDAY_1PM_TO_3PM, CourseType.TUTORIAL, TimeLine.TUESDAY_1PM_TO_3PM));
        Course courseHistory3 = new Course();
        courseHistory3.setCourseName("ENGR 6260");
        courseHistory3.setPreRequisites("ENGR 6250");
        courseHistory3.setGrade(2.9f);
        courseHistory3.setSemester("WINTER 2015");
        courseHistory3.setSection(1);
        courseHistory3.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.MONDAY_3PM_TO_5PM, CourseType.TUTORIAL, TimeLine.FRIDAY_9AM_TO_12PM));
        Set<Course> courseHistories = new HashSet<>();
        courseHistories.add(courseHistory);
        courseHistories.add(courseHistory1);
        courseHistories.add(courseHistory2);
        courseHistories.add(courseHistory3);
        return courseHistories;
    }

    public void storeTeacherToDatabase(String firstName, String lastName, String email, String role, int i) {
        Teacher teacher = new Teacher();
        teacher.setName(firstName);
        teacher.setUser_id(i);
        teacher.setLastName(lastName);
        teacher.setEmail(email);
        teacher.setPassword(bCryptPasswordEncoder.encode("123456"));
        teacher.setActive(1);
        Role userRole = roleRepository.findByRole(role);
        teacher.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        teacher.setCoursesForCurrentSemester(getCourses());
        teacher.setStudents(createStudents());
        teacherRepository.save(teacher);
    }

    private Set<Student> createStudents() {
        Set<Student> students = new HashSet<>();
        Student student1 = storeStudentToDatabase("Maysam", "Mokarian", "maysam@concordia.ca", STUDENT, 1);
//        Student student2 = storeStudentToDatabase("Freyja", "Jökulsdóttir", "freyja@concordia.ca", STUDENT, 1);
//        Student student3 = storeStudentToDatabase("Parisa", "Nikzad", "parisa@concordia.ca", STUDENT, 1);
//        Student student4 = storeStudentToDatabase("Rana", "Jyotsna ", "rana@concordia.ca", STUDENT, 1);
//        Student student5 = storeStudentToDatabase("Robert", "Deniro ", "alex@concordia.ca", STUDENT, 1);
//        Student student6 = storeStudentToDatabase("Lana", "DelRey ", "lana@concordia.ca", STUDENT, 1);
//        Student student7 = storeStudentToDatabase("Jonathan", "BonJovi ", "jonathan@concordia.ca", STUDENT, 1);
//        Student student8 = storeStudentToDatabase("Micheal", "Jackson ", "micheal@concordia.ca", STUDENT, 1);
//        Student student9 = storeStudentToDatabase("George", "Michael", "george@concordia.ca", STUDENT, 1);
//        Student student10 = storeStudentToDatabase("Ed", "Sheeran ", "ed@concordia.ca", STUDENT, 1);

        students.add(student1);
//        students.add(student2);
//        students.add(student3);
//        students.add(student4);
//        students.add(student5);
//        students.add(student6);
//        students.add(student7);
//        students.add(student8);
//        students.add(student9);
//        students.add(student10);
        return students;
    }

    private void createCoursesOfferedThisSemester() {
        Course courseHistory = new Course();
        courseHistory.setCourseName("SOEN 1001");
        courseHistory.setPreRequisites("SOEN 1000");
        courseHistory.setSemester("FALL 2017");
        courseHistory.setSection(1);
        courseHistory.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.MONDAY_9AM_TO_12PM, CourseType.LAB, TimeLine.FRIDAY_9AM_TO_12PM));
        Course courseHistory1 = new Course();
        courseHistory1.setCourseName("COMP 5555");
        courseHistory1.setPreRequisites("COMP 5554");
        courseHistory1.setSemester("FALL 2017");
        courseHistory1.setSection(1);
        courseHistory1.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.MONDAY_3PM_TO_5PM, CourseType.LAB, TimeLine.FRIDAY_3PM_TO_5PM));
        Course courseHistory2 = new Course();
        courseHistory2.setCourseName("COMP 3232");
        courseHistory2.setPreRequisites("COMP 3231");
        courseHistory2.setSemester("FALL 2017");
        courseHistory2.setSection(1);
        courseHistory2.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.MONDAY_5PM_TO_7PM, CourseType.TUTORIAL, TimeLine.FRIDAY_5PM_TO_7PM));
        Course courseHistory3 = new Course();
        courseHistory3.setCourseName("ENGR 6260");
        courseHistory3.setPreRequisites("ENGR 6250");
        courseHistory3.setSemester("FALL 2017");
        courseHistory3.setSection(1);
        courseHistory3.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.TUESDAY_9AM_TO_12PM, CourseType.TUTORIAL, TimeLine.THURSDAY_9AM_TO_12PM));
        Course courseHistory4 = new Course();
        courseHistory4.setCourseName("ENCS 2222");
        courseHistory4.setPreRequisites("ENCS 2221");
        courseHistory4.setSemester("FALL 2017");
        courseHistory4.setSection(1);
        courseHistory4.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.THURSDAY_3PM_TO_5PM, CourseType.TUTORIAL, TimeLine.THURSDAY_5PM_TO_7PM));
        Course courseHistory5 = new Course();
        courseHistory5.setCourseName("ENGR 0001");
        courseHistory5.setPreRequisites("ENGR 0000");
        courseHistory5.setSemester("FALL 2017");
        courseHistory5.setSection(1);
        courseHistory5.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.WEDNESDAY_7PM_TO_9PM, CourseType.TUTORIAL, TimeLine.THURSDAY_7PM_TO_9PM));
        Course courseHistory6 = new Course();
        courseHistory6.setCourseName("ENGR 2221");
        courseHistory6.setPreRequisites("ENGR 2220");
        courseHistory6.setSemester("FALL 2017");
        courseHistory6.setSection(1);
        courseHistory6.setSchedules(getSchedules(CourseType.LECTURE, TimeLine.WEDNESDAY_3PM_TO_5PM, CourseType.TUTORIAL, TimeLine.WEDNESDAY_9AM_TO_12PM));
        Course courseHistory7 = new Course();
        courseHistory7.setCourseName("ENGR 2221");
        courseHistory7.setPreRequisites("ENGR 2220");
        courseHistory7.setSemester("FALL 2017");
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
        courseRepository.save(courseHistories);
    }


}