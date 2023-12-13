package com.example.test.command.impl.product.stock;

import com.example.test.command.model.product.stock.CreateProductStockCommandRequest;
import com.example.test.command.product.stock.CreateProductStockCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.repository.ProductRepository;
import com.example.test.repository.ProductStockRepository;
import com.example.test.repository.model.Product;
import com.example.test.repository.model.ProductStock;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CreateProductStockCommandImpl implements CreateProductStockCommand {

  private final ProductStockRepository productStockRepository;

  private final ProductRepository productRepository;

  public CreateProductStockCommandImpl(ProductStockRepository productStockRepository,
      ProductRepository productRepository) {
    this.productStockRepository = productStockRepository;
    this.productRepository = productRepository;
  }

  @Override
  public Mono<Object> execute(CreateProductStockCommandRequest request) {
    return Mono.defer(() -> findProduct(request))
        .flatMap(product -> checkExist(request))
        .filter(s -> !s)
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PRODUCT_STOCK_ALREADY_EXISTS)))
        .map(s -> toProductStock(request))
        .flatMap(productStockRepository::save);
  }

  private Mono<Product> findProduct(CreateProductStockCommandRequest request) {
    return productRepository.findByDeletedFalseAndId(request.getProductId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PRODUCT_NOT_EXIST)))
        .filter(product -> product.getCompanyShare()
            .contains(request.getCompanyId()))
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PRODUCT_NOT_EXIST)));
  }

  private Mono<Boolean> checkExist(CreateProductStockCommandRequest request) {
    return productStockRepository.existsByCompanyIdAndProductIdAndDeletedFalse(request.getCompanyId(),
        request.getProductId());
  }

  private ProductStock toProductStock(CreateProductStockCommandRequest request) {
    return ProductStock.builder()
        .productId(request.getProductId())
        .companyId(request.getCompanyId())
        .stock(request.getStock())
        .hpp(request.getHpp())
        .retailPrice(request.getRetailPrice())
        .groceryPrice(request.getGroceryPrice())
        .build();
  }
}
