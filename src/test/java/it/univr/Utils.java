package it.univr;

import static io.restassured.RestAssured.given;

public class Utils {
    public static String getEbookJson(String title, String author, String genre, int monthlyReaders) {
        return "{"
                + "\"title\": \"" + title + "\","
                + "\"author\": \"" + author + "\","
                + "\"genre\": \"" + genre + "\","
                + "\"monthlyReaders\": " + monthlyReaders
                + "}";
    }

    public static void initDatabase() {
        given().contentType("application/json").body(getEbookJson("La Divina Commedia", "Dante Alighieri", "Poesia", 1500)).when().post("/ebook").then().statusCode(200);
        given().contentType("application/json").body(getEbookJson("I Promessi Sposi", "Alessandro Manzoni", "Romanzo Storico", 1200)).when().post("/ebook").then().statusCode(200);
        given().contentType("application/json").body(getEbookJson("Moby Dick", "Herman Melville", "Avventura", 800)).when().post("/ebook").then().statusCode(200);
        given().contentType("application/json").body(getEbookJson("1984", "George Orwell", "Distopico", 1000)).when().post("/ebook").then().statusCode(200);
        given().contentType("application/json").body(getEbookJson("Le Avventure di Pinocchio", "Carlo Collodi", "Fiaba", 950)).when().post("/ebook").then().statusCode(200);
        given().contentType("application/json").body(getEbookJson("Orgoglio e Pregiudizio", "Jane Austen", "Romanzo Rosa", 850)).when().post("/ebook").then().statusCode(200);
        given().contentType("application/json").body(getEbookJson("Il Gattopardo", "Giuseppe Tomasi di Lampedusa", "Romanzo Storico", 700)).when().post("/ebook").then().statusCode(200);
        given().contentType("application/json").body(getEbookJson("Cuore", "Edmondo De Amicis", "Educativo", 650)).when().post("/ebook").then().statusCode(200);
        given().contentType("application/json").body(getEbookJson("Il Principe", "Niccolò Machiavelli", "Filosofia", 600)).when().post("/ebook").then().statusCode(200);
        given().contentType("application/json").body(getEbookJson("Cime Tempestose", "Emily Brontë", "Romanzo Gotico", 750)).when().post("/ebook").then().statusCode(200);

    }
}
