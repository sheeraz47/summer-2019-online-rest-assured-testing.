package com.automation.tests.day8;

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

public class BasicAuthenticationTests {

    @BeforeAll
    //https will not work, because of SSL certificate issues
    //this website doesn't have it
    public static void before(){
        baseURI = "http://practice.cybertekschool.com/";
    }

    @Test
    @DisplayName("Verify that authorization header contains Basic Auth")
    public void test1(){
       given().
                auth().basic("admin", "admin").
        when().
                get("basic_auth").prettyPeek().
        then().assertThat().statusCode(200);

    }
}
