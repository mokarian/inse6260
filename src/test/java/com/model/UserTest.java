package com.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * A unit test for User object
 *
 * @author Freyja Jokulsdottir
 * @version 1.0
 * @since 03.12.2017
 */
public class UserTest {
    private User user;

    @Before
    public void setUp() throws Exception {
        this.user = new User();
    }

    @After
    public void tearDown() throws Exception {
        this.user = null;
    }

    @Test
    public void getUser_id() throws Exception {
        int expectedID = 1;
        this.user.setUser_id(expectedID);

        Assert.assertEquals(expectedID, this.user.getUser_id());
    }

    @Test
    public void getPassword() throws Exception {
        String expectedPsw = "123456";
        this.user.setPassword(expectedPsw);

        Assert.assertEquals(expectedPsw, this.user.getPassword());
    }

    @Test
    public void getName() throws Exception {
        String expectedName = "Freyja";
        this.user.setName(expectedName);

        Assert.assertEquals(expectedName, this.user.getName());
    }

    @Test
    public void getLastName() throws Exception {
        String expectedLastName = "Jokulsdottir";
        this.user.setLastName(expectedLastName);

        Assert.assertEquals(expectedLastName, this.user.getLastName());
    }

    @Test
    public void getEmail() throws Exception {
        String expectedEmail = "freyja.jokulsdottir@concordia.ca";
        this.user.setEmail(expectedEmail);

        Assert.assertEquals(expectedEmail, this.user.getEmail());
    }

    @Test
    public void getActive() throws Exception {
        int expectedActive = 1;
        this.user.setActive(expectedActive);

        Assert.assertEquals(expectedActive, this.user.getActive());
    }

    @Test
    public void getRoles() throws Exception {
        Role role1 = new Role();
        role1.setRole("STUDENT");
        role1.setId(1);
        Role role2 = new Role();
        role2.setRole("TEACHER");
        role2.setId(2);
        Set<Role> expectedRoles = new HashSet<>();
        expectedRoles.add(role1);
        expectedRoles.add(role2);

        this.user.setRoles(expectedRoles);

        Assert.assertEquals(expectedRoles, this.user.getRoles());
    }

}