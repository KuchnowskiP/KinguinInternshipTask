package net.kinguin.internshiptask.piotrkuchnowski.repository;

import net.kinguin.internshiptask.piotrkuchnowski.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String> {

}
