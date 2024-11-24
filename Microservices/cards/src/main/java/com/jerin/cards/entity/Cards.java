package com.jerin.cards.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Cards extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long cardId;
    @Column
    private String mobileNumber;
    @Column
    private String cardNumber;
    @Column
    private String cardType;
    @Column
    private int totalLimit;
    @Column
    private int amountUsed;
    @Column
    private int availableAmount;


}
