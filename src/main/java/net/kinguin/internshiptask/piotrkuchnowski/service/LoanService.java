package net.kinguin.internshiptask.piotrkuchnowski.service;

import net.kinguin.internshiptask.piotrkuchnowski.model.Book;
import net.kinguin.internshiptask.piotrkuchnowski.model.Customer;
import net.kinguin.internshiptask.piotrkuchnowski.model.Loan;
import net.kinguin.internshiptask.piotrkuchnowski.model.dto.LoanDTO;
import net.kinguin.internshiptask.piotrkuchnowski.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoanService {
    private final BookService bookService;
    private final CustomerService customerService;
    private final LoanRepository loanRepository;

    @Autowired
    public LoanService(
            BookService bookService,
            CustomerService customerService,
            LoanRepository loanRepository) {
        this.bookService = bookService;
        this.customerService = customerService;
        this.loanRepository = loanRepository;
    }

    private List<LoanDTO> loansToLoanDTOs(List<Loan> loans) {
        List<LoanDTO> loansDTO = new ArrayList<>();
        for (Loan loan : loans) {
            Customer customer = customerService.getCustomerById(loan.customerId());
            Book book = bookService.getBookById(loan.bookId());
            loansDTO.add(new LoanDTO(loan, customer, book));
        }
        return loansDTO;
    }

    public List<LoanDTO> findAllWithCustomerAndBookObjects() {
        List<Loan> loans = loanRepository.findAll();
        return loansToLoanDTOs(loans);
    }

    private boolean allLoansReturned(List<Loan> loans) {
        if (!loans.isEmpty()) {
            System.out.println("Loans: " + loans);
            System.out.println("All loans returned: " + loans.stream().allMatch(Loan::isReturned));
            return loans.stream().allMatch(Loan::isReturned);
        }
        return true;
    }

    private Loan loanBook(String customerId, String bookId) {
        bookService.decrementAvailableCopies(bookId);
        return loanRepository.save(new Loan(null, customerId, bookId, LocalDate.now().toString(), null));
    }

    private Loan returnBook(Loan loan) {
        bookService.incrementAvailableCopies(loan.bookId());
        return loanRepository.updateLoanByReturnDate(loan.id(), LocalDate.now().toString());
    }

    public LoanDTO loanOrReturnBook(String customerId, String bookId) {
        if (!customerService.existsById(customerId)) {
            throw new IllegalArgumentException(
                    "Customer with id " + customerId + " does not exist");
        }
        if (!bookService.existsById(bookId)) {
            throw new IllegalArgumentException("Book with id " + bookId + " does not exist");
        }

        List<Loan> loans = loanRepository.findAllByCustomerIdAndBookId(customerId, bookId);
        Loan loan;
        if (allLoansReturned(loans)) {
            loan =  loanBook(customerId, bookId);
        } else {
            loan = loans.stream().filter(l -> !l.isReturned()).findFirst().get();
            loan = returnBook(loan);
        }
        Customer customer = customerService.getCustomerById(customerId);
        Book book = bookService.getBookById(bookId);
        return new LoanDTO(loan, customer, book);
    }

    public List<LoanDTO> findAllByCustomerId(String customerId) {
        if(!customerService.existsById(customerId)) {
            throw new IllegalArgumentException(
                    "Customer with id " + customerId + " does not exist");
        }
        List<Loan> loans =  loanRepository.findAllByCustomerId(customerId);
        return loansToLoanDTOs(loans);
    }

    public List<LoanDTO> findAllNotReturnedByCustomerId(String customerId) {
        if (!customerService.existsById(customerId)) {
            throw new IllegalArgumentException(
                    "Customer with id " + customerId + " does not exist");
        }
        List<Loan> loans = loanRepository.findAllByCustomerIdAndReturnDateIsNull(customerId);
        return loansToLoanDTOs(loans);
    }

    public List<LoanDTO> findAllReturnedByCustomerId(String customerId) {
        if (!customerService.existsById(customerId)) {
            throw new IllegalArgumentException(
                    "Customer with id " + customerId + " does not exist");
        }
        List<Loan> loans = loanRepository.findAllByCustomerIdAndReturnDateIsNotNull(customerId);
        return loansToLoanDTOs(loans);
    }
}
