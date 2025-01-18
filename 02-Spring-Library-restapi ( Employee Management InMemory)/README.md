# This is a rest api demo
We follow the MVC model
>FLOW  → controller  → service → repository → model


1. **controller** → in this folder we will have the api endpoints , and service logic function are called here
   when endpoints are hit<br/>
   @RestController → the http endpoints are written under this function | it tells that the function
   will contain http rest endpoints
2. **dto** → helps in conversion from JSON data from **UI ←→ Java Object**  | This contains the request model to accept the http request JSON data from the UI <br/>
3. **service / DAO / BO** → the logic of backend after getting data in backend from endpoint | triggered when endpoint is hit <br/>
   @Service → it tells that the function will contain the service logic of the rest endpoints
4. **entity/model** → helps in conversion from **Java Object ←→ Database Schema / Tables** | It is a data models or objects i.e attribute definition for the database or schema <br/>
5. **repository** → creating data object & saving them to  database or in memory logic <br/>
   @Repository → it tells that the function will contain the logic of the DB layer<br/><br/>


### Api here

1. GET - `localhost:9000/api/v1/allemployee`  to get all employee  <br/>
2. POST - `localhost:9000/api/v1/regemployee`  to register a new employee <br/>

<pre>
    payload :
    {
    "efname": "Arindam" ,
    "elname": "Dutta" ,
    "address" :{
        "address":"Bhedia , West Bengal" ,
        "colony": "Near Huttala 7/8 block",
        "house":"2/5 SC block",
        "pin":"7131216"
    },
    "mobile" : 9620922432 ,
    "department" : "Computer Science"
    }

    response :
    {
    "uniqueID": "b8238b7e-fc71-4ad2-9238-50933a777527",
    "dateCreated": 1706965566755,
    "address": {
        "address": "Bhedia , West Bengal",
        "colony": "Near Huttala 7/8 block",
        "house": "2/5 SC block",
        "pin": 7131216
    },
    "efname": "Arindam",
    "elname": "Dutta",
    "mobile": "9620922432",
    "department": "Computer Science"
    }
</pre>

3. PUT -` localhost:9000/api/v1/updemployee/{ unique ID of employee }`  to update an employee with ID

<pre>
    payload :
{
    "efname": "Arindam",
    "elname": "Dutta",
    "address": {
        "address": "Bhedia , West Bengal , Kolkata",
        "colony": "Near Huttala 7/8 block",
        "house": "2/5 SC block",
        "pin": "7131216"
    },
    "mobile": 9620922432,
    "department": "Computer Science"
}

  response :
    {
    "uniqueID": "89847878-490e-478f-93ef-352dc5285333",
    "dateCreated": 1706961767122,
    "address": {
        "address": "Bhedia , West Bengal , Kolkata",
        "colony": "Near Huttala 7/8 block",
        "house": "2/5 SC block",
        "pin": 7131216
    },
    "department": "Computer Science",
    "elname": "Dutta",
    "efname": "Arindam",
    "mobile": "9620922432"
}
</pre>


5. DELETE  -  `localhost:9000/api/v1/delemployee?eid={ unique ID of employee }`  to delete the employee with ID

        Employee :89847878-490e-478f-93ef-352dc5285333 deleted


### RestController`s code here :
a. GET endpoint

    // employeeID passed by path parameter    
    @GetMapping("/api/v1/employee/{eid}")
    public Employee getEmployee(@PathVariable("eid") String employeeID)
    {
        return employeeRepository.getEmployee(employeeID);
    }

    // employeeID passed by requestBody
    @GetMapping("/employee")
    public Employee getEmployeee(@RequestBody String employeeID)
    {
        System.out.println("EmployeeId is passed by Request Body");
        return employeeService.getEmployee(employeeID);
    }

    // employeeID passed by Query Parameter
    @GetMapping("/employee")
    public Employee getEmployeeee(@RequestParam("eid") String employeeID)
    {
        System.out.println("EmployeeId is passed by Request Body");
        return employeeService.getEmployee(employeeID);
    }


b. POST endpoint

    @PostMapping("/api/v1/regemployee")                                                
    public Employee registerEmployee(@RequestBody Employeedto request)
    {
        Employee newEmployee = employeeService.createEmployee(request);
        employeeRepository.saveEmployee(newEmployee);
    return newEmployee;

    }

c. PUT endpoint

    @PutMapping("/api/v1/updemployee/{eid}")
    public Employee updateEmployee (@RequestBody UpdateEmployeedto request , @PathVariable("eid") String employeeID)
    {
        return employeeService.updateEmployee(request, employeeID);
    }

d. DELETE endpoint

    @DeleteMapping("/api/v1/delemployee")
    public String deleteEmployee(@RequestParam("eid") String employeeID)
    {
        return employeeService.deleteEmployee(employeeID);
    }

### Status codes
link : [all codes](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)

### application.properties file
<pre>
        String db_url = "jdbc:mysql://localhost:3306/employee_db?createDatabaseIfNotExist=true";
        String db_username = "root";
        String db_password = "admin";
</pre>