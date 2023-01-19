package com.shadab.expensetrackerapi.repository;

import com.shadab.expensetrackerapi.domain.Transaction;
import com.shadab.expensetrackerapi.exception.EtBadRequestException;
import com.shadab.expensetrackerapi.exception.EtResourceNotFoundException;

import java.util.List;

public interface TransactionRepository {
    List<Transaction> findAll(Integer userId,Integer categoryId);

    Transaction findById(Integer userId,Integer categoryId,Integer transactionId) throws EtResourceNotFoundException;

    Integer create(Integer userId,Integer categoryId, Double amount, String note, long transactionDate) throws EtBadRequestException;

    void update(Integer userId,Integer categoryId,Integer transactionId,Transaction transaction) throws EtResourceNotFoundException;

    void removeById(Integer userId,Integer categoryId,Integer transactionById) throws EtResourceNotFoundException;
}
