package net.kinguin.internshiptask.piotrkuchnowski.model.dto;

public record BookDTO(
        String title,
        String author,
        String publisher,
        String genre,
        String isbn,
        Integer totalCopies,
        Integer availableCopies) {}
