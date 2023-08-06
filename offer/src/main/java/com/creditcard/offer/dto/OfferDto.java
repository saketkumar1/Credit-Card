package com.creditcard.offer.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class OfferDto {

    public long accountId;
    public int limitType;
    public double newLimit;
    public LocalDate offerActivationTime;
    public LocalDate offerExpiryTime;
}
