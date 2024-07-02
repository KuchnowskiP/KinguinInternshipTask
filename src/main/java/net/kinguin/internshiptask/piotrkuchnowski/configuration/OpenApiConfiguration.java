package net.kinguin.internshiptask.piotrkuchnowski.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;

@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                contact = @Contact(
                        name = "Piotr Kuchnowski",
                        email = "kuchnowski.piotr@outlook.com"
                ),
                title = "Library API for Kinguin Internship Task",
                version = "1.0",
                description = "This is a simple API for managing book loans in a library." +
                        "It allows to add, remove and list books and customers, as well as to lend and return books."
        )
)
public class OpenApiConfiguration {
}
