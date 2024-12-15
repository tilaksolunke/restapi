package in.tilaksalunke.restapi.service;

import in.tilaksalunke.restapi.dto.ExpenseDTO;

import java.util.List;

/**
 * Service interface for Expense module
 * @author Tilak S
 * */

public interface ExpenseService {

    /**
     * It will fetch the expense from database
     * @return list
     * */
    List<ExpenseDTO> getAllExpenses();
}
