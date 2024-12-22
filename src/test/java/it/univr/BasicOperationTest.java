package it.univr;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BasicOperationTest {
    @BeforeAll
    public static void beforeAll() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void addOneEbook(){
        final String body = Utils.getEbookJson("L'arte della Guerra", "Sun Tzu",
                "Filosofia", 10);

        given()
                .contentType("application/json")
                .body(body)
                .when()
                .post("/ebook")
                .then()
                .statusCode(200)
                .body("title", Matchers.equalTo("L'arte della Guerra"))
                .body("author", Matchers.equalTo("Sun Tzu"))
                .body("genre", Matchers.equalTo("Filosofia"))
                .body("monthlyReaders", Matchers.equalTo(10));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void getOneEbook(){
        Utils.initDatabase();

        given()
                .pathParam("ebookId", 2)
                .when()
                .get("/ebook/{ebookId}")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(2))
                .body("title", Matchers.equalTo("I Promessi Sposi"))
                .body("author", Matchers.equalTo("Alessandro Manzoni"))
                .body("genre", Matchers.equalTo("Romanzo Storico"))
                .body("monthlyReaders", Matchers.equalTo(1200));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void removeEbook(){
        Utils.initDatabase();

        given()
                .param("id", 2)
                .when()
                .delete("/ebook")
                .then()
                .statusCode(200);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void removeEbookAndCheck(){
        Utils.initDatabase();

        given()
                .param("id", 2)
                .when()
                .delete("/ebook")
                .then()
                .statusCode(200);

        // Check that retrieving the match, is it actually deleted (body text is "null")
        given()
                .pathParam("ebookId", 2)
                .when()
                .get("/ebook/{ebookId}")
                .then()
                .body(Matchers.is("null"));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void updateEbook(){
        Utils.initDatabase();

        final String body = "{\n" +
                "\"id\": " + 2 + ",\n" +
                "\"title\": " + "\"" + "I Promessi Sposi" + "\"" + ",\n" +
                "\"author\": " + "\"" + "Alessandro Manzoni" + "\"" + ",\n" +
                "\"genre\": " + "\"" + "Romanzo Storico" + "\"" + ",\n" +
                "\"monthlyReaders\": " + 12 + "\n" +
                "}";

        given()
                .contentType("application/json")
                .body(body)
                .when()
                .put("/ebook")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(2))
                .body("title", Matchers.equalTo("I Promessi Sposi"))
                .body("author", Matchers.equalTo("Alessandro Manzoni"))
                .body("genre", Matchers.equalTo("Romanzo Storico"))
                .body("monthlyReaders", Matchers.equalTo(12));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void updateEbookAndCheck(){
        Utils.initDatabase();

        final String body = "{\n" +
                "\"id\": " + 2 + ",\n" +
                "\"title\": " + "\"" + "I Promessi Sposi" + "\"" + ",\n" +
                "\"author\": " + "\"" + "Alessandro Manzoni" + "\"" + ",\n" +
                "\"genre\": " + "\"" + "Romanzo Storico" + "\"" + ",\n" +
                "\"monthlyReaders\": " + 12 + "\n" +
                "}";

        given()
                .contentType("application/json")
                .body(body)
                .when()
                .put("/ebook")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(2))
                .body("title", Matchers.equalTo("I Promessi Sposi"))
                .body("author", Matchers.equalTo("Alessandro Manzoni"))
                .body("genre", Matchers.equalTo("Romanzo Storico"))
                .body("monthlyReaders", Matchers.equalTo(12));


        // Check that retrieving the match, is it actually updated
        given()
                .pathParam("ebookId", 2)
                .when()
                .get("/ebook/{ebookId}")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(2))
                .body("title", Matchers.equalTo("I Promessi Sposi"))
                .body("author", Matchers.equalTo("Alessandro Manzoni"))
                .body("genre", Matchers.equalTo("Romanzo Storico"))
                .body("monthlyReaders", Matchers.equalTo(12));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void getAllEbooks(){
        Utils.initDatabase();

        when()
                .get("ebooks")
                .then()
                .statusCode(200)
                .body("ebooks", Matchers.iterableWithSize(10))
                .body("ebooks", Matchers.everyItem(Matchers.aMapWithSize(5)));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void getEbooksByTitle(){
        Utils.initDatabase();

        given()
                .pathParam("title", "1984")
                .when()
                .get("findByTitle/{title}")
                .then()
                .statusCode(200)
                .body("ebooks", Matchers.iterableWithSize(1))
                .body("ebooks", Matchers.everyItem(Matchers.aMapWithSize(5)));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void getEbooksByAuthor(){
        Utils.initDatabase();

        given()
                .pathParam("author", "George Orwell")
                .when()
                .get("/findByAuthor/{author}")
                .then()
                .statusCode(200)
                .body("ebooks", Matchers.iterableWithSize(1))
                .body("ebooks", Matchers.everyItem(Matchers.aMapWithSize(5)));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void getEbooksByGenre(){
        Utils.initDatabase();

        given()
                .pathParam("genre", "Romanzo Gotico")
                .when()
                .get("/findByGenre/{genre}")
                .then()
                .statusCode(200)
                .body("ebooks", Matchers.iterableWithSize(1))
                .body("ebooks", Matchers.everyItem(Matchers.aMapWithSize(5)));
    }
}