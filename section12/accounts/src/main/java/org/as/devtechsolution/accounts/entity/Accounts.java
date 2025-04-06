package org.as.devtechsolution.accounts.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author Aditya Srivastva on 25-01-2024
 */
@Entity
@Table(name = "accounts")
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Accounts extends  BaseEntity {

    @Column(name="customer_id")
    private Long customerId;

    @Column(name="account_number")
    @Id
    private Long accountNumber;

    @Column(name="account_type")
    private String accountType;

    @Column(name="branch_address")
    private String branchAddress;

}