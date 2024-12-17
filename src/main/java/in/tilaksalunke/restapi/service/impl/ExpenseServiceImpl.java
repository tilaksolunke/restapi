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
import java.util.UUID;
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
        ExpenseEntity expenseEntity = getExpenseEntity(expenseId);
        log.info("Printing the expense entity details {}", expenseEntity);
        return mapToExpenseDTO(expenseEntity);
    }



    /**
     * It will delete expense from database
     * @param expenseId
     * @return void
     * */
    @Override
    public void deleteExpenseByExpenseId(String expenseId) {
        ExpenseEntity expenseEntity = getExpenseEntity(expenseId);
        log.info("Printing the expense entity {}", expenseEntity);
        expenseRepository.delete(expenseEntity);

    }

    /**
     *  It will save the expense details to database
     * @param expenseDTO
     * @return ExpenseDTO
     * */

    @Override
    public ExpenseDTO saveExpenseDetails(ExpenseDTO expenseDTO) {
        ExpenseEntity newExpenseEntity = mapToExpenseEntity(expenseDTO);
        newExpenseEntity.setExpenseId(UUID.randomUUID().toString());
        newExpenseEntity = expenseRepository.save(newExpenseEntity);
        log.info("Printing the new expense entity details {}", newExpenseEntity);
        return mapToExpenseDTO(newExpenseEntity);
    }

    /**
     *  Mapper method to map values from expense dto to expense entity
     * @param expenseDTO
     * @return ExpenseEntity
     * */

    private ExpenseEntity mapToExpenseEntity(ExpenseDTO expenseDTO) {
        return modelMapper.map(expenseDTO, ExpenseEntity.class);
    }

    /**
     * Mapper method to convert expense entity to expense DTO
     * @param expenseEntity
     * @return ExpenseDTO
     * */

    private ExpenseDTO mapToExpenseDTO(ExpenseEntity expenseEntity) {
        return modelMapper.map(expenseEntity, ExpenseDTO.class);
    }

    /**
     * fetch the expense by expense id from database
     * @param expenseId
     * @return ExpenseEntity
     * */
    private ExpenseEntity getExpenseEntity(String expenseId) {
        return expenseRepository.findByExpenseId(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found for the expense id "+ expenseId));
    }
}
