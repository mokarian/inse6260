package com.model.request;

import java.util.List;

/**
 * A list of grade requests
 *
 * @author Maysam Mokarian
 * @version 1.0
 * @since 03.12.2017
 */
public class GradeList {
    List<GradeRequest> gradeRequests;

    public List<GradeRequest> getGradeRequests() {
        return gradeRequests;
    }

    public void setGradeRequests(List<GradeRequest> gradeRequests) {
        this.gradeRequests = gradeRequests;
    }
}
