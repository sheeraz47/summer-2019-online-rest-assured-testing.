package com.automation.tests.day2;

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

    private String baseURI = "http://ec2-34-227-143-104.compute-1.amazonaws.com:1000/ords/hr";

//    verify that status code is 200
    @Test
    public void test1(){
        Response response = given().
//                get("http://ec2-34-227-143-104.compute-1.amazonaws.com:1000/ords/hr/employees/");

                get(baseURI + "/employees"); // the above and this both are same

        System.out.println(response.getBody().asString()); // actual result is in one line, its looks weirred

        assertEquals(200, response.getStatusCode());

        System.out.println(response.prettyPrint()); //thats why we use prettyPrint method gives us nice reports which are easily readible

    }
}
