package com.expense.service.deserializer;

import com.expense.service.modal.ExpenseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class ExpenseDerserializer implements Deserializer<ExpenseDto> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public ExpenseDto deserialize(String topic, byte[] data) {
        ObjectMapper objectMapper=new ObjectMapper();
        ExpenseDto expenseDto=null;
        try{
            expenseDto=objectMapper.readValue(data,ExpenseDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return expenseDto;
    }

    @Override
    public void close() {
    }

}
