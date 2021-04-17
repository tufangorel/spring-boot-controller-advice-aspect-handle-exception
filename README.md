## spring-boot-controller-advice-aspect-handle-exception

1- Log exception occurs at service layer to the database log table from @AfterThrowing advice.<br/>
2- Show a meaningful error message to the client by using @RestControllerAdvice.<br/>

### Run with
 Run: `spring-boot-controller-advice-aspect-handle-exception > mvnw.cmd spring-boot:run`
 
### Tech Stack
Java 11 <br/>
H2 Database Engine <br/>
spring boot <br/>
spring data jpa <br/>
spring web <br/>
spring aspects <br/>
hibernate <br/>
logback <br/>
maven <br/>
junit <br/>
<br/>

## API OPERATIONS
### Save customer sucessfully to database

Method : HTTP.POST <br/>
URL : http://localhost:8080/customer/save <br/>

Request : 
<pre>
{ 
  "name":"name1",
  "age":1,
  "addresses":[
                {"streetName":"software","city":"ankara","country":"TR"}
              ]
}
</pre><br/>


Response : 

HTTP response code 200 <br/>
<pre>
{
    "id": 1,
    "name": "name1",
    "age": 1,
    "addresses": [
        {
            "id": 1,
            "streetName": "software",
            "city": "ankara",
            "country": "TR"
        }
    ]
}
</pre><br/>

### Create exception and trigger @AfterThrowing aspect. Insert log message into log table. 

Method : HTTP.POST <br/>
URL : http://localhost:8080/customer/save <br/>

Request : 
<pre>
{
    "name": null,
    "age": null
}
</pre><br/>

Response :

HTTP response code 500 <br/>
<pre>
{
    "timestamp": "2021-04-13T19:29:07.112+00:00",
    "message": "not-null property references a null or transient value : com.company.customerinfo.model.Customer.age; nested exception is org.hibernate.PropertyValueException:   not-null property references a null or transient value : com.company.customerinfo.model.Customer.age",
    "details": "uri=/customer/save"
}
</pre><br/>

### List entries in log table.

Method : HTTP.GET <br/>
URL : http://localhost:8080/log/list <br/>

Input Test Data : No input, just fire the request. <br/>

Request : 
<pre>
{
}
</pre><br/>

Response : 

HTTP response code 200 <br/>
<pre>
[
    {
        "id": 1,
        "message": "org.hibernate.PropertyValueException: not-null property references a null or transient value : com.company.customerinfo.model.Customer.age"
    }
]
</pre><br/>

