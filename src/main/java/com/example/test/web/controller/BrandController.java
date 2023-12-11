package com.example.test.web.controller;

import com.example.test.command.brand.CreateBrandCommand;
import com.example.test.command.brand.DeleteBrandByIdCommand;
import com.example.test.command.brand.GetAllBrandCommand;
import com.example.test.command.brand.GetBrandByIdCommand;
import com.example.test.command.brand.UpdateBrandCommand;
import com.example.test.command.model.brand.CreateBrandCommandRequest;
import com.example.test.command.model.brand.GetAllBrandCommandRequest;
import com.example.test.command.model.brand.UpdateBrandCommandRequest;
import com.example.test.web.model.request.brand.CreateBrandWebRequest;
import com.example.test.web.model.request.brand.UpdateBrandWebRequest;
import com.example.test.web.model.response.brand.GetBrandWebResponse;
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
@RequestMapping("/asis/brand")
public class BrandController extends BaseController {

  public BrandController(CommandExecutor executor) {
    super(executor);
  }

  @PostMapping
  public Mono<Response<Object>> createBrand(@RequestBody CreateBrandWebRequest request) {
    CreateBrandCommandRequest commandRequest = CreateBrandCommandRequest.builder()
        .name(request.getName()
            .toUpperCase())
        .build();

    return executor.execute(CreateBrandCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @GetMapping
  public Mono<Response<List<GetBrandWebResponse>>> getAllBrand(@RequestParam(required = false) String name,
      @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "-createdDate") String sortBy) {
    GetAllBrandCommandRequest commandRequest = GetAllBrandCommandRequest.builder()
        .name(name)
        .pageable(PagingHelper.from(page, size, sortBy))
        .build();

    return executor.execute(GetAllBrandCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @GetMapping("/{id}")
  public Mono<Response<GetBrandWebResponse>> getBrandById(@PathVariable String id) {
    return executor.execute(GetBrandByIdCommand.class, id)
        .map(ResponseHelper::ok);
  }

  @PutMapping("/{id}")
  public Mono<Response<GetBrandWebResponse>> updateBrandById(@PathVariable String id,
      @RequestBody UpdateBrandWebRequest request) {
    UpdateBrandCommandRequest commandRequest = UpdateBrandCommandRequest.builder()
        .id(id)
        .name(request.getName()
            .toUpperCase())
        .build();

    return executor.execute(UpdateBrandCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @DeleteMapping("/{id}")
  public Mono<Response<Void>> deleteBrandById(@PathVariable String id) {
    return executor.execute(DeleteBrandByIdCommand.class, id)
        .map(ResponseHelper::ok);
  }
}
