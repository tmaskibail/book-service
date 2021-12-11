package com.example.bookservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "spring.datasource.url=jdbc:tc:postgresql:13:///")
class BookServiceApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void addBookToCatalog() {
        var bookToCreate = new Book(null, "Lord Of The Rings");

        var responseBook = this.restTemplate
                .postForEntity("http://localhost:" + port + "/books/", bookToCreate, Book.class)
                .getBody();
        assertThat(responseBook.id()).isNotNull();
        assertThat(responseBook.title()).isEqualTo(bookToCreate.title());
    }

}
