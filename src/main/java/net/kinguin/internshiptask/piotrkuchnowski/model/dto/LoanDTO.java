package net.kinguin.internshiptask.piotrkuchnowski.model.dto;

import net.kinguin.internshiptask.piotrkuchnowski.model.Book;
import net.kinguin.internshiptask.piotrkuchnowski.model.Customer;
import net.kinguin.internshiptask.piotrkuchnowski.model.Loan;

public record LoanDTO(
        String id,
        Customer customer,
        Book book,
        String loanDate,
        String returnDate) {

    public LoanDTO(Loan loan, Customer customer, Book book) {
        this(loan.id(), customer, book, loan.loanDate(), loan.returnDate());
    }
}
