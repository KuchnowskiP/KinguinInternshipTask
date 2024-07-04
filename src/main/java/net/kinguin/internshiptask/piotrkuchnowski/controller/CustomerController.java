package net.kinguin.internshiptask.piotrkuchnowski.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import net.kinguin.internshiptask.piotrkuchnowski.model.Customer;
import net.kinguin.internshiptask.piotrkuchnowski.model.dto.CustomerDTO;
import net.kinguin.internshiptask.piotrkuchnowski.response.ApiErrorResponse;
import net.kinguin.internshiptask.piotrkuchnowski.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(
            summary = "Get all customers",
            description = "Gets all customers",
            responses = {
                @ApiResponse(
                        description = "List of customers",
                        responseCode = "200",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = Customer.class))),
                @ApiResponse(
                        description = "No customers found",
                        responseCode = "204",
                        content = @Content)
            })
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return !customers.isEmpty()
                ? ResponseEntity.ok(customers)
                : ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Get customer by id",
            description = "Gets customer by id",
            responses = {
                @ApiResponse(
                        description = "Customer object",
                        responseCode = "200",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = Customer.class))),
                @ApiResponse(
                        description = "Customer with given id does not exist",
                        responseCode = "400",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String id) {
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @Operation(
            summary = "Create customer",
            description = "Creates customer",
            responses = {
                @ApiResponse(
                        description = "Customer object",
                        responseCode = "200",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = Customer.class))),
                @ApiResponse(
                        description = "Customer with given library card number already exists",
                        responseCode = "400",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class)))
            })
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerDTO customer) {
        Customer customerResponse = customerService.createCustomer(customer);
        return ResponseEntity.ok(customerResponse);
    }

    @Operation(
            summary = "Update customer",
            description = "Updates customer",
            responses = {
                @ApiResponse(
                        description = "Customer object",
                        responseCode = "200",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = Customer.class))),
                @ApiResponse(
                        description = "Customer with given id does not exist",
                        responseCode = "400",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class)))
            })
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable String id, @RequestBody CustomerDTO customer) {
        Customer customerResponse = customerService.updateCustomer(id, customer);
        return ResponseEntity.ok(customerResponse);
    }

    @Operation(
            summary = "Delete customer",
            description = "Deletes customer",
            responses = {
                @ApiResponse(description = "Customer deleted", responseCode = "200"),
                @ApiResponse(
                        description = "Customer with given id does not exist",
                        responseCode = "400",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class)))
            })
    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
    }

    @Operation(
            summary = "Search customers",
            description = "Searches customers based on provided parameters",
            responses = {
                @ApiResponse(
                        description = "List of customers",
                        responseCode = "200",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = Customer.class))),
                @ApiResponse(
                        description = "No customers found, based on provided parameters",
                        responseCode = "204",
                        content = @Content)
            })
    @GetMapping("/search")
    public ResponseEntity<List<Customer>> searchCustomers(
            @RequestParam(required = false, defaultValue = "") String firstName,
            @RequestParam(required = false, defaultValue = "") String lastName,
            @RequestParam(required = false, defaultValue = "") String libraryCardNumber) {
        List<Customer> customers =
                customerService.searchCustomers(firstName, lastName, libraryCardNumber);
        return !customers.isEmpty()
                ? ResponseEntity.ok(customers)
                : ResponseEntity.noContent().build();
    }
}
