import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static filters.CustomLogFilter.customLogFilter;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class ReqresTests {
    String registrURL = "https://reqres.in/api/register",
            singleUserURL = "https://reqres.in/api/users/2",
            singleUserNotFoundURL = "https://reqres.in/api/users/23";

    @Test
    void successfulRegistration() {
        given()
                .filter(customLogFilter().withCustomTemplates())
                .contentType(ContentType.JSON)
                .body("{\"email\": \"eve.holt@reqres.in\",\"password\": \"pistol\"}")
                .when()
                .post(registrURL)
                .then()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"))
                .body("id", is(4));
    }

    @Test
    void singleUserNotFound() {
        given()
                .filter(customLogFilter().withCustomTemplates())
                .contentType(ContentType.JSON)
                .when()
                .get(singleUserNotFoundURL)
                .then()
                .statusCode(404);
    }

    @Test
    void unSuccessfulRegistration() {
        given()
                .filter(customLogFilter().withCustomTemplates())
                .contentType(ContentType.JSON)
                .body("{\"email\": \"sydney@fife\"}")
                .when()
                .post(registrURL)
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }
    @Test
    void singleUser() {
        given()
                .filter(customLogFilter().withCustomTemplates())
                .contentType(ContentType.JSON)
                .when()
                .get(singleUserURL)
                .then()
                .statusCode(200)
                .body("data.first_name", is("Janet"))
                .body("support.url", is("https://reqres.in/#support-heading"));
    }
    @Test
    void update() {
        given()
                .filter(customLogFilter().withCustomTemplates())
                .contentType(ContentType.JSON)
                .body("{\"name\": \"morpheus\",\"job\": \"zion resident\"}")
                .when()
                .patch(singleUserURL)
                .then()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }
}
