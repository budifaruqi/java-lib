package com.example.test.validation.validator;

import com.example.test.common.enums.TransactionStatus;
import com.example.test.validation.annotation.TransactionStatusSubset;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class TransactionStatusSubsetValidator
    implements ConstraintValidator<TransactionStatusSubset, TransactionStatus> {

  private TransactionStatus[] subset;

  @Override
  public void initialize(TransactionStatusSubset constraintAnnotation) {
    this.subset = constraintAnnotation.anyOf();
  }

  @Override
  public boolean isValid(TransactionStatus status, ConstraintValidatorContext constraintValidatorContext) {
    return status == null || Arrays.asList(subset)
        .contains(status);
  }
}
