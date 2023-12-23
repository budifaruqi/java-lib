package com.example.test.command.impl.product.stock;

import com.example.test.command.model.product.stock.UpdateProductStockByIdCommandRequest;
import com.example.test.command.product.stock.UpdateProductStockByIdCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.enums.TransactionScope;
import com.example.test.common.enums.TransactionStatus;
import com.example.test.common.enums.TransactionType;
import com.example.test.common.vo.ProductRequest;
import com.example.test.repository.ProductRepository;
import com.example.test.repository.ProductStockRepository;
import com.example.test.repository.TransactionRepository;
import com.example.test.repository.model.MainTransaction;
import com.example.test.repository.model.ProductStock;
import com.example.test.web.model.response.product.stock.GetProductStockWebResponse;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
public class UpdateProductStockByIdCommandImpl implements UpdateProductStockByIdCommand {

  private final ProductStockRepository productStockRepository;

  private final ProductRepository productRepository;

  private final TransactionRepository transactionRepository;

  public UpdateProductStockByIdCommandImpl(ProductStockRepository productStockRepository,
      ProductRepository productRepository, TransactionRepository transactionRepository) {
    this.productStockRepository = productStockRepository;
    this.productRepository = productRepository;
    this.transactionRepository = transactionRepository;
  }

  @Override
  public Mono<GetProductStockWebResponse> execute(UpdateProductStockByIdCommandRequest request) {
    return Mono.defer(() -> findStock(request))
        .map(productStock -> updateStock(request, productStock))
        .flatMap(productStockRepository::save)
        .flatMap(s -> Mono.fromSupplier(() -> toTransaction(s))
            .flatMap(transactionRepository::save)
            .map(transaction -> s))
        .map(this::toGetWebResponse);
  }

  private MainTransaction toTransaction(ProductStock productStock) {
    return MainTransaction.builder()
        .vendorId(productStock.getId())
        .customerId(productStock.getId())
        .productList(Collections.singletonList(ProductRequest.builder()
            .productId(productStock.getProductId())
            .qty(productStock.getStock())
            .price(productStock.getHpp())
            .totalPrice(productStock.getStock() * productStock.getHpp())
            .build()))
        .transactionScope(TransactionScope.INTERNAL)
        .transactionType(TransactionType.ADJUSTMENT)
        .amountTotal(productStock.getStock() * productStock.getHpp())
        .status(TransactionStatus.CONFIRMED)
        .build();
  }

  private Mono<ProductStock> findStock(UpdateProductStockByIdCommandRequest request) {
    return productStockRepository.findByDeletedFalseAndId(request.getId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PRODUCT_STOCK_NOT_EXIST)));
  }

  private ProductStock updateStock(UpdateProductStockByIdCommandRequest request, ProductStock productStock) {
    productStock.setStock(request.getStock());
    productStock.setHpp(request.getHpp());
    productStock.setRetailPrice(request.getRetailPrice());
    productStock.setGroceryPrice(request.getGroceryPrice());

    return productStock;
  }

  private GetProductStockWebResponse toGetWebResponse(ProductStock productStock) {
    return GetProductStockWebResponse.builder()
        .id(productStock.getId())
        .productId(productStock.getProductId())
        .companyId(productStock.getCompanyId())
        .stock(productStock.getStock())
        .hpp(productStock.getHpp())
        .retailPrice(productStock.getRetailPrice())
        .groceryPrice(productStock.getGroceryPrice())
        .build();
  }
}
