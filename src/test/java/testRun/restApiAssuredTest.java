package testRun;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.assertj.core.api.Assertions.assertThat;


public class restApiAssuredTest {


    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    public void test1emptyList() {
        given()
                .params("page", 0)
                .params("size", 10)
                .when()
                .get("/goods/list")
                .then()
                .log().all()
                .statusCode(200)
                .body("goods", empty());

    }


    @Test
    public void test2emptyList() {
        RequestSpecification requestSpec = given()
                .baseUri(RestAssured.baseURI)
                .port(RestAssured.port)
                .basePath("/goods/list")
                .param("page", 0)
                .param("size", 10);

        Response response = given()
                .spec(requestSpec)
                .get();

        response.then().statusCode(200);

        List<?> goodsList = response.jsonPath().getList("goods");
        assertThat(goodsList).isNullOrEmpty();


    }

    @Test
    public void test3emptyList() {
        String uniqueName = "String_" + UUID.randomUUID();

        String jsonBody = """
                {
                 "name": "%s",
                 "price": 0.1
                }
                """.formatted(uniqueName);

        given()
                .auth().basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .statusCode(200);

        Response response = given()
                .auth().basic("admin", "secret123")
                .param("page", 0)
                .param("size", 10)
                .when()
                .get("/goods/list")
                .then()
                .log().all()
                .statusCode(200)
                .body("goods.name", hasItem(uniqueName))
                .extract()
                .response();

        //  Находим id созданного товара
        List<String> names = response.jsonPath().getList("goods.name");
        List<Integer> ids = response.jsonPath().getList("goods.id");
        int idx = names.indexOf(uniqueName);
        Integer createdId = ids.get(idx);

        given()
                .auth().basic("admin", "secret123")
                .when()
                .delete("/goods/" + createdId)
                .then()
                .log().all()
                .statusCode(200);
    }



    @Test
    public void test4emptyList() {
        String uniqueName = "String";

        given()
                .auth().basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "name", uniqueName,
                        "price", 0.1
                ))
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .statusCode(200);


        Response response = given()
                .auth().basic("admin", "secret123")
                .param("page", 0)
                .param("size", 10)
                .when()
                .get("/goods/list")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        //  AssertJ — наш товар в списке
        List<String> names = response.jsonPath().getList("goods.name");
        assertThat(names).contains(uniqueName);

        // Достаём id параллельным списком
        List<Integer> ids = response.jsonPath().getList("goods.id");

        // 5. Находим индекс нашего товара
        int idx = names.indexOf(uniqueName);
        assertThat(idx).isGreaterThan(-1);
        Integer createdId = ids.get(idx);

        given()
                .auth().basic("admin", "secret123")
                .when()
                .delete("/goods/" + createdId)
                .then()
                .log().all()
                .statusCode(200);
    }

}