package com.bank.customerAccountTracker.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.customerAccountTracker.entity.Account;
import com.bank.customerAccountTracker.service.AccountServiceImpl;


@RestController
public class AccountController {

	@Autowired
	private AccountServiceImpl accountService;
	
	@GetMapping("/accounts")
	public ResponseEntity<?> getAccounts()
	{
		Map<String, Object> respJsonOutput = new LinkedHashMap<>();
		List<Account> accounts = accountService.getAllAccounts();
		if(!accounts.isEmpty())
		{
			respJsonOutput.put("staus", 1);
			respJsonOutput.put("data", accounts);
			return new ResponseEntity<>(respJsonOutput, HttpStatus.OK);
		}
		else
		{
			respJsonOutput.clear();
			respJsonOutput.put("staus", 0);
			respJsonOutput.put("data", "No Accounts are Found");
			return new ResponseEntity<>(respJsonOutput, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/account/{id}")
	public ResponseEntity<?> getAccountById(@PathVariable Integer id)
	{
		Map<String, Object> respJsonOutput = new LinkedHashMap<>();
		Account account = accountService.getAccountById(id);
		respJsonOutput.put("status", 1);
		respJsonOutput.put("data", account);
		return new ResponseEntity<>(respJsonOutput, HttpStatus.OK);
	}
	
	@PostMapping("/account")
	public ResponseEntity<?> addAccount(@RequestBody Account account)
	{
		Map<String, Object> respJsonOutput = new LinkedHashMap<>();
		Account account1 = accountService.addAccount(account);
		respJsonOutput.put("status", 1);
		respJsonOutput.put("message", "Account is saved successfully!");
		respJsonOutput.put("data", account1);
		return new ResponseEntity<>(respJsonOutput, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/account/{id}")
	public ResponseEntity<?> deleteUserAccount(@PathVariable Integer id)
	{
		Map<String, Object> respJsonOutput = new LinkedHashMap<>();
		Account account = accountService.getAccountById(id);
		accountService.deleteAccount(account);
		respJsonOutput.put("status", 1);
		respJsonOutput.put("message", "Account is deleted successfully!");
		return new ResponseEntity<>(respJsonOutput, HttpStatus.OK);
	}
	
	@DeleteMapping("/accounts")
	public ResponseEntity<?> deleteAllAccounts()
	{
		Map<String, Object> respJsonOutput = new LinkedHashMap<>();
		accountService.deleteAllAccounts();
		respJsonOutput.put("status", 1);
		respJsonOutput.put("message", "All Accounts are deleted successfully!");
		return new ResponseEntity<>(respJsonOutput, HttpStatus.OK);
	}
	
	@PutMapping("/account/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody Account account)
	{
		Map<String, Object> respJsonOutput = new LinkedHashMap<>();
		accountService.updateAccount(id, account);
		respJsonOutput.put("status", 1);
		respJsonOutput.put("data", accountService.getAccountById(id));
		return new ResponseEntity<>(respJsonOutput, HttpStatus.OK);
	}
	
	@PutMapping("/account/transfer")
	public ResponseEntity<?> transferFunds(@RequestParam Integer fromAccount, @RequestParam Integer toAccount, @RequestParam Double amount)
	{
		Map<String, Object> respJsonOutput = new LinkedHashMap<>();
		accountService.transferFunds(fromAccount, toAccount, amount);
		respJsonOutput.put("status", 1);
		respJsonOutput.put("message", "Transaction Successful");
		return new ResponseEntity<>(respJsonOutput, HttpStatus.OK);
	}
}
