package com.model.request;

/**
 * A student contact information request
 *
 * @author Maysam Mokarian
 * @version 1.0
 * @since 11.11.2017
 */
public class ContactInfoRequest {
    private String phone;
    private String address;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
