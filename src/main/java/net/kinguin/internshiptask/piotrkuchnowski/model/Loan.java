package net.kinguin.internshiptask.piotrkuchnowski.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("loans")
public record Loan(
        @Id String id,
        String bookId,
        String customerId,
        String loanDate,
        String returnDate
) {}
