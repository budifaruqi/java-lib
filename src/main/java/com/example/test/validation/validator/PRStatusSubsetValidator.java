package com.example.test.validation.validator;

import com.example.test.common.enums.PRStatus;
import com.example.test.validation.annotation.PRStatusSubset;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class PRStatusSubsetValidator implements ConstraintValidator<PRStatusSubset, PRStatus> {

  private PRStatus[] subset;

  @Override
  public void initialize(PRStatusSubset constraintAnnotation) {
    this.subset = constraintAnnotation.anyOf();
  }

  @Override
  public boolean isValid(PRStatus status, ConstraintValidatorContext constraintValidatorContext) {
    return status == null || Arrays.asList(subset)
        .contains(status);
  }
}
