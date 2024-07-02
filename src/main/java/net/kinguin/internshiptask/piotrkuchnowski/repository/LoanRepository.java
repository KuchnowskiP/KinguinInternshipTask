package net.kinguin.internshiptask.piotrkuchnowski.repository;

import net.kinguin.internshiptask.piotrkuchnowski.model.Loan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface LoanRepository extends MongoRepository<Loan, String> {

}
