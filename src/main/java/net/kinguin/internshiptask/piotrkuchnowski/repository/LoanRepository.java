package net.kinguin.internshiptask.piotrkuchnowski.repository;

import net.kinguin.internshiptask.piotrkuchnowski.model.Loan;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LoanRepository extends MongoRepository<Loan, String>, CustomLoanRepository {
    List<Loan> findAllByCustomerIdAndBookId(String customerId, String bookId);
    List<Loan> findAllByCustomerId(String customerId);
    List<Loan> findAllByCustomerIdAndReturnDateIsNull(String customerId);
    List<Loan> findAllByCustomerIdAndReturnDateIsNotNull(String customerId);
}
