package com.expense.service.service;

import com.expense.service.entities.Expense;
import com.expense.service.modal.ExpenseDto;
import com.expense.service.repository.ExpenseRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;


@Service
@AllArgsConstructor
public class ExpenseService {

    private ExpenseRepository expenseRepository;

    private ObjectMapper objectMapper=new ObjectMapper();

    @Autowired
    ExpenseService(ExpenseRepository expenseRepository){
        this.expenseRepository=expenseRepository;
    }

    public boolean createExpense(ExpenseDto expenseDto){
        setCurrency(expenseDto);
         try{
            expenseRepository.save(objectMapper.convertValue(expenseDto, Expense.class));
            return true;
         } catch (Exception e) {
             return false;
         }
    }

    public boolean updateExpense(ExpenseDto expenseDto){
        Optional<Expense> expenseFound= expenseRepository.findByUserIdAndExternalId(expenseDto.getUserId(),expenseDto.getExternalId());
        if(expenseFound.isEmpty()){
            return false;
        }
        Expense expense=expenseFound.get();
        expense.setCurrency(Strings.isNotBlank(expenseDto.getCurrency())? expenseDto.getCurrency() : expense.getCurrency());
        expense.setMerchant(Strings.isNotBlank(expenseDto.getMerchant())? expenseDto.getMerchant() : expense.getMerchant());
        expense.setAmount(Strings.isNotBlank(expenseDto.getAmount())?expenseDto.getAmount() :expense.getAmount());
        expenseRepository.save(expense);
        return true;
    }

    public List<ExpenseDto> getExpense(String userId){
        List<Expense> expenseList=expenseRepository.findByUserId(userId);
        return objectMapper.convertValue(expenseList, new TypeReference<List<ExpenseDto>>() {});
    }

    public void setCurrency(ExpenseDto expenseDto){
        if(Objects.isNull(expenseDto.getCurrency())){
            expenseDto.setCurrency("inr");
        }
    }
}
