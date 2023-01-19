package com.shadab.expensetrackerapi.services;

import com.shadab.expensetrackerapi.domain.Transaction;
import com.shadab.expensetrackerapi.exception.EtBadRequestException;
import com.shadab.expensetrackerapi.exception.EtResourceNotFoundException;

import java.util.List;

public interface TransactionService {
    List<Transaction> fetchAllTransactions(Integer userId,Integer categoryId);

    Transaction fetchTransactionById(Integer userId,Integer categoryId,Integer transactionId) throws EtResourceNotFoundException;

    Transaction addTransaction(Integer userId,Integer categoryId,Double amount,String note,long transactionDate) throws EtBadRequestException;

    void updateTransaction(Integer userId,Integer categoryId,Integer transactionId,Transaction transaction) throws EtBadRequestException;

    void removeTransactionById(Integer userId,Integer categoryId,Integer transactionId) throws EtResourceNotFoundException;

}
