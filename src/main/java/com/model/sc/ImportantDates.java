package com.model.sc;

import com.model.sc.enums.Semester;

import java.util.Date;

public class ImportantDates {
    private Date currentDate;
    private Date Drop_FALL;
    private Date DISC_FALL;
    private Date Drop_WINTER;
    private Date DISC_WINTER;
    private Date Drop_SUMMER;
    private Date DISC_SUMMER;

    public ImportantDates() {
        this.currentDate = new Date();
        int year = currentDate.getYear();
        this.Drop_FALL = new Date(year, 8, 18); //Sep. 18th
        this.DISC_FALL = new Date(year, 10, 6); // Nov. 6th
        this.Drop_WINTER = new Date(year, 0, 22); //Jan. 22nd
        this.DISC_WINTER = new Date(year, 2, 19); // Mar. 19th
        this.Drop_SUMMER = new Date(year, 4, 10); //May. 10th
        this.DISC_SUMMER = new Date(year, 6, 10); // Jul. 10th
    }

    public Date getDISC(String semester) {
        if(semester.contains("F")) {
            return DISC_FALL;
        }
        if(semester.contains("W")) {
            return DISC_WINTER;
        }
        else {
            return DISC_SUMMER;
        }
    }

    public Date getDNE(String semester) {
        if(semester.contains("F")) {
            return Drop_FALL;
        }
        if(semester.contains("W")) {
            return Drop_WINTER;
        }
        else {
            return Drop_SUMMER;
        }
    }
}
