package com.springboot.model;

import com.springboot.repository.EmployeeRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Inside model folder we will have
 * the employee update model or schema
 * The employee model contains
 * {
 * 	   "efname": "Arindam" ,
 *     "elname": "Dutta" ,
 *     "address" :"Bhedia , West Bengal" ,
 *     "mobile" : 9620922432 ,
 *     "department" : "Computer Science"
 * }
 */
@Component
public class Employee {

    private String uniqueID = UUID.randomUUID().toString();
    private long dateCreated = System.currentTimeMillis();

    private long slNo = new EmployeeRepository().getTotalNoofEmployees();

    private String EFName;

    private String ELName;

    private Address Address;

    private String Mobile;

    private String Department;


    // getter & setter for the Employee attributes

    // getter & setter for  Employee Unique ID
    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String newuniqueID) {
        this.uniqueID = newuniqueID;
    }

    // getter & setter for  Employee Created date
    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long newDate)
    {
        this.dateCreated = newDate;
    }


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
        Address = address;
    }

    // getter & setter for  Employee Mobile
    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    // getter & setter for  Employee Department
    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }



}
