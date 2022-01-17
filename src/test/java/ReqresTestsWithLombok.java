import lombok.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static Specs.ReqresSpec.reqresRequest;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReqresTestsWithLombok {
    String registrURL = "https://reqres.in/api/register",
            singleUserURL = "https://reqres.in/api/users/2",
            listResourceURL = "https://reqres.in/api/unknown",
            singleUserNotFoundURL = "https://reqres.in/api/users/23";

    @Test
    @DisplayName("successfulRegistration test with lombok model")
    void successfulRegistration() {
        SuccsessfulRegRequest req = new SuccsessfulRegRequest();
        req.email = "eve.holt@reqres.in";
        req.password = "pistol";
        SuccessfulRegResponse res =
                given()
                        .spec(reqresRequest)
                        .body(req)
                        .when()
                        .post(registrURL)
                        .then()
                        .log().ifError()
                        .statusCode(200)
                        .log().all()
                        .extract().as(SuccessfulRegResponse.class);
        assertEquals(4, res.id);
        assertThat(res.token).isNotNull();
    }

    @Test
    @DisplayName("singleUserNotFound test with specification")
    void singleUserNotFound() {
        given()
                .spec(reqresRequest)
                .when()
                .get(singleUserNotFoundURL)
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("unSuccessfulRegistration test with lombok model")
    void unSuccessfulRegistration() {
        UnsuccessfulRegRequest unSucReq = new UnsuccessfulRegRequest();
        unSucReq.email = "sydney@fife";
        UnsuccessfulRegResponse unSucRes =
                given()
                        .spec(reqresRequest)
                        .body(unSucReq)
                        .when()
                        .post(registrURL)
                        .then()
                        .statusCode(400)
                        .log().all()
                        .extract().as(UnsuccessfulRegResponse.class);
        assertThat(unSucRes.error).isEqualTo("Missing password");
    }

    @Test
    @DisplayName("singleUser test with lombok model")
    void singleUser() {
        SingleUserResponse userData =
                given()
                        .spec(reqresRequest)
                        .when()
                        .get(singleUserURL)
                        .then()
                        .statusCode(200)
                        .log().all()
                        .extract().as(SingleUserResponse.class);
        assertThat(userData.first_name).isEqualTo("Janet");
        assertThat(userData.last_name).isEqualTo("Weaver");
        assertThat(userData.id).isEqualTo(2);
        assertThat(userData.email).isEqualTo("janet.weaver@reqres.in");

    }

    @Test
    @DisplayName("update test with lombok model")
    void update() {
        UpdateRequest upReq = new UpdateRequest();
        upReq.name = "morpheus";
        upReq.job = "zion resident";
        UpdateResponse upRes =
                given()
                        .spec(reqresRequest)
                        .body(upReq)
                        .when()
                        .patch(singleUserURL)
                        .then()
                        .statusCode(200)
                        .log().all()
                        .extract().as(UpdateResponse.class);
        assertThat(upRes.name).isEqualTo(upReq.name);
        assertThat(upRes.job).isEqualTo(upReq.job);
        assertThat(upRes.updatedAt).isNotNull();
    }

    @Test
    @DisplayName("listResource test with groovy")
    void listResource() {
        given()
                .spec(reqresRequest)
                .when()
                .get(listResourceURL)
                .then()
                .statusCode(200)
                .log().all()
                .body("data.findAll{it.name}.name.flatten()", hasItem("tigerlily"))
                .body("support.url", is("https://reqres.in/#support-heading"));
    }
}
