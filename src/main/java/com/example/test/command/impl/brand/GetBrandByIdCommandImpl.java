package com.example.test.command.impl.brand;

import com.example.test.command.brand.GetBrandByIdCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.repository.BrandRepository;
import com.example.test.repository.model.Brand;
import com.example.test.web.model.response.brand.GetBrandWebResponse;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetBrandByIdCommandImpl implements GetBrandByIdCommand {

  private final BrandRepository brandRepository;

  public GetBrandByIdCommandImpl(BrandRepository brandRepository) {
    this.brandRepository = brandRepository;
  }

  @Override
  public Mono<GetBrandWebResponse> execute(String request) {
    return Mono.defer(() -> findBrand(request))
        .map(this::toGetWebResponse);
  }

  private Mono<Brand> findBrand(String id) {
    return brandRepository.findByDeletedFalseAndId(id)
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PARTNER_CATEGORY_NOT_EXIST)));
  }

  private GetBrandWebResponse toGetWebResponse(Brand brand) {
    return GetBrandWebResponse.builder()
        .id(brand.getId())
        .name(brand.getName())
        .build();
  }
}
