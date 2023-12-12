package com.example.test.web.controller.product;

import com.example.test.command.model.product.category.CreateProductCategoryCommandRequest;
import com.example.test.command.model.product.category.GetAllProductCategoryCommandRequest;
import com.example.test.command.model.product.category.UpdateProductCategoryCommandRequest;
import com.example.test.command.product.category.CreateProductCategoryCommand;
import com.example.test.command.product.category.DeleteProductCategoryByIdCommand;
import com.example.test.command.product.category.GetAllProductCategoryCommand;
import com.example.test.command.product.category.GetProductCategoryByIdCommand;
import com.example.test.command.product.category.UpdateProductCategoryCommand;
import com.example.test.common.enums.ProductType;
import com.example.test.web.model.request.product.category.CreateProductCategoryWebRequest;
import com.example.test.web.model.request.product.category.UpdateProductCategoryWebRequest;
import com.example.test.web.model.response.product.category.GetProductCategoryWebResponse;
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
@RequestMapping("/asis/product/category")
public class ProductCategoryController extends BaseController {

  public ProductCategoryController(CommandExecutor executor) {
    super(executor);
  }

  @PostMapping
  public Mono<Response<Object>> createProductCategory(@RequestBody CreateProductCategoryWebRequest request) {
    CreateProductCategoryCommandRequest commandRequest = CreateProductCategoryCommandRequest.builder()
        .name(request.getName()
            .toUpperCase())
        .type(request.getType())
        .isDevice(request.getIsDevice())
        .achievementPoint(request.getAchievementPoint())
        .memberPoint(request.getMemberPoint())
        .build();

    return executor.execute(CreateProductCategoryCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @GetMapping
  public Mono<Response<List<GetProductCategoryWebResponse>>> getAllProductCategory(
      @RequestParam(required = false) String name, @RequestParam(required = false) ProductType type,
      @RequestParam(required = false) Boolean isDevice, @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "-createdDate") String sortBy) {
    GetAllProductCategoryCommandRequest commandRequest = GetAllProductCategoryCommandRequest.builder()
        .name(name)
        .type(type)
        .isDevice(isDevice)
        .pageable(PagingHelper.from(page, size, sortBy))
        .build();

    return executor.execute(GetAllProductCategoryCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @GetMapping("/{id}")
  public Mono<Response<GetProductCategoryWebResponse>> getProductCategoryById(@PathVariable String id) {
    return executor.execute(GetProductCategoryByIdCommand.class, id)
        .map(ResponseHelper::ok);
  }

  @PutMapping("/{id}")
  public Mono<Response<GetProductCategoryWebResponse>> updateProductCategoryById(@PathVariable String id,
      @RequestBody UpdateProductCategoryWebRequest request) {
    UpdateProductCategoryCommandRequest commandRequest = UpdateProductCategoryCommandRequest.builder()
        .id(id)
        .name(request.getName()
            .toUpperCase())
        .type(request.getType())
        .isDevice(request.getIsDevice())
        .achievementPoint(request.getAchievementPoint())
        .memberPoint(request.getMemberPoint())
        .build();

    return executor.execute(UpdateProductCategoryCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @DeleteMapping("/{id}")
  public Mono<Response<Void>> deleteProductCategoryById(@PathVariable String id) {
    return executor.execute(DeleteProductCategoryByIdCommand.class, id)
        .map(ResponseHelper::ok);
  }
}
