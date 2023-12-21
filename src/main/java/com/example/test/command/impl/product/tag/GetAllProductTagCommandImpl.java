package com.example.test.command.impl.product.tag;

import com.example.test.command.model.product.tag.GetAllProductTagCommandRequest;
import com.example.test.command.product.tag.GetAllProductTagCommand;
import com.example.test.repository.ProductTagRepository;
import com.example.test.repository.model.ProductTag;
import com.example.test.web.model.response.product.tag.GetProductTagWebResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GetAllProductTagCommandImpl implements GetAllProductTagCommand {

  private final ProductTagRepository productTagRepository;

  public GetAllProductTagCommandImpl(ProductTagRepository productTagRepository) {
    this.productTagRepository = productTagRepository;
  }

  @Override
  public Mono<Page<GetProductTagWebResponse>> execute(GetAllProductTagCommandRequest request) {
    return Mono.zip(getData(request), count(request))
        .map(objects -> toPageResponse(request, objects.getT1(), objects.getT2()));
  }

  private Mono<Long> count(GetAllProductTagCommandRequest request) {
    return productTagRepository.countAllByDeletedFalseAndFilter(request.getName());
  }

  private Mono<List<GetProductTagWebResponse>> getData(GetAllProductTagCommandRequest request) {
    return Flux.defer(() -> getPartner(request))
        .map(this::toGetWebResponse)
        .collectList();
  }

  private Flux<ProductTag> getPartner(GetAllProductTagCommandRequest request) {
    return productTagRepository.findAllByDeletedFalseAndFilter(request.getName(), request.getPageable());
  }

  private GetProductTagWebResponse toGetWebResponse(ProductTag productTag) {
    return GetProductTagWebResponse.builder()
        .id(productTag.getId())
        .name(productTag.getName())
        .build();
  }

  private PageImpl<GetProductTagWebResponse> toPageResponse(GetAllProductTagCommandRequest request,
      List<GetProductTagWebResponse> contents, Long total) {
    return new PageImpl<>(contents, request.getPageable(), total);
  }
}
