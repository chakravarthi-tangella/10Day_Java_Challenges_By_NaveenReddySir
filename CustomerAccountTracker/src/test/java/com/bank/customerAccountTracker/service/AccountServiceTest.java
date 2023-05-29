package com.bank.customerAccountTracker.service;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.bank.customerAccountTracker.entity.Account;
import com.bank.customerAccountTracker.entity.Customer;
import com.bank.customerAccountTracker.exception.AccountNotFoundException;
import com.bank.customerAccountTracker.exception.TransactionException;
import com.bank.customerAccountTracker.repository.AccountRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class AccountServiceTest {

	@InjectMocks
	AccountServiceImpl accountService;
	
	@Mock
	AccountRepository accountRepository;
	
	private List<Account> accountList;
	
	@BeforeEach
	void setUp() throws Exception
	{
		accountList = new ArrayList<>();
		accountList.add(new Account(1, "savings", 1000.0, new Customer(1, "Dhoni", "Ranchi", "97463413545")));
		accountList.add(new Account(2, "current", 9000.0, new Customer(1, "Virat", "Delhi", "4574678678")));
		accountList.add(new Account(3, "savings", 12000.0, new Customer(1, "Sachin", "Mumbai", "64756786756")));
	}
	
	@Test
	void testAddAccount() {
		Account expectedAccount = accountList.get(1);
		
		when(accountRepository.save(any())).thenReturn(expectedAccount);
		Account actualAccount = accountService.addAccount(expectedAccount);
		assertEquals(expectedAccount, actualAccount);
		verify(accountRepository).save(any());
	}

	@Test
	void testGetAllAccounts() {
		when(accountRepository.findAll()).thenReturn(accountList);
		List<Account> actual = accountService.getAllAccounts();
		assertEquals(actual, accountList);
		verify(accountRepository).findAll();
	}

	@Test
	void testGetAccountById() {
		Optional<Account> account = Optional.of(accountList.get(1));
		when(accountRepository.findById(1)).thenReturn(account);
		Optional<Account> actual = Optional.of(accountService.getAccountById(1));
		assertEquals(account, actual);
	}

	@Test
	void testDeleteAllAccounts() {
		try
		{
			accountService.deleteAllAccounts();
		}
		catch(AccountNotFoundException ex)
		{
			assertEquals(new AccountNotFoundException("No Accounts Found").getMessage(), ex.getMessage());
		}
	}

	@Test
	void testDeleteAccount() {
		Account account = accountList.get(1);
		accountService.deleteAccount(account);
		verify(accountRepository,times(1)).delete(account);
	}

	@Test
	void testTransferFunds() {
		when(accountService.isAccountExists(3)).thenReturn(true);
		when(accountService.isAccountExists(2)).thenReturn(true);
		
		when(accountRepository.findById(2)).thenReturn(accountList.stream().filter(account -> account.getAccountId() == 2).findFirst());
		when(accountRepository.findById(3)).thenReturn(accountList.stream().filter(account -> account.getAccountId() == 3).findFirst());
		
		assertEquals("Transaction is successful", accountService.transferFunds(3, 2, 1000));
		assertEquals(11000, accountList.get(2).getBalance());
		assertEquals(10000, accountList.get(1).getBalance());
		
		verify(accountRepository).findById(2);
		verify(accountRepository).findById(3);
		verify(accountRepository).save(accountList.get(2));
		verify(accountRepository).save(accountList.get(1));
	}
	
	@Test
	void testTransferFundsSourceAccountNotFound() {
		try
		{
			when(accountService.isAccountExists(5)).thenReturn(false);
			accountService.transferFunds(5, 2, 1000);
		}
		catch(AccountNotFoundException ex)
		{
			assertEquals(new AccountNotFoundException("Source Account doesn't exists").getMessage(), ex.getMessage());
			verify(accountRepository,never()).findById(5);
		}
	}
	
	@Test
	void testTransferFundsDestinationAccountNotFound() {
		try
		{
			when(accountService.isAccountExists(1)).thenReturn(true);
			accountService.transferFunds(1, 2, 1000);
		}
		catch(AccountNotFoundException ex)
		{
			assertEquals(new AccountNotFoundException("Destination Account doesn't exists").getMessage(), ex.getMessage());
		}
	}
	
	@Test
	void testTransferFundsInvalidAmount() {
		when(accountService.isAccountExists(3)).thenReturn(true);
		when(accountService.isAccountExists(2)).thenReturn(true);
		try
		{
			accountService.transferFunds(3, 2, -1000);
		}
		catch(TransactionException ex)
		{
			assertEquals(new TransactionException("Invalid Amount").getMessage(), ex.getMessage());
		}
	}
	
	@Test
	void testTransferFundsInsufficientFunds() {
		when(accountService.isAccountExists(3)).thenReturn(true);
		when(accountService.isAccountExists(2)).thenReturn(true);
		when(accountRepository.findById(2)).thenReturn(accountList.stream().filter(account -> account.getAccountId() == 2).findFirst());
		when(accountRepository.findById(3)).thenReturn(accountList.stream().filter(account -> account.getAccountId() == 3).findFirst());
		
		try
		{
			accountService.transferFunds(3, 2, 990000);
		}
		catch(TransactionException ex)
		{
			assertEquals(new TransactionException("Insufficient Funds").getMessage(), ex.getMessage());
		}
	}

	@Test
	void testUpdateAccount() {
		Account newAccount = new Account(2, "savings", 7400.0, new Customer(2, "virat kohli", "delhi", "98684652"));
		
		when(accountRepository.findById(2)).thenReturn(accountList.stream().filter(account -> account.getAccountId()==2).findFirst());
		when(accountRepository.save(accountList.get(1))).thenReturn(accountList.get(1));
		
		assertEquals("savings", accountService.updateAccount(2, newAccount).getAccountType());
		assertEquals(7400, accountService.updateAccount(2, newAccount).getBalance());
		assertEquals("virat kohli", accountService.updateAccount(2, newAccount).getCustomer().getCustomerName());
		assertEquals("98684652", accountService.updateAccount(2, newAccount).getCustomer().getMobileNumber());
		
		verify(accountRepository,times(4)).findById(2);
		verify(accountRepository,times(4)).save(accountList.get(1));	
	}
	
	@Test
	void testUpdateInvalidAccount() {
		Account newAccount = new Account(5, "savings", 7400.0, new Customer(2, "virat kohli", "delhi", "98684652"));
		
		when(accountRepository.findById(5)).thenReturn(Optional.empty());
		
		try
		{
			accountService.updateAccount(5, newAccount);
		}
		catch (AccountNotFoundException e) {
			assertEquals(new AccountNotFoundException("No Account Found with id: 5").getMessage(), e.getMessage());
			verify(accountRepository).findById(5);
			verify(accountRepository,never()).save(Mockito.any(Account.class));
		}	
	}

}
