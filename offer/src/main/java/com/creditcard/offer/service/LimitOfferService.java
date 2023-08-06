package com.creditcard.offer.service;

import com.creditcard.offer.dto.OfferDto;
import com.creditcard.offer.entity.Account;
import com.creditcard.offer.entity.LimitOffer;
import com.creditcard.offer.entity.LimitOfferStatus;
import com.creditcard.offer.entity.LimitType;
import com.creditcard.offer.repository.AccountRepository;
import com.creditcard.offer.repository.LimitOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LimitOfferService implements ILimitOfferService {

    @Autowired
    LimitOfferRepository limitOfferRepository;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public LimitOffer createLimitOffer(OfferDto limitOffer) throws ParseException {
        Optional<Account> account = accountRepository.findById(limitOffer.getAccountId());

        try {

            if(account == null){
                throw new IllegalArgumentException("Account not found");
            }

            if ( limitOffer.getLimitType() == LimitType.ACCOUNT_LIMIT.ordinal() && limitOffer.getNewLimit() > account.get().getAccountLimit()) {
                LimitOffer newlimitOffer = new LimitOffer();
                newlimitOffer.setAccountId(limitOffer.getAccountId());
                newlimitOffer.setLimitType(LimitType.values()[limitOffer.getLimitType()]);
                newlimitOffer.setNewLimit(limitOffer.getNewLimit());

                newlimitOffer.setOfferActivationTime(limitOffer.getOfferActivationTime());
                newlimitOffer.setOfferExpiryTime(limitOffer.getOfferExpiryTime());
                newlimitOffer.setStatus(LimitOfferStatus.PENDING);
                return limitOfferRepository.save(newlimitOffer);
            } else if (limitOffer.getLimitType() == LimitType.PER_TRANSACTION_LIMIT.ordinal() && limitOffer.getNewLimit() > account.get().getPerTransactionLimit()) {
                LimitOffer newlimitOffer = new LimitOffer();
                newlimitOffer.setAccountId(limitOffer.getAccountId());
                newlimitOffer.setLimitType(LimitType.values()[limitOffer.getLimitType()]);
                newlimitOffer.setNewLimit(limitOffer.getNewLimit());
                newlimitOffer.setOfferActivationTime(limitOffer.getOfferActivationTime());
                newlimitOffer.setOfferExpiryTime(limitOffer.getOfferExpiryTime());
                newlimitOffer.setStatus(LimitOfferStatus.PENDING);
                return limitOfferRepository.save(newlimitOffer);
            } else {
                throw new IllegalArgumentException("New limit must be greater than the current limit");
            }
        }catch (Exception ex){
            throw new IllegalArgumentException(ex+"error occur is input");
        }
    }

    @Override
    public Optional<LimitOffer> findLimitById(long limitId) {
        try {
            Optional<LimitOffer> offer = limitOfferRepository.findById(limitId);
            if(offer == null){
                throw new IllegalArgumentException("offer not found");
            }
            return offer;
        }catch (Exception exception){
            throw new IllegalArgumentException(exception+"offer not found");
        }
    }

    @Override
    public LimitOffer updateStatus(long limitOfferId, int status) {
        Optional<LimitOffer> limitOffer = limitOfferRepository.findById(limitOfferId);

        if (limitOffer != null) {
            limitOffer.get().setStatus(LimitOfferStatus.values()[status]);
            return limitOfferRepository.save(limitOffer.get());
        }else{
            throw new IllegalArgumentException("offer not found");
        }
    }

    @Override
    public List<LimitOffer> findActiveLimitOffers(long accountId, LocalDate activeDate) {
        List<LimitOffer> activeOffers = new ArrayList<>();
        for (LimitOffer limitOffer : limitOfferRepository.findAll()) {
            if (limitOffer.getAccountId() == accountId &&
                    limitOffer.getStatus() == LimitOfferStatus.PENDING &&
                    limitOffer.getOfferActivationTime().isBefore(activeDate) &&
                    limitOffer.getOfferExpiryTime().isAfter(activeDate)) {
                activeOffers.add(limitOffer);
            }
        }
        return activeOffers;
    }

    @Override
    public List<LimitOffer> findAllOffer() {
        return limitOfferRepository.findAll();
    }

    @Override
    public Account updateLimitOfferStatus(long limitOfferId, int status) {
        try{
            updateStatus(limitOfferId, status);
            if (LimitOfferStatus.values()[status] == LimitOfferStatus.ACCEPTED) {
                Optional<LimitOffer> limitOffer = limitOfferRepository.findById(limitOfferId);
                Optional<Account> account = accountRepository.findById(limitOffer.get().getAccountId());

                if(limitOffer == null||account == null){
                    new IllegalArgumentException("offer not found");
                }

                if (limitOffer.get().getLimitType() == LimitType.ACCOUNT_LIMIT) {
                    account.get().setLastAccountLimit(account.get().getAccountLimit());
                    account.get().setAccountLimit(limitOffer.get().getNewLimit());
                    account.get().setAccountLimitUpdateTime(LocalDate.now());
                } else if (limitOffer.get().getLimitType() == LimitType.PER_TRANSACTION_LIMIT) {
                    account.get().setLastPerTransactionLimit(account.get().getPerTransactionLimit());
                    account.get().setPerTransactionLimit(limitOffer.get().getNewLimit());
                    account.get().setPerTransactionLimitUpdateTime(LocalDate.now());
                }

                return accountRepository.save(account.get());
            }
        }catch (Exception ex){
            throw new IllegalArgumentException(ex+"error occur is input");

        }
        return null;
    }
}
