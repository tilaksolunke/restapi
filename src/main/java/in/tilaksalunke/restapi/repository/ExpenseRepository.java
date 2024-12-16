package in.tilaksalunke.restapi.repository;

import in.tilaksalunke.restapi.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * JPA repository for expense resource
 * @author Tilak S
 * */

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {

    /**
     * It will fetch the single expense from database
     * @param expenseId
     * @return OPtional
     * */
    Optional<ExpenseEntity> findByExpenseId(String expenseId);
}
