package net.kinguin.internshiptask.piotrkuchnowski.configuration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.kinguin.internshiptask.piotrkuchnowski.model.Book;
import net.kinguin.internshiptask.piotrkuchnowski.model.Customer;
import net.kinguin.internshiptask.piotrkuchnowski.model.Loan;
import net.kinguin.internshiptask.piotrkuchnowski.repository.BookRepository;
import net.kinguin.internshiptask.piotrkuchnowski.repository.CustomerRepository;
import net.kinguin.internshiptask.piotrkuchnowski.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;
    private final LoanRepository loanRepository;

    @Autowired
    public DataLoader(
            BookRepository bookRepository,
            CustomerRepository customerRepository,
            LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.customerRepository = customerRepository;
        this.loanRepository = loanRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadBooks();
        loadCustomers();
        loadLoans();
    }

    private void loadBooks() throws IOException {
        File file = ResourceUtils.getFile("classpath:books.json");
        ObjectMapper objectMapper = new ObjectMapper();
        List<Book> books = objectMapper.readValue(file, new TypeReference<>() {});
        bookRepository.saveAll(books);
    }

    private void loadCustomers() throws IOException {
        File file = ResourceUtils.getFile("classpath:customers.json");
        ObjectMapper objectMapper = new ObjectMapper();
        List<Customer> customers = objectMapper.readValue(file, new TypeReference<>() {});
        customerRepository.saveAll(customers);
    }

    private void loadLoans() throws IOException {
        File file = ResourceUtils.getFile("classpath:loans.json");
        ObjectMapper objectMapper = new ObjectMapper();
        List<Loan> loans = objectMapper.readValue(file, new TypeReference<>() {});
        loanRepository.saveAll(loans);
    }
}
