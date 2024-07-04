package net.kinguin.internshiptask.piotrkuchnowski.response;

public record ApiErrorResponse(
        String timestamp, Integer statusCode, String error, String message, String path) {}
