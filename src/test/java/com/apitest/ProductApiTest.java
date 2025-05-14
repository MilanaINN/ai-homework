package com.apitest;

import com.apitest.model.Product;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductApiTest {
    private static final String API_URL = "https://fakestoreapi.com/products";

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = API_URL;
    }

    @Test
    public void testApiResponse() {
        Response response = given()
            .when()
            .get()
            .then()
            .extract()
            .response();

        assertEquals(200, response.getStatusCode(), "Expected status code 200");
    }

    @Test
    public void testProductAttributes() {
        Response response = given()
            .when()
            .get()
            .then()
            .extract()
            .response();

        Product[] products = response.as(Product[].class);
        List<DefectiveProduct> defectiveProducts = new ArrayList<>();

        for (Product product : products) {
            List<String> defects = new ArrayList<>();

            // Check title
            if (product.getTitle() == null || product.getTitle().trim().isEmpty()) {
                defects.add("Empty or missing title");
            }

            // Check price
            if (product.getPrice() == null || product.getPrice() < 0) {
                defects.add("Missing or negative price");
            }

            // Check rating
            if (product.getRating() == null || 
                product.getRating().getRate() == null || 
                product.getRating().getRate() > 5) {
                defects.add("Invalid or missing rating");
            }

            if (!defects.isEmpty()) {
                defectiveProducts.add(new DefectiveProduct(
                    product.getId(),
                    product.getTitle(),
                    defects
                ));
            }
        }

        // Print defective products report
        if (!defectiveProducts.isEmpty()) {
            System.out.println("\nDefective Products Report:");
            for (DefectiveProduct product : defectiveProducts) {
                System.out.println("\nProduct ID: " + product.getId());
                System.out.println("Title: " + product.getTitle());
                System.out.println("Defects found: " + String.join(", ", product.getDefects()));
            }
        }

        assertTrue(defectiveProducts.isEmpty(), 
            String.format("Found %d products with defects", defectiveProducts.size()));
    }

    private static class DefectiveProduct {
        private final Long id;
        private final String title;
        private final List<String> defects;

        public DefectiveProduct(Long id, String title, List<String> defects) {
            this.id = id;
            this.title = title;
            this.defects = defects;
        }

        public Long getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public List<String> getDefects() {
            return defects;
        }
    }
} 