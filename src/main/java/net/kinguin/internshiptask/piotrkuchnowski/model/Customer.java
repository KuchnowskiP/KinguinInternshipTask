package net.kinguin.internshiptask.piotrkuchnowski.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("customers")
public record Customer(
        @Id
        String id,
        String firstName,
        String lastName,
        String libraryCardNumber
) {}
