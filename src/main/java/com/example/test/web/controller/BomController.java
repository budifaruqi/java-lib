package com.example.test.web.controller;

import com.example.test.command.bom.CreateBomCommand;
import com.example.test.command.bom.GetAllBomCommand;
import com.example.test.command.bom.GetBomByIdCommand;
import com.example.test.command.bom.UpdateBomCommand;
import com.example.test.command.model.bom.CreateBomCommandRequest;
import com.example.test.command.model.bom.GetAllBomCommandRequest;
import com.example.test.command.model.bom.GetBomByIdCommandRequest;
import com.example.test.command.model.bom.UpdateBomCommandRequest;
import com.example.test.web.model.request.bom.CreateBomWebRequest;
import com.example.test.web.model.request.bom.UpdateBomByIdWebRequest;
import com.example.test.web.model.response.bom.GetBomWebResponse;
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
@RequestMapping("/asis/bom")
public class BomController extends BaseController {

  public BomController(CommandExecutor executor) {
    super(executor);
  }

  @PostMapping
  public Mono<Response<Object>> createBom(@RequestBody CreateBomWebRequest request) {
    CreateBomCommandRequest commandRequest = CreateBomCommandRequest.builder()
        .name(request.getName())
        .productId(request.getProductId())
        .materialList(request.getMaterialList())
        .build();

    return executor.execute(CreateBomCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @GetMapping
  public Mono<Response<List<GetBomWebResponse>>> getAllBom(@RequestParam String name, @RequestParam String productId,
      @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "-createdDate") String sortBy) {
    GetAllBomCommandRequest commandRequest = GetAllBomCommandRequest.builder()
        .productId(productId)
        .pageable(PagingHelper.from(page, size, sortBy))
        .build();

    return executor.execute(GetAllBomCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @GetMapping("/{id}")
  public Mono<Response<GetBomWebResponse>> getBomById(@PathVariable String id) {
    GetBomByIdCommandRequest commandRequest = GetBomByIdCommandRequest.builder()
        .id(id)
        .build();

    return executor.execute(GetBomByIdCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @PutMapping("/{id}")
  public Mono<Response<GetBomWebResponse>> updateBomById(@PathVariable String id,
      @RequestBody UpdateBomByIdWebRequest request) {
    UpdateBomCommandRequest commandRequest = UpdateBomCommandRequest.builder()
        .id(id)
        .productId(request.getProductId())
        .name(request.getName())
        .materialList(request.getMaterialList())
        .build();

    return executor.execute(UpdateBomCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }
}
