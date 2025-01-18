package com.springboot.repository;

import com.springboot.model.Employee;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EmployeeRepository {

    /**
     * the repository file contains the code of DB connection and data storage
     */

    // The InMemory DB of the Project
    Map<String , Employee> empMap = new HashMap<>();


    // to save a particular employee in the DB
    public void saveEmployee(Employee employee)
    {
        empMap.put(employee.getUniqueID() , employee);
    }


    // to get a employee from the DB
    public Employee getEmployeeFromDB(String Eid)
    {
        return empMap.getOrDefault(Eid , null);
    }


    // returns a list of all employees from the DB
    public List<Employee> getAllEmployeeFromDB ()
    {
        List<Employee> allEmployeeList = new ArrayList<>();

        for(Map.Entry<String , Employee> e : empMap.entrySet())
        {
            allEmployeeList.add(e.getValue());
        }

        return allEmployeeList;
    }

    // return total count of employee i.e Map size
    public long getTotalNoofEmployees()
    {
        return empMap.size();
    }



    // remove an employee from the DB
    public boolean removeEmployee ( String empid)
    {
        if(!empMap.containsKey(empid))
            return false;

     empMap.remove(empid);
     return true;
    }



}
