package com.bank.customerAccountTracker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.customerAccountTracker.entity.Account;
import com.bank.customerAccountTracker.entity.Customer;
import com.bank.customerAccountTracker.exception.AccountNotFoundException;
import com.bank.customerAccountTracker.exception.TransactionException;
import com.bank.customerAccountTracker.repository.AccountRepository;
import com.bank.customerAccountTracker.repository.CustomerRepository;



@Service
public class AccountServiceImpl implements AccountService{

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public Account addAccount(Account account) {
		Account createAcc = accountRepository.save(account);
		return createAcc;
	}

	@Override
	public List<Account> getAllAccounts() {
		return accountRepository.findAll();
	}

	@Override
	public Account getAccountById(int id) {
		Optional<Account> account = accountRepository.findById(id);
		
		if(account.isPresent())
		{
			return account.get();
		}
		else
			throw new AccountNotFoundException("No Account Found with id: "+id);
	}

	@Override
	public void deleteAllAccounts() {
		List<Account> accounts = accountRepository.findAll();
		if(accounts.isEmpty())
		{
			throw new AccountNotFoundException("No Accounts Found");
		}
		else
		{
			accountRepository.deleteAll();;
		}
		
	}

	@Override
	public void deleteAccount(Account account) {
		accountRepository.delete(account);
		
	}

	@Override
	public Boolean isAccountExists(int id) {
		return accountRepository.existsById(id);
	}

	@Override
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	@Override
	public Account updateAccount(int id, Account account) {
		Account updatedAccount = getAccountById(id);
		updatedAccount.setAccountId(id);
		updatedAccount.setAccountType(account.getAccountType());
		updatedAccount.setBalance(account.getBalance());
		updatedAccount.getCustomer().setCustomerId(account.getCustomer().getCustomerId());
		updatedAccount.getCustomer().setCustomerName(account.getCustomer().getCustomerName());;
		updatedAccount.getCustomer().setCustomerAddress(account.getCustomer().getCustomerAddress());
		updatedAccount.getCustomer().setMobileNumber(account.getCustomer().getMobileNumber());;
		return accountRepository.save(updatedAccount);
	}

	@Override
	public String transferFunds(int from, int to, double amount) {
		if(!isAccountExists(from))
		{
			throw new AccountNotFoundException("Source Account doesn't exists");
		}
		if(!isAccountExists(to))
		{
			throw new AccountNotFoundException("Destination Account doesn't exists");
		}
		if(from == to)
		{
			throw new TransactionException("Source and Destination accounts cannot be same");
		}
		if(amount<0)
		{
			throw new TransactionException("Invalid Amount");
		}
		
		Account fromAccount = getAccountById(from);
		Account toAccount = getAccountById(to);
		
		if(fromAccount.getBalance() < amount)
		{
			throw new TransactionException("Insufficient Funds");
		}
		
		fromAccount.setBalance(fromAccount.getBalance() - amount);
		addAccount(fromAccount);
		toAccount.setBalance(toAccount.getBalance() + amount);
		addAccount(toAccount);
		
		return "Transaction is successful";
	}
	
	
}
