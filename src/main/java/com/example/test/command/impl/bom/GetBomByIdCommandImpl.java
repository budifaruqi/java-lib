package com.example.test.command.impl.bom;

import com.example.test.command.bom.GetBomByIdCommand;
import com.example.test.command.model.bom.GetBomByIdCommandRequest;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.vo.MaterialVO;
import com.example.test.repository.BomRepository;
import com.example.test.repository.ProductRepository;
import com.example.test.repository.model.Bom;
import com.example.test.repository.model.Product;
import com.example.test.web.model.response.bom.GetBomWebResponse;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GetBomByIdCommandImpl implements GetBomByIdCommand {

  private final ProductRepository productRepository;

  private final BomRepository bomRepository;

  public GetBomByIdCommandImpl(ProductRepository productRepository, BomRepository bomRepository) {
    this.productRepository = productRepository;
    this.bomRepository = bomRepository;
  }

  @Override
  public Mono<GetBomWebResponse> execute(GetBomByIdCommandRequest request) {
    return Mono.defer(() -> findBom(request))
        .flatMap(bom -> Mono.defer(() -> getProduct(bom))
            .flatMap(product -> Flux.fromIterable(bom.getMaterialList())
                .flatMapSequential(this::getProductName)
                .collectList()
                .map(materialVOS -> toGetWebResponse(bom, product, materialVOS))));
  }

  private Mono<Bom> findBom(GetBomByIdCommandRequest request) {
    return bomRepository.findByDeletedFalseAndId(request.getId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.BOM_NOT_EXIST)));
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
}
