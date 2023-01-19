package com.shadab.expensetrackerapi.resources;

import com.shadab.expensetrackerapi.domain.Transaction;
import com.shadab.expensetrackerapi.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories/{categoryId}/transactions")
public class TransactionResource {

    @Autowired
    TransactionService transactionService;

    @GetMapping("")
    public ResponseEntity<List<Transaction>> getAllTransactions(HttpServletRequest req,@PathVariable("categoryId") Integer categoryId){
        int userId = (Integer) req.getAttribute("userId");
        List<Transaction> transactions = transactionService.fetchAllTransactions(userId,categoryId);
        return new ResponseEntity<>(transactions,HttpStatus.OK);
    }
    @GetMapping("/{transactionId}")
    public ResponseEntity<Transaction> getTransaction(HttpServletRequest req, @PathVariable("categoryId") Integer categoryId,
                                                      @PathVariable("transactionId") Integer transactionId){
        Integer userId = (Integer)req.getAttribute("userId");
        Transaction transaction = transactionService.fetchTransactionById(userId,categoryId,transactionId);
        return new ResponseEntity<>(transaction,HttpStatus.OK);
    }
    @PostMapping("")
    public ResponseEntity<Transaction> addTransaction(HttpServletRequest req, @PathVariable("categoryId") Integer categoryId,
                                                      @RequestBody Map<String,Object> transactionMap){
        Integer userId = (Integer)req.getAttribute("userId");
        Double amount = (Double) transactionMap.get("amount");
        String note = (String) transactionMap.get("note");
        long transactionDate = (long)transactionMap.get("transactionDate");
        Transaction transaction = transactionService.addTransaction(userId,categoryId,amount,note,transactionDate);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }
    @PutMapping("/{transactionId}")
    public ResponseEntity<Map<String,Boolean>> updateTransaction(HttpServletRequest req,@PathVariable("categoryId") Integer categoryId,
                                                                 @PathVariable("transactionId") Integer transactionId, @RequestBody Transaction transaction){
        Integer userId = (Integer)req.getAttribute("userId");
        transactionService.updateTransaction(userId,categoryId,transactionId,transaction);
        Map<String,Boolean> map = new HashMap<>();
        map.put("success",true);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }
}
