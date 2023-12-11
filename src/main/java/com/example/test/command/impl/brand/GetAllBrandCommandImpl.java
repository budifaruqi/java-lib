package com.example.test.command.impl.brand;

import com.example.test.command.brand.GetAllBrandCommand;
import com.example.test.command.model.brand.GetAllBrandCommandRequest;
import com.example.test.repository.BrandRepository;
import com.example.test.repository.model.Brand;
import com.example.test.web.model.response.brand.GetBrandWebResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GetAllBrandCommandImpl implements GetAllBrandCommand {

  private final BrandRepository brandRepository;

  public GetAllBrandCommandImpl(BrandRepository brandRepository) {
    this.brandRepository = brandRepository;
  }

  @Override
  public Mono<Page<GetBrandWebResponse>> execute(GetAllBrandCommandRequest request) {
    return Mono.zip(getData(request), count(request))
        .map(objects -> toPageResponse(request, objects.getT1(), objects.getT2()));
  }

  private Mono<Long> count(GetAllBrandCommandRequest request) {
    return brandRepository.countAllByDeletedFalseAndFilter(request.getName());
  }

  private Mono<List<GetBrandWebResponse>> getData(GetAllBrandCommandRequest request) {
    return Flux.defer(() -> getPartner(request))
        .map(this::toGetWebResponse)
        .collectList();
  }

  private Flux<Brand> getPartner(GetAllBrandCommandRequest request) {
    return brandRepository.findAllByDeletedFalseAndFilter(request.getName(), request.getPageable());
  }

  private GetBrandWebResponse toGetWebResponse(Brand brand) {
    return GetBrandWebResponse.builder()
        .id(brand.getId())
        .name(brand.getName())
        .build();
  }

  private PageImpl<GetBrandWebResponse> toPageResponse(GetAllBrandCommandRequest request,
      List<GetBrandWebResponse> contents, Long total) {
    return new PageImpl<>(contents, request.getPageable(), total);
  }
}
