package org.sid.bankaccountservice.web;


import org.sid.bankaccountservice.dto.BankAccountRequestDTO;
import org.sid.bankaccountservice.dto.BankAccountResponseDTO;
import org.sid.bankaccountservice.entities.BankAccount;
import org.sid.bankaccountservice.entities.Customer;
import org.sid.bankaccountservice.repositories.BankAccountRepository;
import org.sid.bankaccountservice.repositories.CustomerRepository;
import org.sid.bankaccountservice.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BankAccountGraphQLController {

    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CustomerRepository customerRepository;


    @QueryMapping
    public List<BankAccount> accountsList(){
        return bankAccountRepository.findAll() ;

    }

    @QueryMapping
    public BankAccount bankAccountById(@Argument String id) {
        return bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Account %s not found", id)));
    }

    @MutationMapping
    public BankAccountResponseDTO createBankAccount(@Argument BankAccountRequestDTO bankAccount) {
        return accountService.addAccount(bankAccount);

    }

    @MutationMapping
    public BankAccountResponseDTO updateAccount(@Argument String id,@Argument BankAccountRequestDTO bankAccount) {
        return accountService.updateAccount(id,bankAccount);

    }

    @MutationMapping
    public Boolean deleteBankAccount(@Argument String id) {
        // Check if the bank account exists
        if (!bankAccountRepository.existsById(id)) {
            throw new RuntimeException(String.format("Account %s does not exist", id)); // Throw exception if not found
        }
        try {
            bankAccountRepository.deleteById(id);
            return true; // Return true if deletion is successful
        } catch (Exception e) {
            // Log the exception for debugging purposes (optional)
            System.err.println("Error deleting account: " + e.getMessage());
            return false; // Return false if there is an error
        }
    }

    // Customer customersList

    @QueryMapping
    public List<Customer> customersList(){
        return customerRepository.findAll() ;

    }




}

//record BankAccountDTO(Double balance , String type , String currency){
//
//
//}