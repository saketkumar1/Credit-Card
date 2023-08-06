package com.creditcard.offer.repository;

import com.creditcard.offer.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {
}
