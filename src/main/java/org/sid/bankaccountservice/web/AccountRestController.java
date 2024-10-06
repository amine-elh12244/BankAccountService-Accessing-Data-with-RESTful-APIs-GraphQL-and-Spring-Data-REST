package org.sid.bankaccountservice.web;

import org.sid.bankaccountservice.dto.BankAccountRequestDTO;
import org.sid.bankaccountservice.dto.BankAccountResponseDTO;
import org.sid.bankaccountservice.entities.BankAccount;
import org.sid.bankaccountservice.mappers.AccountMapper;
import org.sid.bankaccountservice.repositories.BankAccountRepository;
import org.sid.bankaccountservice.services.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class AccountRestController {

    private  BankAccountRepository bankAccountRepository;
    private AccountService accountService;
    private AccountMapper accountMapper;

    public AccountRestController(BankAccountRepository bankAccountRepository , AccountService accountService , AccountMapper accountMapper)  {
        this.bankAccountRepository = bankAccountRepository;
        this.accountService = accountService;
        this.accountMapper = accountMapper;
    }

    // Get all bank accounts
    @GetMapping("/bankAccounts")
    public List<BankAccount> getAllAccounts() {
        return bankAccountRepository.findAll();
    }

    // Get a bank account by ID
    @GetMapping("/bankAccounts/{id}")
    public BankAccount bankAccount(@PathVariable String id) {
        return bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Bank account with ID %s not found", id)));
    }



    // Create a new bank account
    @PostMapping("/bankAccounts")
    public BankAccountResponseDTO createAccount(@RequestBody BankAccountRequestDTO requestDTO ) {

        return accountService.addAccount(requestDTO);
    }

    //update
    @PutMapping("/bankAccounts/{id}")
    public BankAccount update(@PathVariable String id, @RequestBody BankAccount bankAccount) {
        BankAccount account = bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Bank account with ID %s not found", id)));

        if (bankAccount.getBalance() != 0) account.setBalance(bankAccount.getBalance());
        if (bankAccount.getCreationDate() != null) account.setCreationDate(new Date());
        if (bankAccount.getType() != null) account.setType(bankAccount.getType());
        if (bankAccount.getCurrency() != null) account.setCurrency(bankAccount.getCurrency());

        return bankAccountRepository.save(account);
    }


    // Delete a bank account
    @DeleteMapping("/bankAccounts/{id}")
    public void deleteAccount(@PathVariable String id) {
        bankAccountRepository.deleteById(id);
    }



}
