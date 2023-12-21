package com.example.test.web.controller.product;

import com.example.test.command.model.product.tag.CreateProductTagCommandRequest;
import com.example.test.command.model.product.tag.GetAllProductTagCommandRequest;
import com.example.test.command.model.product.tag.UpdateProductTagCommandRequest;
import com.example.test.command.product.tag.CreateProductTagCommand;
import com.example.test.command.product.tag.DeleteProductTagByIdCommand;
import com.example.test.command.product.tag.GetAllProductTagCommand;
import com.example.test.command.product.tag.GetProductTagByIdCommand;
import com.example.test.command.product.tag.UpdateProductTagCommand;
import com.example.test.web.model.request.product.tag.CreateProductTagWebRequest;
import com.example.test.web.model.request.product.tag.UpdateProductTagWebRequest;
import com.example.test.web.model.response.product.tag.GetProductTagWebResponse;
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
@RequestMapping("/asis/product/tag")
public class ProductTagController extends BaseController {

  public ProductTagController(CommandExecutor executor) {
    super(executor);
  }

  @PostMapping
  public Mono<Response<Object>> createProductTag(@RequestBody CreateProductTagWebRequest request) {
    CreateProductTagCommandRequest commandRequest = CreateProductTagCommandRequest.builder()
        .name(request.getName()
            .toUpperCase())
        .build();

    return executor.execute(CreateProductTagCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @GetMapping
  public Mono<Response<List<GetProductTagWebResponse>>> getAllProductTag(@RequestParam(required = false) String name,
      @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "-createdDate") String sortBy) {
    GetAllProductTagCommandRequest commandRequest = GetAllProductTagCommandRequest.builder()
        .name(name)
        .pageable(PagingHelper.from(page, size, sortBy))
        .build();

    return executor.execute(GetAllProductTagCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @GetMapping("/{id}")
  public Mono<Response<GetProductTagWebResponse>> getProductTagById(@PathVariable String id) {
    return executor.execute(GetProductTagByIdCommand.class, id)
        .map(ResponseHelper::ok);
  }

  @PutMapping("/{id}")
  public Mono<Response<GetProductTagWebResponse>> updateProductTagById(@PathVariable String id,
      @RequestBody UpdateProductTagWebRequest request) {
    UpdateProductTagCommandRequest commandRequest = UpdateProductTagCommandRequest.builder()
        .id(id)
        .name(request.getName()
            .toUpperCase())
        .build();

    return executor.execute(UpdateProductTagCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @DeleteMapping("/{id}")
  public Mono<Response<Void>> deleteProductTagById(@PathVariable String id) {
    return executor.execute(DeleteProductTagByIdCommand.class, id)
        .map(ResponseHelper::ok);
  }
}
