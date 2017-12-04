package com.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * A unit test for Role
 *
 * @author Freyja Jokulsdottir
 * @version 1.0
 * @since 03.12.2017
 */
public class RoleTest {
    private Role role;

    @Before
    public void setUp() throws Exception {
        this.role = new Role();
    }

    @After
    public void tearDown() throws Exception {
        this.role = null;
    }

    @Test
    public void getId() throws Exception {
        int expectedID = 1;
        this.role.setId(expectedID);

        Assert.assertEquals(expectedID, this.role.getId());
    }

    @Test
    public void getRole() throws Exception {
        String expectedRole = "STUDENT";
        this.role.setRole(expectedRole);

        Assert.assertEquals(expectedRole, this.role.getRole());
    }

}