package net.kinguin.internshiptask.piotrkuchnowski.repository;

import net.kinguin.internshiptask.piotrkuchnowski.model.Loan;

public interface CustomLoanRepository {
    Loan updateLoanByReturnDate(String id, String returnDate);
}
