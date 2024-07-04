package net.kinguin.internshiptask.piotrkuchnowski.repository.implementation;

import net.kinguin.internshiptask.piotrkuchnowski.model.Book;
import net.kinguin.internshiptask.piotrkuchnowski.model.Customer;
import net.kinguin.internshiptask.piotrkuchnowski.model.Loan;
import net.kinguin.internshiptask.piotrkuchnowski.model.dto.LoanDTO;
import net.kinguin.internshiptask.piotrkuchnowski.repository.CustomLoanRepository;
import net.kinguin.internshiptask.piotrkuchnowski.service.BookService;
import net.kinguin.internshiptask.piotrkuchnowski.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LoanRepositoryImpl implements CustomLoanRepository {
    private final MongoTemplate mongoTemplate;
    private final CustomerService customerService;
    private final BookService bookService;

    @Autowired
    public LoanRepositoryImpl(
            MongoTemplate mongoTemplate, CustomerService customerService, BookService bookService) {
        this.mongoTemplate = mongoTemplate;
        this.customerService = customerService;
        this.bookService = bookService;
    }

    @Override
    public Loan updateLoanByReturnDate(String id, String returnDate) {
        Loan loan = mongoTemplate.findById(id, Loan.class);
        if (loan == null) {
            throw new IllegalArgumentException("Loan with id " + id + " does not exist");
        }
        return mongoTemplate.save(
                new Loan(loan.id(), loan.customerId(), loan.bookId(), loan.loanDate(), returnDate));
    }
}
