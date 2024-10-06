package org.sid.bankaccountservice.services;

import org.sid.bankaccountservice.dto.BankAccountRequestDTO;
import org.sid.bankaccountservice.dto.BankAccountResponseDTO;
import org.sid.bankaccountservice.entities.BankAccount;
import org.sid.bankaccountservice.mappers.AccountMapper;
import org.sid.bankaccountservice.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;


@Service
@Transactional
public class AccountServiceImpl implements AccountService {


    @Autowired
    private BankAccountRepository bankaccountRepository;

    @Autowired
    private AccountMapper accountMapper;


    @Override
    public BankAccountResponseDTO addAccount(BankAccountRequestDTO bankAccountDTO) {

        BankAccount bankAccount = BankAccount.builder()
                .id(UUID.randomUUID().toString())
                .creationDate(new Date())
                .type(bankAccountDTO.getType())
                .balance(bankAccountDTO.getBalance())
                .currency(bankAccountDTO.getCurrency())
                .build();
        BankAccount saveBankAccount = bankaccountRepository.save(bankAccount);
        // pour le mapper mapping
        BankAccountResponseDTO bankAccountResponseDTO =accountMapper.fromBankAccount(saveBankAccount) ;
        return bankAccountResponseDTO;
    }

    @Override
    public BankAccountResponseDTO updateAccount(String id, BankAccountRequestDTO bankAccountDTO) {
        // Fetch the existing bank account from the repository
        BankAccount existingBankAccount = bankaccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Account %s not found", id)));

        // Update only the fields that are provided in the bankAccountDTO
        if (bankAccountDTO.getBalance() != 0) {
            existingBankAccount.setBalance(bankAccountDTO.getBalance());
        }
        if (bankAccountDTO.getType() != null) {
            existingBankAccount.setType(bankAccountDTO.getType());
        }
        if (bankAccountDTO.getCurrency() != null) {
            existingBankAccount.setCurrency(bankAccountDTO.getCurrency());
        }

        // Save the updated bank account back to the repository
        BankAccount savedBankAccount = bankaccountRepository.save(existingBankAccount);

        // Map the saved bank account to the response DTO
        BankAccountResponseDTO bankAccountResponseDTO = accountMapper.fromBankAccount(savedBankAccount);
        return bankAccountResponseDTO;
    }

}
