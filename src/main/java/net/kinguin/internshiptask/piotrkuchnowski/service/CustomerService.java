package net.kinguin.internshiptask.piotrkuchnowski.service;

import net.kinguin.internshiptask.piotrkuchnowski.model.Customer;
import net.kinguin.internshiptask.piotrkuchnowski.model.Loan;
import net.kinguin.internshiptask.piotrkuchnowski.model.dto.CustomerDTO;
import net.kinguin.internshiptask.piotrkuchnowski.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(String id) {
        return customerRepository
                .findById(id)
                .orElseThrow(
                        () ->
                                new IllegalArgumentException(
                                        "Customer with id " + id + " does not exist"));
    }

    public Customer createCustomer(CustomerDTO customer) {
        if(customerRepository.findByLibraryCardNumber(customer.libraryCardNumber()) != null) {
            throw new IllegalArgumentException("Customer with library card number " + customer.libraryCardNumber() + " already exists");
        }
        return customerRepository.save(
                new Customer(
                        null,
                        customer.firstName(),
                        customer.lastName(),
                        customer.libraryCardNumber()));
    }

    public void deleteCustomer(String id) {
        if (!customerRepository.existsById(id)) {
            throw new IllegalArgumentException("Customer with id " + id + " does not exist");
        }
        customerRepository.deleteById(id);
    }

    public Customer updateCustomer(String id, CustomerDTO customer) {
        if (!customerRepository.existsById(id)) {
            throw new IllegalArgumentException("Customer with id " + id + " does not exist");
        }

        return customerRepository.save(
                new Customer(
                        id,
                        customer.firstName(),
                        customer.lastName(),
                        customer.libraryCardNumber()));
    }

    public List<Customer> searchCustomers(
            String firstName, String lastName, String libraryCardNumber) {
        if (firstName.isEmpty() && lastName.isEmpty() && libraryCardNumber.isEmpty()) {
            return customerRepository.findAll();
        }

        if (!libraryCardNumber.isEmpty()) {
            Customer customer = customerRepository.findByLibraryCardNumber(libraryCardNumber);
            if (customer == null) {
                return new ArrayList<>();
            }
            return List.of(customer);
        }

        List<Customer> customers = new ArrayList<>();
        if (!firstName.isEmpty()) {
            customers.addAll(customerRepository.findByFirstNameContainingIgnoreCase(firstName));
        }
        if (!lastName.isEmpty()) {
            customers.addAll(customerRepository.findByLastNameContainingIgnoreCase(lastName));
        }
        return customers;
    }

    public boolean existsById(String id) {
        return customerRepository.existsById(id);
    }
}
