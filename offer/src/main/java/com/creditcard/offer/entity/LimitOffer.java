package com.creditcard.offer.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "offer")
@Data
public class LimitOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long limitOfferId;
    private long accountId;
    private LimitType limitType;
    private double newLimit;
    private LocalDate offerActivationTime;
    private LocalDate offerExpiryTime;
    private LimitOfferStatus status;
}
