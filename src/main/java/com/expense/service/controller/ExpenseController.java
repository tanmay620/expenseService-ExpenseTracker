package com.expense.service.controller;

import com.expense.service.entities.Expense;
import com.expense.service.modal.ExpenseDto;
import com.expense.service.service.ExpenseService;
import jakarta.websocket.server.PathParam;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/expense/v1")
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    ExpenseController(ExpenseService expenseService){
        this.expenseService=expenseService;
    }

    @GetMapping("/getExpense")
    public ResponseEntity<List<ExpenseDto>> getExpense(@RequestHeader(value="X-User_Id") @NonNull String userId){
        try{
            List<ExpenseDto> expenseDtoList=expenseService.getExpense(userId);
            return new ResponseEntity<>(expenseDtoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/updateExpense")
    public ResponseEntity<Boolean> updateExpense(@RequestHeader(value = "X-User-Id") @NonNull String userId, @RequestBody ExpenseDto expenseDto){
        try{
            expenseDto.setUserId(userId);
            boolean result=expenseService.updateExpense(expenseDto);
            if(result){
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    @PostMapping("addExpense")
    public ResponseEntity<Boolean> createExpense(@RequestHeader(value = "X-User-Id") @NonNull String userId, @RequestBody ExpenseDto expenseDto){
        try{
            expenseDto.setUserId(userId);
            boolean result=expenseService.createExpense(expenseDto);
            if(result){
                return ResponseEntity.ok(true);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }
}
