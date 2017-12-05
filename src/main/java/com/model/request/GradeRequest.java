package com.model.request;

/**
 * A request for grades and related models
 *
 * @author Maysam Mokarian
 * @version 1.0
 * @since 03.12.2017
 */
public class GradeRequest {
    private String studentEmail;
    private String course;
    private float grade;
    private String contactInfo;

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
}
