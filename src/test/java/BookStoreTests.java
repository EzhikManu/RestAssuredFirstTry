import io.qameta.allure.restassured.AllureRestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static filters.CustomLogFilter.customLogFilter;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class BookStoreTests {
    String requestURL = "https://demoqa.com/Account/v1/GenerateToken";
    static Map<String, String> data = new HashMap<>();
    @BeforeAll
    static void beforeAll() {
        data.put("userName", "alex");
        data.put("password", "asdsad#frew_DFS2");
    }

    @Test
    void generateTokenTest() {
        given()
                .filter(new AllureRestAssured())
                .contentType("application/json")
                .body(data)
                .when()
                .post(requestURL)
                .then()
                .log().ifError()
                .statusCode(200)
                .body("status", is("Success"))
                .body("token", is(notNullValue()));
    }
    @Test
    void generateTokenWithSchema() {
        given()
                .filter(new AllureRestAssured())
                .contentType("application/json")
                .body(data)
                .when()
                .post(requestURL)
                .then()
                .log().ifError()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/generateTokenSchema.json"))
                .body("status", is("Success"))
                .body("token", is(notNullValue()));
    }
    @Test
    void generateTokenWithSchemaAndPrettyAllure() {
        given()
                .filter(customLogFilter().withCustomTemplates())
                .contentType("application/json")
                .body(data)
                .when()
                .post(requestURL)
                .then()
                .log().ifError()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/generateTokenSchema.json"))
                .body("status", is("Success"))
                .body("token", is(notNullValue()));
    }
}
