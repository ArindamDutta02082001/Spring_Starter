<h1> This is a rest api demo</h1>

<p>We follow the MVC model </p>
<p> controller  → service → repository → model<br/><br/>
controller → in this folder we will have the api endpoints , and service logic function are called here 
when endpoints are hit<br/>
@Restcontroller → the http endpoints are written under this function | it tells that the function
will contain http rest endpoints<br/><br/>
dto → this contains the request model to accept the http request <br/><br/>
service → the logic of backend after getting data in backend from endpoint | triggered when endpoint is hit <br/>
@Service → it tells that the function will contain the service logic of the rest endpoints <br/><br/>
entity/model → data models i.e attribute definition for the input to backend <br/><br/>
repository → creating data object & saving them to  database or in memory logic <br/>
@Repository → it tells that the function will contain the logic of the DB layer<br/><br/>
</p>

## Endpoints here
a. GET endpoint

    // here I am taking the employeeID by path parameter    
    @GetMapping("/api/v1/employee/{eid}")
    public Employee getEmployee(@PathVariable("eid") String employeeID)
    {
        return employeeRepository.getEmployee(employeeID);
    }

    // here I am taking the employeeID by requestBody
    @GetMapping("/employee")
    public Employee getEmployeee(@RequestBody String employeeID)
    {
        System.out.println("EmployeeId is passed by Request Body");
        return employeeService.getEmployee(employeeID);
    }

    //  here I am taking the employeeID by Query Parameter
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

<h3>Api here </h3>
1. GET - `localhost:9000/api/v1/allemployee`  to get all employee  <br/>
2. POST - `localhost:9000/api/v1/regemployee`  to register a new employee <br/>

<pre>
  <code>
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
  </code>
</pre>

3. PUT - `localhost:9000/api/v1/updemployee/{ unique ID of employee }`  to update an employee with ID

<pre>
  <code>
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
  </code>
</pre>


4. DELETE  -  `localhost:9000/api/v1/delemployee?eid={ unique ID of employee }`  to delete the employee with ID
    <pre>
      <code>
        Employee :89847878-490e-478f-93ef-352dc5285333 deleted
      </code>
    </pre>

## Lombok & Validation Dependency

### Installation

To use Lombok in your Spring Boot project, add the Lombok dependency to your pom.xml

### Annotations

- `@Getter`: Automatically generates getter methods for all fields in the class.
- `@Setter`: Automatically generates setter methods for all fields in the class.
- `@Builder`: Provides a builder pattern for easier object instantiation.
- `@AllArgsConstructor`: Generates a constructor with arguments for all fields in the class.

## Validation Dependency

## Installation

To enable validation in your Spring Boot project, add the Validation dependency to your `pom.xml`:

### Annotations

- `@NotNull (message = "Name field is required !!")` : the dto parameter cannot be null
- `@NotBlank (message = "Name field is required !!")` : the dto parameter cannot be null + cant be empty
- `@NotEmpty (message = "Name field is required !!")` : the dto parameter cannot be empty
- `@Max(60)` : the max value for the dto parameter can be 60
- `@Min(20)	` : the min value for the dto parameter can be 20
- `@Size(min = 2,max = 20,message = "min 2 and max 20 characters are allowed !!")`:
max and min characters in a String dto parameter

we have to make @Valid in the endpoint parameter | if in the incoming body any of the validation is
not found we get **400 BAD REQUEST**


<pre>
<code>
@PostMapping("/regemployee")
public Employee registerEmployee(@RequestBody @Valid Employeedto request) throws SQLException {
//code 
}
</code>
</pre>

# Properties used
<pre>
<code>
        String db_url = "jdbc:mysql://localhost:3306/employee_db_jdbc?createDatabaseIfNotExist=true";
        String db_username = "root";
        String db_password = "admin";
</code>
</pre>

# JDBC 
learn from doc