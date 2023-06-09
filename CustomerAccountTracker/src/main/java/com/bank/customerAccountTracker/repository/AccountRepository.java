package com.bank.customerAccountTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.customerAccountTracker.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{

}
