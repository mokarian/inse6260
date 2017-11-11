package com.configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import javax.xml.transform.Source;

import com.model.Role;
import com.model.User;
import com.model.sc.Course;
import com.model.sc.Schedule;
import com.model.sc.Student;
import com.repository.RoleRepository;
import com.repository.StudentRepository;
import com.repository.UserRepository;
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
        // Role userRole = roleRepository.findByRole("ADMIN");
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
    }

    @PostConstruct
    public void initUsers() {
        storeUserToDatabase("Maysam", "Mokarian", "maysam@concordia.ca", STUDENT, 1);
        storeStudentToDatabase("Maysam", "Mokarian", "maysam@concordia.ca", STUDENT,  1);
//        storeUserToDatabase("freyjaj", "Jökulsdóttir", "freyjaj@concordia.ca", STUDENT, 2);
//        storeStudentToDatabase("freyjaj", "Jökulsdóttir", "freyjaj@concordia.ca", STUDENT, 2);
        storeUserToDatabase("rachida", "dssouli", "rachida.dssouli@concordia.ca", TEACHER, 3);
        storeUserToDatabase("Salvatore", "Colavita", "Salvatore.Colavita@concordia.ca", ADMIN, 4);

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

    public void storeStudentToDatabase(String firstName, String lastName, String email,String role, int i) {
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
        studentRepository.save(student);
    }

    private Set<Course> getCourses() {
        Course course1 = new Course();
        course1.setCourseName("INSE 6260");
        course1.setPreRequisites("INSE 6250");
        Course course2 = new Course();
        course2.setCourseName("COMP 6761");
        course2.setPreRequisites("COMP 6311");
        Set<Course> courses = new HashSet<>();
        courses.add(course1);
        courses.add(course2);
        return courses;
    }

    private Set<Course> createCourseHistory() {
        Course courseHistory = new Course();
        courseHistory.setCourseName("INSE 6260");
//        courseHistory.setCourse_id(1);
        courseHistory.setPreRequisites("INSE 6250");
        courseHistory.setGrade(3.4f);
        courseHistory.setSemester("FALL 2016");
//        courseHistory.setCourse_id(1);
        courseHistory.setSchedule_id(1);
        Course courseHistory1 = new Course();
        courseHistory.setCourseName("COMP 352");
//        courseHistory.setCourse_id(2);
        courseHistory.setPreRequisites("COMP 249");
        courseHistory1.setGrade(4f);
        courseHistory1.setSemester("SUMMER 2016");
        courseHistory1.setSchedule_id(1);
        Course courseHistory2 = new Course();
        courseHistory.setCourseName("COMP 6260");
        courseHistory.setPreRequisites("COMP 6250");
        courseHistory2.setGrade(3f);
        courseHistory2.setSemester("WINTER 2016");
        courseHistory2.setSchedule_id(1);
        Course courseHistory3 = new Course();
        courseHistory.setCourseName("ENGR 6260");
        courseHistory.setPreRequisites("ENGR 6250");
        courseHistory3.setGrade(2.9f);
        courseHistory3.setSemester("WINTER 2015");
        courseHistory3.setSchedule_id(1);
        Set<Course> courseHistories = new HashSet<>();
        courseHistories.add(courseHistory);
        courseHistories.add(courseHistory1);
        courseHistories.add(courseHistory2);
        courseHistories.add(courseHistory3);
        return courseHistories;
    }

}