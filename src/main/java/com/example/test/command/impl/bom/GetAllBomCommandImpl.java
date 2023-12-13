package com.example.test.command.impl.bom;

import com.example.test.command.bom.GetAllBomCommand;
import com.example.test.command.model.bom.GetAllBomCommandRequest;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.vo.MaterialVO;
import com.example.test.repository.BomRepository;
import com.example.test.repository.ProductRepository;
import com.example.test.repository.model.Bom;
import com.example.test.repository.model.Product;
import com.example.test.web.model.response.bom.GetBomWebResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GetAllBomCommandImpl implements GetAllBomCommand {

  private final BomRepository bomRepository;

  private final ProductRepository productRepository;

  public GetAllBomCommandImpl(BomRepository bomRepository, ProductRepository productRepository) {
    this.bomRepository = bomRepository;
    this.productRepository = productRepository;
  }

  @Override
  public Mono<Page<GetBomWebResponse>> execute(GetAllBomCommandRequest request) {
    return Mono.zip(getData(request), count(request))
        .map(objects -> toPageResponse(request, objects.getT1(), objects.getT2()));
  }

  private Mono<Long> count(GetAllBomCommandRequest request) {
    return bomRepository.countAllByDeletedFalseAndFilter(request.getName(), request.getProductId(),
        request.getPageable());
  }

  private Mono<List<GetBomWebResponse>> getData(GetAllBomCommandRequest request) {
    return Flux.defer(() -> getBom(request))
        .flatMapSequential(bom -> Mono.defer(() -> getProduct(bom))
            .flatMap(product -> Flux.fromIterable(bom.getMaterialList())
                .flatMapSequential(this::getProductName)
                .collectList()
                .map(materialVOS -> toGetWebResponse(bom, product, materialVOS))))
        .collectList();
  }

  private Flux<Bom> getBom(GetAllBomCommandRequest request) {
    return bomRepository.findAllByDeletedFalseAndFilter(request.getName(), request.getProductId(),
        request.getPageable());
  }

  private Mono<Product> getProduct(Bom bom) {
    return productRepository.findByDeletedFalseAndId(bom.getProductId())
        .switchIfEmpty(Mono.fromSupplier(() -> Product.builder()
            .name(ErrorCode.PRODUCT_NOT_EXIST)
            .build()));
  }

  private Mono<MaterialVO> getProductName(MaterialVO materialVO) {
    return productRepository.findByDeletedFalseAndId(materialVO.getProductId())
        .switchIfEmpty(Mono.fromSupplier(() -> Product.builder()
            .name(ErrorCode.PRODUCT_NOT_EXIST)
            .build()))
        .map(product -> setName(product, materialVO));
  }

  private MaterialVO setName(Product product, MaterialVO materialVO) {
    materialVO.setProductName(product.getName());

    return materialVO;
  }

  private GetBomWebResponse toGetWebResponse(Bom bom, Product product, List<MaterialVO> materialVOS) {
    return GetBomWebResponse.builder()
        .id(bom.getId())
        .productId(bom.getProductId())
        .productName(product.getName())
        .materialList(materialVOS)
        .build();
  }

  private PageImpl<GetBomWebResponse> toPageResponse(GetAllBomCommandRequest request, List<GetBomWebResponse> contents,
      Long total) {
    return new PageImpl<>(contents, request.getPageable(), total);
  }
}
