package com.example.test.command.impl.brand;

import com.example.test.command.brand.UpdateBrandCommand;
import com.example.test.command.model.brand.UpdateBrandCommandRequest;
import com.example.test.common.constant.ErrorCode;
import com.example.test.repository.BrandRepository;
import com.example.test.repository.model.Brand;
import com.example.test.web.model.response.brand.GetBrandWebResponse;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class UpdateBrandCommandImpl implements UpdateBrandCommand {

  private final BrandRepository brandRepository;

  public UpdateBrandCommandImpl(BrandRepository brandRepository) {
    this.brandRepository = brandRepository;
  }

  @Override
  public Mono<GetBrandWebResponse> execute(UpdateBrandCommandRequest request) {
    return Mono.defer(() -> findPartner(request))
        .flatMap(partner -> Mono.defer(() -> checkRequest(request, partner))
            .map(s -> updatePartner(partner, request))
            .flatMap(brandRepository::save))
        .map(this::toGetWebResponse);
  }

  private Mono<Brand> checkRequest(UpdateBrandCommandRequest request, Brand brand) {
    return brandRepository.findByDeletedFalseAndName(request.getName())
        .switchIfEmpty(Mono.fromSupplier(() -> Brand.builder()
            .build()))
        .filter(s -> s.getId() == null || Objects.equals(s.getId(), request.getId()))
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.NAME_ALREADY_USED)));
  }

  private Mono<Brand> findPartner(UpdateBrandCommandRequest request) {
    return brandRepository.findByDeletedFalseAndId(request.getId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PARTNER_CATEGORY_NOT_EXIST)));
  }

  private GetBrandWebResponse toGetWebResponse(Brand brand) {
    return GetBrandWebResponse.builder()
        .id(brand.getId())
        .name(brand.getName())
        .build();
  }

  private Brand updatePartner(Brand brand, UpdateBrandCommandRequest request) {
    brand.setName(request.getName());

    return brand;
  }
}
