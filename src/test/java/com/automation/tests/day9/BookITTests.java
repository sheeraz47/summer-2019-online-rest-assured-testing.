package com.automation.tests.day9;


import com.automation.utilities.APIUtilities;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;


import com.automation.utilities.ConfigurationReader;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;


public class BookITTests {

    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("bookit.qa1");
    }

    /**
     * Given accept content type as JSON
     * When user sends get requests to /api/rooms
     * Then user should get 401 status code
     *
     */

    @Test
    @DisplayName("Verify that user cannot access bookit API without providing credentials")
    public void test1(){
        given().
                accept(ContentType.JSON).
                when().
                get("/api/rooms").
                then().assertThat().statusCode(401).log().all(true);
        //this service doesn't return 401, it returns 422
        //is it correct or wrong? good time talk to developer and check business requirements
    }

    /**
     * Given accept content type as JSON
     * And user provides invalid token
     * When user sends get requests to /api/rooms
     * Then user should get 422 status code
     *
     */

    @Test
    @DisplayName("Verify that system doesn't accept invalid token")
    public void test2(){
        //
        //same procedure: you need to provide token
        //since bearer token was originally created for oauth 2.0
        //it works in the same way
        //auth().oauth2()
        //500 Server Error - server is in trouble
        given().
                accept(ContentType.JSON).
                header("Authorization", "invalid token").
                when().
                get("/api/rooms").prettyPeek().
                then().assertThat().statusCode(422);
    }

    /**
     * given valid bearer token
     * when user performs GET request to "/api/rooms"
     * then user should get list of rooms in the payload
     * and status code 200
     */
    @Test
    public void test3(){
        given().auth().oauth2(APIUtilities.getTokenForBookit()).
                accept(ContentType.JSON).
                when().
                get("/api/rooms").prettyPeek();
    }
}
