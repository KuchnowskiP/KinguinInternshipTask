package net.kinguin.internshiptask.piotrkuchnowski.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import net.kinguin.internshiptask.piotrkuchnowski.model.dto.LoanDTO;
import net.kinguin.internshiptask.piotrkuchnowski.response.ApiErrorResponse;
import net.kinguin.internshiptask.piotrkuchnowski.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {
    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @Operation(
            summary = "Get all loans",
            description = "Retrieves all loans",
            responses = {
                    @ApiResponse(
                            description = "List of loans with customer and book objects",
                            responseCode = "200",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = LoanDTO.class))),
                    @ApiResponse(
                            description = "No loans found",
                            responseCode = "204",
                            content = @Content)
            })
    @GetMapping
    public ResponseEntity<List<LoanDTO>> getAllLoans() {
        List<LoanDTO> loans = loanService.findAllWithCustomerAndBookObjects();
        return !loans.isEmpty() ? ResponseEntity.ok(loans) : ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Loan or return book",
            description =
                    "This endpoint loans or returns a book for a customer with the given ID and book with the given ID. " +
                            "If the book was previously lent to the specified customer, it checks if the book was returned. " +
                            "If not, it returns it. Otherwise, it loans it.",
            responses = {
                    @ApiResponse(
                            description = "Loan object representing the newly made loan or return",
                            responseCode = "200",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = LoanDTO.class))),
                    @ApiResponse(
                            description = "Customer or book with the given ID does not exist",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            ))
            })
    @PostMapping
    public ResponseEntity<LoanDTO> loanOrReturnBook(String customerId, String bookId) {
        LoanDTO loan = loanService.loanOrReturnBook(customerId, bookId);
        return ResponseEntity.ok(loan);
    }

    @Operation(
            summary = "Get all loans made by a customer",
            description = "Retrieves all loans made by a customer",
            responses = {
                    @ApiResponse(
                            description = "List of loans made by the customer",
                            responseCode = "200",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = LoanDTO.class))),
                    @ApiResponse(
                            description = "No loans made by the customer found",
                            responseCode = "204",
                            content = @Content),
                    @ApiResponse(
                            description = "Customer with the given ID does not exist",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            ))
            })
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<LoanDTO>> allLoansMadeByCustomer(@PathVariable String customerId) {
        List<LoanDTO> loans = loanService.findAllByCustomerId(customerId);
        return !loans.isEmpty() ? ResponseEntity.ok(loans) : ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Get all loans not returned by a customer",
            description = "Retrieves all loans not returned by a customer",
            responses = {
                    @ApiResponse(
                            description = "List of loans not returned by the customer",
                            responseCode = "200",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = LoanDTO.class))),
                    @ApiResponse(
                            description = "No loans not returned by the customer found",
                            responseCode = "204",
                            content = @Content),
                    @ApiResponse(
                            description = "Customer with the given ID does not exist",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            ))
            })
    @GetMapping("/customer/{customerId}/active")
    public ResponseEntity<List<LoanDTO>> allLoansNotReturned(@PathVariable String customerId) {
        List<LoanDTO> loans = loanService.findAllNotReturnedByCustomerId(customerId);
        return !loans.isEmpty() ? ResponseEntity.ok(loans) : ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Get all loans returned by a customer",
            description = "Retrieves all loans returned by a customer",
            responses = {
                    @ApiResponse(
                            description = "List of loans returned by the customer",
                            responseCode = "200",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = LoanDTO.class))),
                    @ApiResponse(
                            description = "No loans returned by the customer found",
                            responseCode = "204",
                            content = @Content),
                    @ApiResponse(
                            description = "Customer with the given ID does not exist",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            ))
            })
    @GetMapping("/customer/{customerId}/returned")
    public ResponseEntity<List<LoanDTO>> allLoansReturned(@PathVariable String customerId) {
        List<LoanDTO> loans = loanService.findAllReturnedByCustomerId(customerId);
        return !loans.isEmpty() ? ResponseEntity.ok(loans) : ResponseEntity.noContent().build();
    }
}
