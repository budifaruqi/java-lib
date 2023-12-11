package com.example.test.command.impl.product;

import com.example.test.command.model.product.DeleteProductByIdCommandRequest;
import com.example.test.command.product.DeleteProductByIdCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.repository.ProductRepository;
import com.example.test.repository.model.Product;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeleteProductByIdCommandImpl implements DeleteProductByIdCommand {

  private final ProductRepository productRepository;

  public DeleteProductByIdCommandImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public Mono<Void> execute(DeleteProductByIdCommandRequest request) {
    return Mono.defer(() -> findProduct(request))
        .map(this::deleteProduct)
        .flatMap(productRepository::save)
        .then();
  }

  private Product deleteProduct(Product product) {
    product.setDeleted(true);

    return product;
  }

  private Mono<Product> findProduct(DeleteProductByIdCommandRequest request) {
    return productRepository.findByDeletedFalseAndId(request.getId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PRODUCT_NOT_EXIST)));
  }
}
