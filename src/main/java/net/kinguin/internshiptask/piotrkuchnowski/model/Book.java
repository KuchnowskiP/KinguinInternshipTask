package net.kinguin.internshiptask.piotrkuchnowski.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("books")
public record Book (
        @Id
        String id,
        String title,
        String author,
        String publisher,
        String genre,
        String isbn,
        Integer totalCopies,
        Integer availableCopies
) {}
