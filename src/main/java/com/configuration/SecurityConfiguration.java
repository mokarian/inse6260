package com.configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import com.model.Role;
import com.model.User;
import com.model.sc.*;
import com.model.sc.enums.*;
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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * A security configuration
 *
 * @author Maysam Mokarian
 * @version 2.0
 * @since 24.10.2017
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    public static final String STUDENT = "STUDENT";
    public static final String TEACHER = "PROFESSOR";
    public static final String ADMIN = "ADMIN";
    private CoursesOffered coursesOffered = new CoursesOffered();

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
    private TeacherRepository teacherRepository;

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
      //  storeStudentToDatabase("Maysam", "Mokarian", "maysam@concordia.ca", STUDENT, 1);
    //    storeUserToDatabase("rachida", "dssouli", "rachida.dssouli@concordia.ca", TEACHER, 3);
        storeTeacherToDatabase("rachida", "dssouli", "rachida.dssouli@concordia.ca", TEACHER, 3);

//        storeUserToDatabase("Freyja", "Jökulsdóttir", "freyja@concordia.ca", STUDENT, 2);
//        storeStudentToDatabase("Freyja", "Jökulsdóttir", "freyja@concordia.ca", STUDENT, 2);
        storeUserToDatabase("rachida", "dssouli", "rachida.dssouli@concordia.ca", TEACHER, 3);
        storeUserToDatabase("Salvatore", "Colavita", "Salvatore.Colavita@concordia.ca", ADMIN, 4);
        Set<Course> courseHistories = this.coursesOffered.createCoursesOfferedThisSemester();
        courseRepository.save(courseHistories);
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

    public Student storeStudentToDatabase(String firstName, String lastName, String email, String role, int i, ProgramType program, StudentStatus status) {
        Student student = new Student();
        student.setName(firstName);
        student.setUser_id(i);
        student.setLastName(lastName);
        student.setEmail(email);
        student.setPassword(bCryptPasswordEncoder.encode("123456"));
        student.setActive(1);
        Role userRole = roleRepository.findByRole(role);
        student.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        student.setCoursesForCurrentSemester(coursesOffered.getCourses());
        student.setCourseHistory(coursesOffered.createCourseHistory());
        student.setAddress("45 Forden Crescent, Westmount, Quebec H3Y 3H2 Canada");
        student.setPhone("(514)226-0101");
        student.setTuition(status, Semester.FALL_2017.name());
        student.setProgram(program);
        student.setStatus(status);
      //  studentRepository.save(student);
       // userRepository.save(student);
        return student;
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
        teacher.setCoursesForCurrentSemester(coursesOffered.getCourses());
        teacher.setStudents(createStudents());
        teacherRepository.save(teacher);
      //  userRepository.save(teacher);

    }

    private Set<Student> createStudents() {
        Set<Student> students = new HashSet<>();
        Student student1 = storeStudentToDatabase("Maysam", "Mokarian", "maysam@concordia.ca", STUDENT, 11, ProgramType.COMP, StudentStatus.INTERNATIONAL);
       // storeUserToDatabase("Maysam", "Mokarian", "maysam@concordia.ca", STUDENT, 1);
//
//                Student student2 = storeStudentToDatabase2("Freyja", "Jökulsdóttir", "freyja@concordia.ca", STUDENT, 12);
//        Student student3 = storeStudentToDatabase2("Parisa", "Nikzad", "parisa@concordia.ca", STUDENT, 13);
//        Student student4 = storeStudentToDatabase2("Rana", "Jyotsna ", "rana@concordia.ca", STUDENT, 14);
//        Student student5 = storeStudentToDatabase2("Robert", "Deniro ", "alex@concordia.ca", STUDENT, 15);
//        Student student6 = storeStudentToDatabase2("Lana", "DelRey ", "lana@concordia.ca", STUDENT, 16);
//        Student student7 = storeStudentToDatabase2("Jonathan", "BonJovi ", "jonathan@concordia.ca", STUDENT, 17);
//        Student student8 = storeStudentToDatabase2("Micheal", "Jackson ", "micheal@concordia.ca", STUDENT, 18);
//        Student student9 = storeStudentToDatabase2("George", "Michael", "george@concordia.ca", STUDENT, 19);
//        Student student10 = storeStudentToDatabase2("Ed", "Sheeran ", "ed@concordia.ca", STUDENT, 20);
//        storeUserToDatabase("Salvatore", "Colavita", "Salvatore.Colavita@concordia.ca", ADMIN, 4);

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
}