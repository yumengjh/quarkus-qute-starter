package com.example.web;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

@QuarkusTest
class StarterResourceTest {

    @Test
    void rendersDefaultHome() {
        given().when().get("/").then()
                .statusCode(200).contentType("text/html")
                .body(containsString("你好，开发者！"))
                .body(containsString("/css/app.css"));
    }

    @Test
    void escapesUserInputInHtmlAndAttributes() {
        given().queryParam("name", "\"><script>alert(1)</script>")
                .when().get("/").then().statusCode(200)
                .body(containsString("&lt;script&gt;"))
                .body(not(containsString("<script>")))
                .body(not(containsString("value=\"\"><script>")));
    }

    @Test
    void returnsJsonGreeting() {
        given().queryParam("name", "  Qute  ").when().get("/api/hello").then()
                .statusCode(200).contentType("application/json")
                .body("message", equalTo("你好，Qute！"));
    }

    @Test
    void blankNameUsesDefault() {
        given().queryParam("name", "  ").when().get("/api/hello").then()
                .statusCode(200).body("message", equalTo("你好，开发者！"));
    }

    @Test
    void servesStylesheet() {
        given().when().get("/css/app.css").then()
                .statusCode(200).contentType("text/css");
    }
}
