package com.shadab.expensetrackerapi.services;

import com.shadab.expensetrackerapi.domain.Transaction;
import com.shadab.expensetrackerapi.exception.EtBadRequestException;
import com.shadab.expensetrackerapi.exception.EtResourceNotFoundException;
import com.shadab.expensetrackerapi.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TransactionServiceImp implements TransactionService{
    @Autowired
    TransactionRepository transactionRepository;
    @Override
    public List<Transaction> fetchAllTransactions(Integer userId, Integer categoryId) {
        return transactionRepository.findAll(userId,categoryId);
    }

    @Override
    public Transaction fetchTransactionById(Integer userId, Integer categoryId, Integer transactionId) throws EtResourceNotFoundException {
        return transactionRepository.findById(userId,categoryId,transactionId);
    }

    @Override
    public Transaction addTransaction(Integer userId, Integer categoryId, Double amount, String note, long transactionDate) throws EtBadRequestException {
        Integer transactionId = transactionRepository.create(userId,categoryId,amount,note,transactionDate);
        return transactionRepository.findById(userId,categoryId,transactionId);
    }

    @Override
    public void updateTransaction(Integer userId, Integer categoryId,Integer transactionId, Transaction transaction) throws EtBadRequestException {
        transactionRepository.update(userId,categoryId,transactionId,transaction);
    }

    @Override
    public void removeTransactionById(Integer userId, Integer categoryId, Integer transactionId) throws EtResourceNotFoundException {
            transactionRepository.removeById(userId,categoryId,transactionId);
    }
}
