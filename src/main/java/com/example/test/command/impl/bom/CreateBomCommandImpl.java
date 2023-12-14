package com.example.test.command.impl.bom;

import com.example.test.command.bom.CreateBomCommand;
import com.example.test.command.model.bom.CreateBomCommandRequest;
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
public class CreateBomCommandImpl implements CreateBomCommand {

  private final BomRepository bomRepository;

  private final ProductRepository productRepository;

  public CreateBomCommandImpl(BomRepository bomRepository, ProductRepository productRepository) {
    this.bomRepository = bomRepository;
    this.productRepository = productRepository;
  }

  @Override
  public Mono<Object> execute(CreateBomCommandRequest request) {
    return Mono.defer(() -> checkProduct(request))
        .flatMap(product -> Mono.fromSupplier(() -> product)
            .flatMap(product1 -> Flux.fromIterable(request.getMaterialList())
                .flatMapSequential(this::checkMaterial)
                .collectList())
            .map(materials -> toBom(request, product, materials))
            .flatMap(bomRepository::save)
            .map(this::toGetWebResponse));
  }

  private Mono<Boolean> checkExist(CreateBomCommandRequest request) {
    return bomRepository.existsByProductIdAndDeletedFalse(request.getProductId());
  }

  private Mono<Product> checkProduct(CreateBomCommandRequest request) {
    return productRepository.findByDeletedFalseAndId(request.getProductId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PRODUCT_NOT_EXIST)));
  }

  private Mono<MaterialVO> checkMaterial(MaterialVO materialVO) {
    return productRepository.findByDeletedFalseAndId(materialVO.getProductId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PRODUCT_NOT_EXIST)))
        .map(product -> buildMaterial(product, materialVO));
  }

  private MaterialVO buildMaterial(Product product, MaterialVO materialVO) {
    materialVO.setUnitOfMeasure(product.getUnitOfMeasure());
    materialVO.setProductName(product.getName());

    return materialVO;
  }

  private Bom toBom(CreateBomCommandRequest request, Product product, List<MaterialVO> materials) {
    return Bom.builder()
        .name(request.getName())
        .productId(product.getId())
        .materialList(materials)
        .build();
  }

  private GetBomWebResponse toGetWebResponse(Bom bom) {
    return GetBomWebResponse.builder()
        .id(bom.getId())
        .name(bom.getName())
        .productId(bom.getProductId())
        .materialList(bom.getMaterialList())
        .build();
  }
}
