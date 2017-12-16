package com.model.sc;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by maysam.mokarian on 12/16/2017.
 */
public class GPA {
    private float cGpa;
    Map<String, Float> anuallGpa= new HashMap<>();

    public float getcGpa() {
        return cGpa;
    }

    public void setcGpa(float cGpa) {
        this.cGpa = cGpa;
    }

    public Map<String, Float> getAnuallGpa() {
        return anuallGpa;
    }

    public void setAnuallGpa(Map<String, Float> anuallGpa) {
        this.anuallGpa = anuallGpa;
    }
}
