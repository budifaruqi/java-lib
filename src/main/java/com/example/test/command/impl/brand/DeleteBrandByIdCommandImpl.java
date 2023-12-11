package com.example.test.command.impl.brand;

import com.example.test.command.brand.DeleteBrandByIdCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.repository.BrandRepository;
import com.example.test.repository.model.Brand;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeleteBrandByIdCommandImpl implements DeleteBrandByIdCommand {

  private final BrandRepository brandRepository;

  public DeleteBrandByIdCommandImpl(BrandRepository brandRepository) {
    this.brandRepository = brandRepository;
  }

  @Override
  public Mono<Void> execute(String request) {
    return Mono.defer(() -> findBrand(request))
        .map(this::deletePartner)
        .flatMap(brandRepository::save)
        .then();
  }

  private Mono<Brand> findBrand(String request) {
    return brandRepository.findByDeletedFalseAndId(request)
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PARTNER_NOT_EXIST)));
  }

  private Brand deletePartner(Brand brand) {
    brand.setDeleted(true);

    return brand;
  }
}
