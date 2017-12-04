package com.model.request;

import com.model.sc.Student;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * A unit test for Contact Information Request
 *
 * @author Freyja Jokulsdottir
 * @version 1.0
 * @since 03.12.2017
 */
public class ContactInfoRequestTest {
    private Student student = new Student();

    @Before
    public void setUp() throws Exception {
        this.student.setAddress("91 University Avenue, Montreal, H3K 1Z2, Quebec");
        this.student.setPhone("(514)888-0000");
    }

    @After
    public void tearDown() throws Exception {
        this.student = null;
    }

    @Test
    public void getPhone() throws Exception {
        ContactInfoRequest contact = new ContactInfoRequest();
        contact.setPhone(this.student.getPhone());

        Assert.assertEquals(this.student.getPhone(), contact.getPhone());
    }

    @Test
    public void getAddress() throws Exception {
        ContactInfoRequest contact = new ContactInfoRequest();
        contact.setAddress(this.student.getAddress());

        Assert.assertEquals(this.student.getAddress(), contact.getAddress());
    }

}