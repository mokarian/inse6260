package com.controller;

import com.model.request.ContactInfoRequest;
import com.model.request.SemesterRequest;
import com.model.request.TuitionRequest;
import com.model.sc.Course;
import com.model.sc.ImportantDates;
import com.model.sc.enums.ProgramType;
import com.model.sc.Student;
import com.model.sc.enums.Semester;
import com.service.CourseService;
import com.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.*;

/**
 * A student controller
 *
 * @author Maysam Mokarian
 * @version 2.0
 * @since 21.09.2017
 */
@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;

    private String semesterToEnroll;
    private double futureDue;

    @RequestMapping(value = "/student/home", method = RequestMethod.GET)
    public ModelAndView studentHome() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
;
        modelAndView.addObject("studentMessage", "Content Available Only for Users with Student Role");
        modelAndView.setViewName("student/home");
        return modelAndView;
    }

    @RequestMapping(value = "/student/chooseterm", method = RequestMethod.GET)
    public ModelAndView studentChooseTerm() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student user = studentService.findByEmail(auth.getName());

        modelAndView.addObject("semesterRequest", new SemesterRequest());

        modelAndView.setViewName("student/chooseterm");
        return modelAndView;
    }

    @RequestMapping(value = "/student/chooseterm", method = RequestMethod.POST)
    public ModelAndView studentChooseTermPost(SemesterRequest semesterRequest) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("student/chooseterm");

        String semester ="";
        semester = semesterRequest.getSemester();
        if(semester.contains("F")) {
            semester = Semester.FALL_2017.name();
            this.semesterToEnroll = semester;
            return studentEnroll(semester);
        }
        if(semester.contains("W")) {
            semester = Semester.WINTER_2018.name();
            this.semesterToEnroll = semester;
            return studentEnroll(semester);
        }
        if(semester.contains("S")) {
            semester = Semester.SUMMER_2018.name();
            this.semesterToEnroll = semester;
            return studentEnroll(semester);
        }

        return modelAndView;
    }

    @RequestMapping(value = "/student/enroll", method = RequestMethod.GET)
    public ModelAndView studentEnroll(String semester) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student user = studentService.findByEmail(auth.getName());

        List<String> list = new ArrayList<>();

        List<Course> listOfCourses = new ArrayList<>();
        listOfCourses.addAll(user.getCoursesForSemester(semester));
        for (Course course : listOfCourses) {
            list.add(course.getCourseName());
        }
        modelAndView.addObject("courses", list);
        List<String> listOfCoursesOffered = getCourseName(studentService.getCoursesOfferedThisSemester(semester));
        List<Course> listOfCoursesOfferedFull = studentService.getCoursesOfferedThisSemester(semester);

        modelAndView.addObject("coursesToAdd", listOfCoursesOffered);
        modelAndView.addObject("coursesToAddFull", listOfCoursesOfferedFull);

        modelAndView.setViewName("student/enroll");
        return modelAndView;
    }

    private List<String> getCourseName(List<Course> coursesOfferedThisSemester) {
        List<String> courses = new ArrayList<>();
        for (Course course : coursesOfferedThisSemester) {
            courses.add(course.getCourseName());
        }
        return courses;
    }

    @RequestMapping(value = "student/drop/{courseId}", method = RequestMethod.GET)
    public ModelAndView studentDropPost(@PathVariable String courseId) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student user = studentService.findByEmail(auth.getName());

        String message = "";
        ImportantDates dates = new ImportantDates();
        Date disc = dates.getDISC(this.semesterToEnroll);
        Date dne = dates.getDNE(this.semesterToEnroll);
        Date today = new Date();
        disc.setYear(Integer.parseInt(semesterToEnroll.substring(this.semesterToEnroll.length()-4, this.semesterToEnroll.length())));
        dne.setYear(Integer.parseInt(semesterToEnroll.substring(this.semesterToEnroll.length()-4, this.semesterToEnroll.length())));
        //today.setMonth(11);
        //today.setDate(1);

        List<Course> courseListIterator = new ArrayList();
        courseListIterator.addAll(user.getCoursesForSemester(this.semesterToEnroll));
        ListIterator<Course> iterator = courseListIterator.listIterator();
        while (iterator.hasNext()) {
            Course course = iterator.next();
            if (course.getCourseName().equalsIgnoreCase(courseId)) {
                if(today.before(dne)) {
                    message = getMsgBeforeDNE(courseId, user, iterator, course);
                    break;
                } else if(today.before(disc)) {
                    message = getMsgBeforeDISC(courseId, user.getCourseHistory(), user, course);
                    break;
                } else {
                    message = "course(" + courseId + ") could not be dropped since it is passed the DISC deadline. Please go see an advisor to drop it.";
                    break;
                }
            } else {
                message = "course(" + courseId + ") was not found in the list of courses ";
            }
        }

        List<String> list = new ArrayList<>();
        for (Course course : user.getCoursesForSemester(this.semesterToEnroll)) {
            list.add(course.getCourseName());
        }

        modelAndView.addObject("courses", list);

        modelAndView.addObject("message", message);
        List<String> listOfCoursesOffered = getCourseName(studentService.getCoursesOfferedThisSemester(this.semesterToEnroll));
        List<Course> listOfCoursesOfferedFull = studentService.getCoursesOfferedThisSemester(this.semesterToEnroll);

        modelAndView.addObject("coursesToAdd", listOfCoursesOffered);
        modelAndView.addObject("coursesToAddFull", listOfCoursesOfferedFull);
        modelAndView.setViewName("student/enroll");
        return modelAndView;
    }

    private String getMsgBeforeDISC(String courseId, Set<Course> courseHistory, Student user, Course course) {
        String message;

//        List<Course> courses = new ArrayList<>(courseHistory);
//        ListIterator<Course> iterator = courses.listIterator();
//        while (iterator.hasNext()) {
//            if(iterator.next().getCourseName().equals(course.getCourseName()))
//                iterator.remove();
//        }
        user.removeCoursesForSemester(course, this.semesterToEnroll);

        course.setGrade(-1.0f);
        user.getCourseHistory().add(course);
        user.setCourseHistory(user.getCourseHistory());
        studentService.saveStudent(user);
        if (course.getEnrollementTotal() >= course.getCapacity())
            course.setWaitlistTotal(course.getWaitlistTotal() - 1);
        else
            course.setEnrollementTotal(course.getEnrollementTotal() - 1);
        courseService.saveCourse(course);
        message = "course(" + courseId + ") discontinued successfully ";
        return message;
    }

    private String getMsgBeforeDNE(@PathVariable String courseId, Student user, ListIterator<Course> iterator, Course course) {
        String message;
        iterator.remove();
        user.removeCoursesForSemester(course, this.semesterToEnroll);
        studentService.saveStudent(user);
        if (course.getEnrollementTotal() >= course.getCapacity())
            course.setWaitlistTotal(course.getWaitlistTotal() - 1);
        else
            course.setEnrollementTotal(course.getEnrollementTotal() - 1);
        courseService.saveCourse(course);
        message = "course(" + courseId + ") dropped successfully ";
        return message;
    }

    @RequestMapping(value = "student/enroll/{courseId}", method = RequestMethod.GET)
    public ModelAndView studentEnrollPost(@PathVariable String courseId) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student user = studentService.findByEmail(auth.getName());

        List<Course> courses =studentService.getCoursesOfferedThisSemester(this.semesterToEnroll);
        String message = "";
        boolean found = false;

        for(Course course:courses){
            if(course.getCourseName().equalsIgnoreCase(courseId) && !found) {
                if (noConflictsDetected(user.getCoursesForSemester(this.semesterToEnroll), course, user.getProgram())) {
                    user.setCoursesForSemester(course, this.semesterToEnroll);
                    studentService.saveStudent(user);
                    if(course.getEnrollementTotal() >= course.getCapacity()) {
                        course.setWaitlistTotal(course.getWaitlistTotal() + 1);
                        message = "You have been added to the waitlist of " +courseId + " successfully";
                    }
                    else {
                        course.setEnrollementTotal(course.getEnrollementTotal() + 1);
                        message = courseId + " added to your courses successfully";
                    }
                    courseService.saveCourse(course);
                } else {
                    if(course.getWaitlistTotal() >= course.getWaitlistCapacity())
                        message = courseId + " could not be added to your courses because the course and waitlist is full.";
                    else if(user.getCoursesForSemester(this.semesterToEnroll).size() >= 3)
                        message = courseId + " could not be added to your courses because you have reached the maximum course load.";
                    else if(!course.getCourseName().contains(user.getProgram().toString()))
                        message = courseId + " could not be added to your courses because it is not withing your faculty, please go see an advisor to add it.";
                    else
                        message = courseId + " could not be added to your courses due to a conflict or you have already registered in it";
                }
                found = true;
            }
        }

        List<String> list = new ArrayList<>();
        for (Course course : user.getCoursesForSemester(this.semesterToEnroll)) {
            list.add(course.getCourseName());
        }

        modelAndView.addObject("courses", list);

        modelAndView.addObject("message", message);
        List<String> listOfCoursesOffered = getCourseName(studentService.getCoursesOfferedThisSemester(this.semesterToEnroll));
        List<Course> listOfCoursesOfferedFull = studentService.getCoursesOfferedThisSemester(this.semesterToEnroll);

        modelAndView.addObject("coursesToAdd", listOfCoursesOffered);
        modelAndView.addObject("coursesToAddFull", listOfCoursesOfferedFull);
        modelAndView.setViewName("student/enroll");
        return modelAndView;
    }

    private boolean noConflictsDetected(Set<Course> coursesForCurrentSemester, Course course, ProgramType program) {
        List<Course> courses = new ArrayList<>(coursesForCurrentSemester);
        if(courses.size() >= 3)
            return false;
        if(!course.getCourseName().contains(program.toString())) {
            return false;
        }
        if(course.getWaitlistTotal() >= course.getWaitlistCapacity())
            return false;
        for(Course c:courses){
            if(c.getSchedules().contains(course.getSchedules())){
                return false;
            }
            if(c.getCourseName().equals(course.getCourseName()))
                return false;
        }
        return true;
    }

    @RequestMapping(value = "/student/schedule", method = RequestMethod.GET)
    public ModelAndView studentSchedule() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student user = studentService.findByEmail(auth.getName());

        modelAndView.addObject("courses", user.getCoursesForCurrentSemester());
        modelAndView.setViewName("student/schedule");
        return modelAndView;
    }

    @RequestMapping(value = "/student/transcript", method = RequestMethod.GET)
    public ModelAndView studentTranscript() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student user = studentService.findByEmail(auth.getName());
        Map<String, Float> cGPA = user.getAnnualGPA();
        user.getCumulativeGPA();

        List<Course> Courses = new ArrayList<>(user.getCourseHistory());
        List<String> gpaList = new ArrayList<>();
        Set<String> years= new HashSet<>();
        for (Course course : Courses) {
            String year = course.getSemester().substring(course.getSemester().length() - 4, course.getSemester().length());
            Float CGPA = 0.0f;
            if (cGPA.containsKey(year)) {
                CGPA = cGPA.get(year);
            }
            if( !years.contains(year) && CGPA!=0.0f){
                gpaList.add(year+": GPA: "+CGPA);
            }
            years.add(year);

            String grade = "";
            if(course.getGrade() == -1.0f)
                grade = "DISC";
            else if(course.getGrade() == 0.0f)
                grade = "Not Posted";
            else
                grade += course.getGrade();

            String space = " | ";
            String semester = course.getSemester().substring(0,course.getSemester().length()-5);
            gpaList.add(year +" | " +semester +space +course.getCourseName() +space +grade);
        }
        Collections.sort(gpaList);
        gpaList.add("CGPA: "+user.getCumulativeGPA());
        modelAndView.addObject("student", user.getName()+ " "+user.getLastName());
        modelAndView.addObject("gpas", gpaList);
        modelAndView.setViewName("student/transcript");
        return modelAndView;
    }

    @RequestMapping(value = "/student/tuition", method = RequestMethod.GET)
    public ModelAndView studentTuition() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("tuitionRequest", new TuitionRequest());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student user = studentService.findByEmail(auth.getName());

        modelAndView.addObject("tuition", user.getTuition().toString());

        this.futureDue = user.getFutureTuition();
        modelAndView.addObject("futureTuition", futureDue);

        modelAndView.setViewName("student/tuition");
        return modelAndView;
    }


    @RequestMapping(value = "/student/tuition", method = RequestMethod.POST)
    public ModelAndView studentTuitionPost(TuitionRequest stringRequest) {
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student user = studentService.findByEmail(auth.getName());
        String message = "";
        BigDecimal futureTuition = new BigDecimal(this.futureDue);
        if (user.getTuition().subtract(new BigDecimal(stringRequest.getAmount())).signum() == -1) {
            message = "The Amount Paid is More Than Your Current Tuition Fees. Your Future Tuition Fees Have Been Updated Accordingly";
            BigDecimal enteredAmount = new BigDecimal(stringRequest.getAmount());
            futureTuition = futureTuition.subtract(enteredAmount.subtract(user.getTuition()));
            user.setNewTuition(new BigDecimal(0));
            this.futureDue = futureTuition.doubleValue();
        } else if (user.getTuition().subtract(new BigDecimal(stringRequest.getAmount())).signum() == +1) {
            message = "The Amount Paid is Not Enough";
            user.setNewTuition(user.getTuition().subtract(new BigDecimal(stringRequest.getAmount())));
        } else {
            user.setNewTuition(new BigDecimal(0));
            message = "You Successfully Paid Your Current Tuition Fees";
        }
        studentService.saveStudent(user);
        modelAndView.addObject("tuition", user.getTuition().toString()+" CAD");
        modelAndView.addObject("futureTuition", futureDue +" CAD");
        modelAndView.addObject("message", message);
        modelAndView.setViewName("student/tuition");
        return modelAndView;
    }

    @RequestMapping(value = "/student/contactinfo", method = RequestMethod.POST)
    public ModelAndView studentContactInfo(ContactInfoRequest contactInfoRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student student = studentService.findByEmail(auth.getName());
        student.setPhone(contactInfoRequest.getPhone());
        student.setAddress(contactInfoRequest.getAddress());
        studentService.saveStudent(student);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("phoneToShow", student.getPhone());
        modelAndView.addObject("addressToShow", student.getAddress());
        modelAndView.addObject("successMessage", "your Address and Phone changed successfully");
        modelAndView.setViewName("student/contactinfo");
        return modelAndView;
    }

    @RequestMapping(value = "/student/contactinfo", method = RequestMethod.GET)
    public ModelAndView studentContactInfoGet() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("contactInfoRequest", new ContactInfoRequest());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student user = studentService.findByEmail(auth.getName());
        modelAndView.addObject("phoneToShow", user.getPhone());
        modelAndView.addObject("addressToShow", user.getAddress());
        modelAndView.setViewName("student/contactinfo");

        return modelAndView;
    }
}
