package com.automation.tests.homework;

import com.automation.pojo.User;
import com.automation.utilities.APIUtilities;
import org.junit.jupiter.api.BeforeAll;

import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

public class Homework1 {

    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("ui.names.uri");
    }


    @Test
    @DisplayName("No params test")
    public void test1() {
        Response response = given().
                accept(ContentType.JSON).
                when().
                get().prettyPeek();

        response.then().assertThat().
                statusCode(200).
                contentType(ContentType.JSON).
                body("name", not(empty())).
                body("surname", not(empty())).
                body("gender", not(empty())).
                body("region", not(empty()));

    }

    @Test
    @DisplayName("Gender test")
    public void test2() {
        String gender = "male";

        Response response = given().
                accept(ContentType.JSON).
                queryParam("gender", gender).
                when().
                get().prettyPeek();

        response.then().assertThat().
                statusCode(200).
                contentType(ContentType.JSON).
                body("gender", is(gender));

    }

    @Test
    @DisplayName("2 params test")
    public void test3() {
        String gender = "male";
        String region = "Colombia";

        Response response = given().
                accept(ContentType.JSON).
                queryParam("gender", gender).
                queryParam("region", region).
                when().
                get().prettyPeek();

        response.then().assertThat().
                statusCode(200).
                contentType(ContentType.JSON);

        String actualGender = response.jsonPath().getString("gender");
        String actualRegion = response.jsonPath().getString("region");

        assertEquals(gender, actualGender, "Gender is wrong!");
        assertEquals(region, actualRegion, "Region is wrong!");
    }

    @Test
    @DisplayName("Invalid gender test")
    public void test4() {
        String gender = "invalid";

        Response response = given().
                accept(ContentType.JSON).
                queryParam("gender", gender).
                when().
                get().prettyPeek();

        response.then().assertThat().
                statusCode(400).
                contentType(ContentType.JSON).
                body("error", containsString("Invalid gender"));

    }

    @Test
    @DisplayName("Invalid region test")
    public void test5() {
        String region = "invalid";

        Response response = given().
                accept(ContentType.JSON).
                queryParam("region", region).
                when().
                get().prettyPeek();

        response.then().assertThat().
                statusCode(400).
                contentType(ContentType.JSON).
                body("error", containsString("Region or language not found"));

    }

    @Test
    @DisplayName("Amount and regions test")
    public void test6() {
        Response response = given().
                accept(ContentType.JSON).
                queryParam("region", "germany").
                queryParam("gender", "male").
                queryParam("amount", 20).
                when().
                get();

        response.then().assertThat().statusCode(200).contentType(ContentType.JSON);

        List<User> people = response.jsonPath().getList("", User.class);

        //for demo
        for (User u : people) {
            u.printFullName();
        }

        boolean hasDuplicates = APIUtilities.hasDuplicates(people);

        assertFalse(hasDuplicates, "List has some duplicates");

    }

    @Test
    @DisplayName("3 params test")
    public void test7() {
        Response response = given().
                accept(ContentType.JSON).
                queryParam("region", "Germany").
                queryParam("gender", "male").
                queryParam("amount", 20).
                when().
                get().prettyPeek();

        response.then().assertThat().
                statusCode(200).
                contentType(ContentType.JSON).
                body("gender", everyItem(is("male"))).
                body("region", everyItem(is("Germany")));



    }

    @Test
    @DisplayName("Amount count test")
    public void test8() {
        int count = 20;
        Response response = given().
                accept(ContentType.JSON).
                queryParam("amount", count).
                when().
                get().prettyPeek();

        response.then().assertThat().
                statusCode(200).
                contentType(ContentType.JSON).
                body("size()", is(count));
    }

}




