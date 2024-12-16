package in.tilaksalunke.restapi.service.impl;

import in.tilaksalunke.restapi.dto.ExpenseDTO;
import in.tilaksalunke.restapi.entity.ExpenseEntity;
import in.tilaksalunke.restapi.exceptions.ResourceNotFoundException;
import in.tilaksalunke.restapi.repository.ExpenseRepository;
import in.tilaksalunke.restapi.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for expense module
 * @author Tilak S
 * */
@Service
@Slf4j
@RequiredArgsConstructor

public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ModelMapper modelMapper;

    /**
     * It will fetch the expense from database
     * @return list
     * */
    @Override
    public List<ExpenseDTO> getAllExpenses(){
        //Call the repository method
        List<ExpenseEntity> list = expenseRepository.findAll();
        log.info("Printing the data from repository {}", list);

        //Convert the entity object to DTO object
        List<ExpenseDTO> listOfExpenses  =  list.stream().map(expenseEntity -> mapToExpenseDTO(expenseEntity)).collect(Collectors.toList());

        //Return the list
        return listOfExpenses;
    }

    /**
     * It will fetch the single expense from database
     * @param expenseId
     * @return ExpenseDTO
     * */

    @Override
    public ExpenseDTO getExpenseByExpenseId(String expenseId) {
        ExpenseEntity expenseEntity = expenseRepository.findByExpenseId(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found for the expense id "+expenseId));
        log.info("Printing the expense entity details {}", expenseEntity);
        return mapToExpenseDTO(expenseEntity);
    }

    /**
     * Mapper method to convert expense entity to expense DTO
     * @param expenseEntity
     * @return ExpenseDTO
     * */

    private ExpenseDTO mapToExpenseDTO(ExpenseEntity expenseEntity) {
        return modelMapper.map(expenseEntity, ExpenseDTO.class);
    }
}
