package net.kinguin.internshiptask.piotrkuchnowski.repository;

import net.kinguin.internshiptask.piotrkuchnowski.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    Customer findByLibraryCardNumber(String libraryCardNumber);

    List<Customer> findByFirstNameContainingIgnoreCase(String firstName);

    List<Customer> findByLastNameContainingIgnoreCase(String lastName);
}
