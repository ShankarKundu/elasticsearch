package com.example.elasticsearch.domain;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.UUID;


@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "book-index4", type = "book3", shards = 1, replicas = 0)
public class Book {
    @Id
    private String bookId;

    private String title;

    private String author;

    private String price;
}
