package com.model.request;

import com.model.sc.Student;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * A unit test for Tuition Request
 *
 * @author Freyja Jokulsdottir
 * @version 1.0
 * @since 03.12.2017
 */
public class TuitionRequestTest {
    private Student student = new Student();
    private TuitionRequest tuitionRequest = new TuitionRequest();
    private BigDecimal amount;

    @Before
    public void setUp() throws Exception {
        this.amount = new BigDecimal(7500.00);
        this.student.setNewTuition(this.amount);
    }

    @After
    public void tearDown() throws Exception {
        this.amount = null;
        this.student = null;
        this.tuitionRequest = null;
    }

    @Test
    public void getAmount() throws Exception {
        String amount = "" +this.amount;
        this.tuitionRequest.setAmount(amount);
        String expectedAmount = "" +this.student.getTuition();

        Assert.assertEquals(expectedAmount, this.tuitionRequest.getAmount());
    }

    @Test
    public void getCardNum() throws Exception {
        String cardNum = "1234-5678-9123-4567";
        this.tuitionRequest.setCardNum(cardNum);

        Assert.assertEquals(cardNum, this.tuitionRequest.getCardNum());
    }

    @Test
    public void getDate() throws Exception {
        String date = "03/19";
        this.tuitionRequest.setDate(date);

        Assert.assertEquals(date, this.tuitionRequest.getDate());
    }

    @Test
    public void getCvv() throws Exception {
        String cvv = "123";
        this.tuitionRequest.setCvv(cvv);

        Assert.assertEquals(cvv, this.tuitionRequest.getCvv());
    }

}