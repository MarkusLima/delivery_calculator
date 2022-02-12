package com.challenger.calculator.repositories;

import com.challenger.calculator.models.CalculatorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalculatorRepository extends JpaRepository<CalculatorModel, Long> {
}
