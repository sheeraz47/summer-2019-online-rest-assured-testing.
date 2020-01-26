package com.automation.tests.day3;

import com.automation.utilities.ConfigurationReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ORDSTestDAy3 {

    @BeforeAll
    public static void setup(){
         baseURI = ConfigurationReader.getProperty("ords.uri");
    }

    //accept("application/json") shortcut for header("Accept", "application/json")
    //we are asking for json as a response

    @Test
    public void test1(){
            given().
                    accept("application/json").
                    get("/employees").
            then().
                    assertThat().statusCode(200).
                    and().assertThat().contentType("application/json").
                    //log().ifError(); // if the error then its printed otherwise test pass
                    log().all(true);

    }

    //path parameter - to point on specific resource /employee/:id/ - id it's a path parameter
    //query parameter - to filter results, or describe new resource :
    // POST /users?name=James&age=60&job-title=SDET
    //or to filter GET /employee?name=Jamal get all employees with name Jamal

    @Test
    public void test2(){
        given().
                accept("application/json").
                pathParam("id", 100).
                when().get("employees/{id}").
                then().assertThat().statusCode(200).
                        and().assertThat().body("employee_id",is (100),
                                                "department_id" , is (90),
                                                "last_name" ,  is ("King")).
                        log().all(true);

    }

    /**
     * given path parameter is "/regions/{id}"
     * when user makes get request
     * and region id is equals to 1
     * then assert that status code is 200
     * and assert that region name is Europe
     */

    @Test
    public void test3(){
        given().
                accept("application/json").
                pathParam("id" ,1).
        when().get("/regions/{id}").
                then().assertThat().statusCode(200).
                and().assertThat().body("region_name", is("Europe")).
                log().all(true);

    }

    /** ####TASK#####
     * Given accept type as JSON
     * And path parameter is id
     * When user sends get request to /locations
     *  Then user verifies that status code is 200
     *  And user verifies that location_id is 1700
     *  And user verifies that postal_code is 98199
     *  And user verifies that city is Seattle
     *  And user verifies that state_province is Washington
     */

    @Test
    public void test4(){
        given().
                accept("application/json").
                pathParam("id", 1700).
        when().get("/locations/{id}").
                then().assertThat().statusCode(200).
                and().assertThat().body("location_id", is(1700),
                                  "street_address", is ("2004 Charade Rd"),
                                           "postal_code", is ("98199"),
                                           "city" , is("Seattle"),
                                           "state_province" , is ("Washington"),
                                           "country_id", is("US")).
                    log().all(true);



    }




}
