package com.automation.tests.day2;

import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class ORDSTests {

//    address of ORDS web service, that is running no AWS ec2.
//    dara is coming from SQL Oracle data base to this web service
//    during back-end testing with SQL developer and JDBC API
//    we were accessing data base directly
//    now, we gonna access web service

//    according to OOP conventions, all instance variable should be private
//    but, if we will make it public, it will not make ant difference for us
//    it's just good practice, so later we will not hesitate which keyword to use when it's gonna important

//    ec2-34-201-69-55.compute-1.amazonaws.com - my host, you have something else
//       /ords/hr or //ords/hr/employees - same for all

    private String baseURI = "http://ec2-34-227-143-104.compute-1.amazonaws.com:1000/ords/hr";

//    we start from given()
//    then we can specify type of request like: get(), put(), delete(), post()
//    and as parameter, we enter resource location (URI)
//    then we are getting response back. that response we can put into Response object
//    from response object, we can retrieve: body, header, status code
//    it works without RestAssured.given() because of static import

//    verify that status code is 200

    @Test
    public void test1(){
        Response response = given().get(baseURI + "/employees"); // static method thats coming from rest assured


                get(baseURI + "/employees");

//        System.out.println(response.getBody().asString()); // actual result is in one line, its looks weirred

        assertEquals(200, response.getStatusCode());

        System.out.println(response.prettyPrint()); //thats why we use prettyPrint method gives us nice reports which are easily readible

    }

//    #TASK: get employee with id 100 and verify that response returns status code 200 also, print body

    @Test
    public void test2(){

//                header stands for meta data
//                usually it's used to include cookies
//                in this example, we are specifying what kind of response type we need
//                because web server can return let's say json or xml
//                when we put header info "Accept", "application/json", we are saying that we need only json response

        Response response = given()
                .header("Accept", "application/json")//we tell to the server we want json files
                .get(baseURI + "/employees/100");

        int actualStatusCode = response.getStatusCode();

//       System.out.println(response.prettyPrint());

        assertEquals(200, actualStatusCode, response.prettyPrint() ); // we can print pretty in assert

//        get information about content type, you can retrieve from response object
        System.out.println("What kind of content server sends to you, in this response: "+ response.getHeader("Content-Type"));
    }


//    #Task: perform GET request to /regions, print body and all headers.

      @Test
    public void test3(){
        Response response = given().get(baseURI + "/regions");

        assertEquals(200, response.getStatusCode());

        Header header = response.getHeaders().get("Content-Type"); // we can specify that what we need "Content-type" from rest assured Header

        for(Header h: response.getHeaders()){ //we can print out all of them one by one
            System.out.println(h);

        }
        System.out.println("==========================");
        System.out.println(response.prettyPrint());

    }
}
