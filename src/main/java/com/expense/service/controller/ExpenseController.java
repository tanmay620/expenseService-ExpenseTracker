package com.expense.service.controller;

import com.expense.service.entities.Expense;
import com.expense.service.modal.ExpenseDto;
import com.expense.service.service.ExpenseService;
import jakarta.websocket.server.PathParam;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    ExpenseController(ExpenseService expenseService){
        this.expenseService=expenseService;
    }

    @GetMapping("/expense/v1/")
    public ResponseEntity<List<ExpenseDto>> getExpense(@PathParam("user_id")@NonNull String userId){
        try{
            List<ExpenseDto> expenseDtoList=expenseService.getExpense(userId);
            return new ResponseEntity<>(expenseDtoList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/expense/v1/update")
    public ResponseEntity<String> updateExpense(@RequestBody ExpenseDto expenseDto){
            boolean result=expenseService.updateExpense(expenseDto);
            if(result){
                return ResponseEntity.ok("update successful");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("failed to update expense");
    }

    @GetMapping("/expense/v1/create")
    public ResponseEntity<String> createExpense(@RequestBody ExpenseDto expenseDto){
        boolean result=expenseService.createExpense(expenseDto);
        if(result){
            return ResponseEntity.ok("expense created successful");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("failed to create expense");
    }


}
