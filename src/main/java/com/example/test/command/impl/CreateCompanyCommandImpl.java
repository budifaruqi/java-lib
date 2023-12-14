package com.example.test.command.impl;

import com.example.test.command.CreateCompanyCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.repository.CompanyRepository;
import com.example.test.repository.model.Company;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CreateCompanyCommandImpl implements CreateCompanyCommand {

  private final CompanyRepository companyRepository;

  public CreateCompanyCommandImpl(CompanyRepository companyRepository) {
    this.companyRepository = companyRepository;
  }

  @Override
  public Mono<Object> execute(String request) {
    return Mono.defer(() -> checkName(request))
        .filter(s -> !s)
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.NAME_ALREADY_USED)))
        .map(s -> toCompany(request))
        .flatMap(companyRepository::save);
  }

  private Mono<Boolean> checkName(String request) {
    return companyRepository.existsByDeletedFalseAndName(request);
  }

  private Company toCompany(String name) {
    return Company.builder()
        .name(name)
        .build();
  }
}
