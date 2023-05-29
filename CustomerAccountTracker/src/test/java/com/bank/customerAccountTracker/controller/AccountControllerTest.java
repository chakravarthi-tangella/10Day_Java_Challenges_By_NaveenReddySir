package com.bank.customerAccountTracker.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.bank.customerAccountTracker.entity.Account;
import com.bank.customerAccountTracker.entity.Customer;
import com.bank.customerAccountTracker.exception.AccountNotFoundException;
import com.bank.customerAccountTracker.exception.TransactionException;
import com.bank.customerAccountTracker.service.AccountService;
import com.bank.customerAccountTracker.service.AccountServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {

	@MockBean
	private AccountServiceImpl accountService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Account account1;
	private Account account2;

	@BeforeEach
	void init()
	{
		account1 = new Account(1, "current", 1000.0, new Customer(1, "Ash", "varanasi", "999999"));
		account2 = new Account(1, "current", 9999.0, new Customer(1, "Ram", "Goa", "777777"));
	}
	
	@Test
	void testGetAccounts() throws Exception
	{
		List<Account> accountList = new ArrayList<>();
		accountList.add(account1);
		accountList.add(account2);
		
		when(accountService.getAllAccounts()).thenReturn(accountList);
		mockMvc.perform(get("/accounts")).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.size()").value(accountList.size()));
	}
	
	@Test
	void testGetAccountById() throws Exception
	{
		int id =1;
		Account account = account1;
		
		when(accountService.getAccountById(id)).thenReturn(account);
		mockMvc.perform(get("/account/{id}",1)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.accountType").value(account.getAccountType()))
				.andExpect(jsonPath("$.data.balance").value(account.getBalance()))
				.andExpect(jsonPath("$.data.accountType").value(account.getAccountType()))
		.andExpect(jsonPath("$.data.customer.customerId").value(account.getCustomer().getCustomerId()))
		.andExpect(jsonPath("$.data.customer.customerName").value(account.getCustomer().getCustomerName()))
		.andExpect(jsonPath("$.data.customer.customerAddress").value(account.getCustomer().getCustomerAddress()))
		.andExpect(jsonPath("$.data.customer.mobileNumber").value(account.getCustomer().getMobileNumber()))
		.andDo(print());
		
	}
	
	@Test
	void testUpdateAccount() throws Exception
	{
		int id =1;
		when(accountService.addAccount(any(Account.class))).thenReturn(account1);
		when(accountService.getAccountById(anyInt())).thenReturn(account1);
		
		this.mockMvc.perform(put("/account/{id}",id).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(account1)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data.accountType").value(account1.getAccountType()))
		.andExpect(jsonPath("$.data.balance").value(account1.getBalance()))
		.andExpect(jsonPath("$.data.accountType").value(account1.getAccountType()))
		.andExpect(jsonPath("$.data.customer.customerId").value(account1.getCustomer().getCustomerId()))
		.andExpect(jsonPath("$.data.customer.customerName").value(account1.getCustomer().getCustomerName()))
		.andExpect(jsonPath("$.data.customer.customerAddress").value(account1.getCustomer().getCustomerAddress()))
		.andExpect(jsonPath("$.data.customer.mobileNumber").value(account1.getCustomer().getMobileNumber()))
		.andDo(print());
		
	}
	
	@Test
	void testAddAccount() throws Exception
	{

		when(accountService.addAccount(any(Account.class))).thenReturn(account1);
		
		this.mockMvc.perform(post("/account").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(account1)))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.data.accountType").value(account1.getAccountType()))
		.andExpect(jsonPath("$.data.balance").value(account1.getBalance()))
		.andExpect(jsonPath("$.data.accountType").value(account1.getAccountType()))
		.andExpect(jsonPath("$.data.customer.customerId").value(account1.getCustomer().getCustomerId()))
		.andExpect(jsonPath("$.data.customer.customerName").value(account1.getCustomer().getCustomerName()))
		.andExpect(jsonPath("$.data.customer.customerAddress").value(account1.getCustomer().getCustomerAddress()))
		.andExpect(jsonPath("$.data.customer.mobileNumber").value(account1.getCustomer().getMobileNumber()))
		.andDo(print());
		
	}
	
	@Test
	void testDeleteAccount() throws Exception
	{
		int id = 1;
		
		doNothing().when(accountService).deleteAccount(any(Account.class));
		
		mockMvc.perform(delete("/account/{id}", id)).andExpect(status().isOk()).andDo(print());
	}
	
	
	@Test
	void testDeleteAllAccount() throws Exception
	{
		
		doNothing().when(accountService).deleteAllAccounts();
		
		mockMvc.perform(delete("/accounts")).andExpect(status().isOk()).andDo(print());
	}
	
	@Test
	void testTransferFunds() throws Exception
	{

		when(accountService.transferFunds(anyInt(), anyInt(), anyDouble())).thenReturn("Transaction Successful");
		
		this.mockMvc.perform(put("/account/transfer")
				.param("fromAccount", "2")
				.param("toAccount", "1")
				.param("amount", "1000.0"))
				.andExpect(status().isOk())
				.andDo(print());
	}
	
	@Test
	void testTransferFundsAccountNotFound() throws Exception
	{

		when(accountService.transferFunds(anyInt(), anyInt(), anyDouble())).thenThrow(AccountNotFoundException.class);
		
		this.mockMvc.perform(put("/account/transfer")
				.param("fromAccount", "5")
				.param("toAccount", "1")
				.param("amount", "1000.0"))
				.andExpect(status().isNotFound())
				.andDo(print());
	}
	
	@Test
	void testTransferFundsTransactionError() throws Exception
	{

		when(accountService.transferFunds(anyInt(), anyInt(), anyDouble())).thenThrow(TransactionException.class);
		
		this.mockMvc.perform(put("/account/transfer")
				.param("fromAccount", "2")
				.param("toAccount", "1")
				.param("amount", "8544000.0"))
				.andExpect(status().isNotFound())
				.andDo(print());
	}
	
}
