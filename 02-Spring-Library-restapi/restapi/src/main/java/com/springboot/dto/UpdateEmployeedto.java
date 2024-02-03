package com.springboot.dto;
import com.springboot.model.Address;
/**
 * DTO - consists the model to accept the JSON body
 * This dto will acceot the incoming http request body for the second POST mapping i.e,
 * to update a new Employee
 */

public class UpdateEmployeedto {


    private String EFName;

    private String ELName;

    private Address Address;

    private String Mobile;

    private String Department;


    // getter & setter for the Employee update Dto attributes

    // getter & setter for  Employee First Name
    public String getEFName() {
        return EFName;
    }

    public void setEFName(String EFName) {
        this.EFName = EFName;
    }


    // getter & setter for  Employee Last Name
    public String getELName() {
        return ELName;
    }

    public void setELName(String ELName) {
        this.ELName = ELName;
    }

    // getter & setter for  Employee Address
    public Address getAddress() {
        return Address;
    }

    public void setAddress(Address address) {
        this.Address = address;
    }

    // getter & setter for  Employee Mobile
    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        this.Mobile = mobile;
    }

    // getter & setter for  Employee Department
    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        this.Department = department;
    }
}
