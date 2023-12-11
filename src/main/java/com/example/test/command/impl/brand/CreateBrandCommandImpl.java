package com.example.test.command.impl.brand;

import com.example.test.command.brand.CreateBrandCommand;
import com.example.test.command.model.brand.CreateBrandCommandRequest;
import com.example.test.common.constant.ErrorCode;
import com.example.test.repository.BrandRepository;
import com.example.test.repository.model.Brand;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class CreateBrandCommandImpl implements CreateBrandCommand {

  private final BrandRepository brandRepository;

  public CreateBrandCommandImpl(BrandRepository brandRepository) {
    this.brandRepository = brandRepository;
  }

  @Override
  public Mono<Object> execute(CreateBrandCommandRequest request) {
    return Mono.defer(() -> checkRequest(request))
        .map(s -> toBrand(request))
        .flatMap(brandRepository::save);
  }

  private Mono<Brand> checkRequest(CreateBrandCommandRequest request) {
    return brandRepository.findByDeletedFalseAndName(request.getName())
        .switchIfEmpty(Mono.fromSupplier(() -> Brand.builder()
            .build()))
        .filter(s -> !Objects.equals(s.getName(), request.getName()))
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.NAME_ALREADY_USED)));
  }

  private Brand toBrand(CreateBrandCommandRequest request) {
    return Brand.builder()
        .name(request.getName())
        .build();
  }
}
