package com.expense.service.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Expense {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="external_id")
    private String externalId;

    @Column(name = "user_id")
    private String userId;

    @Column(name="amount")
    private String amount;

    @Column(name="currency")
    private String currency;

    @Column(name="merchant")
    private String merchant;

    @Column(name="create_at")
    private Timestamp createdAt;

    @PrePersist
    @PreUpdate
    private void generateExternalId(){
        if(this.externalId==null){
            this.externalId = UUID.randomUUID().toString();
        }
    }
}

