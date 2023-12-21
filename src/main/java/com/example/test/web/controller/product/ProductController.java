package com.example.test.web.controller.product;

import com.example.test.command.model.product.CreateProductCommandRequest;
import com.example.test.command.model.product.DeleteProductByIdCommandRequest;
import com.example.test.command.model.product.GetProductByIdCommandRequest;
import com.example.test.command.model.product.GetProductCommandRequest;
import com.example.test.command.model.product.UpdateProductCommandRequest;
import com.example.test.command.product.CreateProductCommand;
import com.example.test.command.product.DeleteProductByIdCommand;
import com.example.test.command.product.GetAllProductCommand;
import com.example.test.command.product.GetProductByIdCommand;
import com.example.test.command.product.UpdateProductCommand;
import com.example.test.web.model.request.product.CreateProductWebRequest;
import com.example.test.web.model.request.product.UpdateProductWebRequest;
import com.example.test.web.model.response.product.GetProductWebResponse;
import com.solusinegeri.command.helper.PagingHelper;
import com.solusinegeri.command.reactive.executor.CommandExecutor;
import com.solusinegeri.common.helper.ResponseHelper;
import com.solusinegeri.common.model.web.response.Response;
import com.solusinegeri.web.controller.reactive.BaseController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/asis/product")
public class ProductController extends BaseController {

  public ProductController(CommandExecutor executor) {
    super(executor);
  }

  @PostMapping
  public Mono<Response<Object>> createProduct(@RequestBody CreateProductWebRequest request) {
    CreateProductCommandRequest commandRequest = CreateProductCommandRequest.builder()
        .categoryId(request.getCategoryId())
        .brandId(request.getBrandId())
        .name(request.getName())
        .code(request.getCode())
        .sku(request.getSku())
        .unitOfMeasure(request.getUnitOfMeasure())
        .description(request.getDescription())
        .imageUrls(request.getImageUrls())
        .companyShare(request.getCompanyShare())
        .productTagIds(request.getProductTagIds())
        .build();

    return executor.execute(CreateProductCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @GetMapping
  public Mono<Response<List<GetProductWebResponse>>> getAllProduct(@RequestParam(required = false) String name,
      @RequestParam(required = false) String code, @RequestParam(required = false) String sku,
      @RequestParam(required = false) String categoryId, @RequestParam(required = false) String brandId,
      @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "-createdDate") String sortBy) {
    GetProductCommandRequest commandRequest = GetProductCommandRequest.builder()
        .name(name)
        .code(code)
        .sku(sku)
        .categoryId(categoryId)
        .brandId(brandId)
        .pageable(PagingHelper.from(page, size, sortBy))
        .build();

    return executor.execute(GetAllProductCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @GetMapping("/{id}")
  public Mono<Response<GetProductWebResponse>> getProductById(@PathVariable String id) {
    GetProductByIdCommandRequest commandRequest = GetProductByIdCommandRequest.builder()
        .id(id)
        .build();

    return executor.execute(GetProductByIdCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @PutMapping("/{id}")
  public Mono<Response<GetProductWebResponse>> updateProductById(@PathVariable String id,
      @RequestBody UpdateProductWebRequest request) {
    UpdateProductCommandRequest commandRequest = UpdateProductCommandRequest.builder()
        .id(id)
        .categoryId(request.getCategoryId())
        .brandId(request.getBrandId())
        .name(request.getName())
        .code(request.getCode())
        .sku(request.getSku())
        .unitOfMeasure(request.getUnitOfMeasure())
        .isActive(request.getIsActive())
        .description(request.getDescription())
        .imageUrls(request.getImageUrls())
        .companyShare(request.getCompanyShare())
        .productTagIds(request.getProductTagIds())
        .build();

    return executor.execute(UpdateProductCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @DeleteMapping("/{id}")
  public Mono<Response<Void>> deleteProductById(@PathVariable String id) {
    DeleteProductByIdCommandRequest commandRequest = DeleteProductByIdCommandRequest.builder()
        .id(id)
        .build();

    return executor.execute(DeleteProductByIdCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }
}
