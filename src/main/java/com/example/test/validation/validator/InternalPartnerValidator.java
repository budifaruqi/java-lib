package com.example.test.validation.validator;

import com.example.test.command.model.partner.CreatePartnerCommandRequest;
import com.example.test.validation.annotation.InternalPartner;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InternalPartnerValidator implements ConstraintValidator<InternalPartner, CreatePartnerCommandRequest> {

  @Override
  public boolean isValid(CreatePartnerCommandRequest value, ConstraintValidatorContext context) {
    return !value.getIsInternal() || (value.getCompanyId() != null && !value.getCompanyId()
        .equals(""));
  }
}
