package com.example.test.web.controller.product;

import com.example.test.command.model.product.stock.CreateProductStockCommandRequest;
import com.example.test.command.model.product.stock.GetProductStockCommandRequest;
import com.example.test.command.model.product.stock.UpdateProductStockByIdCommandRequest;
import com.example.test.command.product.stock.CreateProductStockCommand;
import com.example.test.command.product.stock.GetProductStockCommand;
import com.example.test.command.product.stock.UpdateProductStockByIdCommand;
import com.example.test.web.model.request.product.stock.CreateProductStockWebRequest;
import com.example.test.web.model.request.product.stock.UpdateProductStockWebRequest;
import com.example.test.web.model.response.product.stock.GetProductStockWebResponse;
import com.solusinegeri.command.helper.PagingHelper;
import com.solusinegeri.command.reactive.executor.CommandExecutor;
import com.solusinegeri.common.helper.ResponseHelper;
import com.solusinegeri.common.model.web.response.Response;
import com.solusinegeri.web.controller.reactive.BaseController;
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
@RequestMapping("/asis/product/stock")
public class ProductStockController extends BaseController {

  public ProductStockController(CommandExecutor executor) {
    super(executor);
  }

  @PostMapping
  public Mono<Response<Object>> createProductStock(@RequestBody CreateProductStockWebRequest request) {
    CreateProductStockCommandRequest commandRequest = CreateProductStockCommandRequest.builder()
        .companyId("X")
        .productId(request.getProductId())
        .stock(request.getStock())
        .hpp(request.getHpp())
        .retailPrice(request.getRetailPrice())
        .groceryPrice(request.getGroceryPrice())
        .build();

    return executor.execute(CreateProductStockCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @GetMapping
  public Mono<Response<List<GetProductStockWebResponse>>> getAllProductStock(
      @RequestParam(required = false) String productId, @RequestParam(required = false) String companyId,
      @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "-createdDate") String sortBy) {
    GetProductStockCommandRequest commandRequest = GetProductStockCommandRequest.builder()
        .companyId(companyId)
        .productId(productId)
        .pageable(PagingHelper.from(page, size, sortBy))
        .build();

    return executor.execute(GetProductStockCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @PutMapping("/{id} ")
  public Mono<Response<GetProductStockWebResponse>> updateProductStockById(@PathVariable String id,
      @RequestBody UpdateProductStockWebRequest request) {
    UpdateProductStockByIdCommandRequest commandRequest = UpdateProductStockByIdCommandRequest.builder()
        .id(id)
        .stock(request.getStock())
        .hpp(request.getHpp())
        .retailPrice(request.getRetailPrice())
        .groceryPrice(request.getGroceryPrice())
        .build();

    return executor.execute(UpdateProductStockByIdCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }
}
