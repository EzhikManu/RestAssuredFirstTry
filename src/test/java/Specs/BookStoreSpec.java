package Specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.HashMap;
import java.util.Map;

import static filters.CustomLogFilter.customLogFilter;
import static io.restassured.RestAssured.with;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class BookStoreSpec {
    public static Map<String, String> generateData() {
        Map<String, String> data = new HashMap<>();
        data.put("userName", "alex");
        data.put("password", "asdsad#frew_DFS2");
        return data;
    }

    public static RequestSpecification request = with()
            .filter(customLogFilter().withCustomTemplates())
            .contentType(ContentType.JSON)
            .body(generateData());

    public static ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectBody("status", is("Success"))
            .expectBody("token", is(notNullValue()))
            .build();
}
