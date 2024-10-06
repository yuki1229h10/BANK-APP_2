package com.eazybytes.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.model.AccountTransactions;
import com.eazybytes.repository.AccountTransactionsRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BalanceController {

	private final AccountTransactionsRepository accountTransactionsRepository;

	@GetMapping("/myBalance")
	public List<AccountTransactions> getBalanceDetails(@RequestParam long id) {
		List<AccountTransactions> accountTransactions = accountTransactionsRepository
				.findByCustomerIdOrderByTransactionDtDesc(id);
		if (accountTransactions != null) {
			return accountTransactions;
		} else {
			return null;
		}
	}

}
