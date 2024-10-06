package org.sid.bankaccountservice.entities;


import org.sid.bankaccountservice.enums.AccountType;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "p1", types = { BankAccount.class })
public interface AccountProjection {

    public String getId();  // Assuming the ID is of type Long, adjust accordingly

    public AccountType getType();  // Assuming 'type' is a String, adjust accordingly
}
