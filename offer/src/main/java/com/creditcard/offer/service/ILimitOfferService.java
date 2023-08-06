package com.creditcard.offer.service;

import com.creditcard.offer.dto.OfferDto;
import com.creditcard.offer.entity.Account;
import com.creditcard.offer.entity.LimitOffer;
import com.creditcard.offer.entity.LimitOfferStatus;
import com.creditcard.offer.entity.LimitType;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ILimitOfferService {

    public LimitOffer createLimitOffer(OfferDto limitOffer) throws ParseException;
    public Optional<LimitOffer> findLimitById(long limitId);

    public LimitOffer updateStatus(long limitOfferId, int status);

    public List<LimitOffer> findActiveLimitOffers(long accountId, LocalDate activeDate);

    public List<LimitOffer> findAllOffer();

    public Account updateLimitOfferStatus(long limitOfferId, int status) ;
}
