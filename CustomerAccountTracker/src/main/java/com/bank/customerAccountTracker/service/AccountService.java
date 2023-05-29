package com.bank.customerAccountTracker.service;

import java.util.List;

import com.bank.customerAccountTracker.entity.Account;
import com.bank.customerAccountTracker.entity.Customer;

public interface AccountService {
	
	public Account addAccount(Account account);
	public List<Account> getAllAccounts();
	public Account getAccountById(int id);
	public void deleteAllAccounts();
	public void deleteAccount(Account account);
	public Boolean isAccountExists(int id);
	public List<Customer> getAllCustomers();
	public Account updateAccount(int id, Account account);
	public String transferFunds(int from, int to, double amount);
}
