package com.example.test.command.impl.bom;

import com.example.test.command.bom.UpdateBomCommand;
import com.example.test.command.model.bom.UpdateBomCommandRequest;
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
public class UpdateBomCommandImpl implements UpdateBomCommand {

  private final BomRepository bomRepository;

  private final ProductRepository productRepository;

  public UpdateBomCommandImpl(BomRepository bomRepository, ProductRepository productRepository) {
    this.bomRepository = bomRepository;
    this.productRepository = productRepository;
  }

  @Override
  public Mono<GetBomWebResponse> execute(UpdateBomCommandRequest request) {
    return Mono.defer(() -> findBom(request))
        .flatMap(bom -> Flux.fromIterable(request.getMaterialList())
            .flatMap(this::checkMaterial)
            .collectList()
            .map(materialVOS -> updateBom(bom, materialVOS))
            .flatMap(bomRepository::save))
        .map(this::toGetWebResponse);
  }

  private Mono<Bom> findBom(UpdateBomCommandRequest request) {
    return bomRepository.findByDeletedFalseAndId(request.getId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.BOM_NOT_EXIST)));
  }

  private Mono<MaterialVO> checkMaterial(MaterialVO materialVO) {
    return productRepository.findByDeletedFalseAndId(materialVO.getProductId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PRODUCT_NOT_EXIST)))
        .map(product -> buildMaterial(product, materialVO));
  }

  private MaterialVO buildMaterial(Product product, MaterialVO materialVO) {
    materialVO.setUnitOfMeasure(product.getUnitOfMeasure());

    return materialVO;
  }

  private Bom updateBom(Bom bom, List<MaterialVO> materialVOS) {
    bom.setMaterialList(materialVOS);

    return bom;
  }

  private GetBomWebResponse toGetWebResponse(Bom bom) {
    return GetBomWebResponse.builder()
        .id(bom.getId())
        .productId(bom.getProductId())
        .materialList(bom.getMaterialList())
        .build();
  }
}
