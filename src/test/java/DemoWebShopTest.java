import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static filters.CustomLogFilter.customLogFilter;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

public class DemoWebShopTest {
    String logoLinkAddress = "http://demowebshop.tricentis.com/Themes/DefaultClean/Content/images/logo.png",
            email = "ole@ole.com",
            password = "oleole",
            loginURL = "http://demowebshop.tricentis.com/login",
            indexURL = "http://demowebshop.tricentis.com";

    @Test
    void checkButtonLogOut() {
        step("get cookie by api and set to browser", () -> {
            String authCookie =
                    given()
                            .filter(customLogFilter().withCustomTemplates())
                            .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                            .formParam("Email", email)
                            .formParam("Password", password)
                            .when()
                            .post(loginURL)
                            .then()
                            .statusCode(302)
                            .extract()
                            .cookie("NOPCOMMERCE.AUTH");
            step("Open minimal content, because cookie can be set when site is opened", () ->
                    open(logoLinkAddress));

            step("Set cookie to browser", () ->
                    getWebDriver().manage().addCookie(
                            new Cookie("NOPCOMMERCE.AUTH", authCookie)));
        });
        step("check, that after log out clicking, the button sign in appears", () -> {
            open(indexURL);
            $(".ico-logout").click();
            $(".ico-login").shouldBe(visible);
        });
    }
}
