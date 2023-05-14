package bitxon.jsonview;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import io.restassured.RestAssured;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class RestApiTest {

    private static final String BODY_WITH_ALL_FIELDS = """
        {
            "name":"Mike",
            "externalId":"externalValue",
            "internalUuid":"internalValue"
        }
        """;

    @LocalServerPort
    Integer port;

    @BeforeEach
    void beforeEach() {
        RestAssured.port = port;
    }

    @ParameterizedTest
    @ValueSource(strings = {"/v1/internal", "/v2/internal"})
    void getInternalUser(String path) {
        //@formatter:off
        when()
            .get(path)
        .then()
            .statusCode(200)
            .body(allOf(
                containsString("internalValue"),
                not(containsString("externalValue"))
            ));
        //@formatter:on
    }

    @ParameterizedTest
    @ValueSource(strings = {"/v1/external", "/v2/external"})
    void getExternalUser(String path) {
        //@formatter:off
        when()
            .get(path)
        .then()
            .statusCode(200)
            .body(allOf(
                not(containsString("internalValue")),
                containsString("externalValue")
            ));
        //@formatter:on
    }

    @ParameterizedTest
    @ValueSource(strings = {"/v1/internal", "/v2/internal"})
    void postInternalUser(String path) {
        //@formatter:off
        given()
            .contentType(ContentType.JSON)
            .body(BODY_WITH_ALL_FIELDS)
        .when()
            .post(path)
        .then()
            .statusCode(200);
        //@formatter:on
    }

    @ParameterizedTest
    @ValueSource(strings = {"/v1/external", "/v2/external"})
    void postExternalUser(String path) {
        //@formatter:off
        given()
            .contentType(ContentType.JSON)
            .body(BODY_WITH_ALL_FIELDS)
        .when()
            .post(path)
        .then()
            .statusCode(200);
        //@formatter:on
    }

}
