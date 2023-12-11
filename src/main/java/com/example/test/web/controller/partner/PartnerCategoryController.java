package com.example.test.web.controller.partner;

import com.example.test.command.model.partner.category.CreatePartnerCategoryCommandRequest;
import com.example.test.command.model.partner.category.GetAllPartnerCategoryCommandRequest;
import com.example.test.command.model.partner.category.UpdatePartnerCategoryCommandRequest;
import com.example.test.command.partner.category.CreatePartnerCategoryCommand;
import com.example.test.command.partner.category.DeletePartnerCategoryByIdCommand;
import com.example.test.command.partner.category.GetAllPartnerCategoryCommand;
import com.example.test.command.partner.category.GetPartnerCategoryByIdCommand;
import com.example.test.command.partner.category.UpdatePartnerCategoryCommand;
import com.example.test.web.model.request.partner.category.CreatePartnerCategoryWebRequest;
import com.example.test.web.model.request.partner.category.UpdatePartnerCategoryWebRequest;
import com.example.test.web.model.response.partner.category.GetPartnerCategoryWebResponse;
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
@RequestMapping("/asis/partner/category")
public class PartnerCategoryController extends BaseController {

  public PartnerCategoryController(CommandExecutor executor) {
    super(executor);
  }

  @PostMapping
  public Mono<Response<Object>> createPartnerCategory(@RequestBody CreatePartnerCategoryWebRequest request) {
    CreatePartnerCategoryCommandRequest commandRequest = CreatePartnerCategoryCommandRequest.builder()
        .name(request.getName()
            .toUpperCase())
        .isPoint(request.getIsPoint())
        .isSPK(request.getIsSPK())
        .build();

    return executor.execute(CreatePartnerCategoryCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @GetMapping
  public Mono<Response<List<GetPartnerCategoryWebResponse>>> getAllPartnerCategory(
      @RequestParam(required = false) String name, @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "-createdDate") String sortBy) {
    GetAllPartnerCategoryCommandRequest commandRequest = GetAllPartnerCategoryCommandRequest.builder()
        .name(name)
        .pageable(PagingHelper.from(page, size, sortBy))
        .build();

    return executor.execute(GetAllPartnerCategoryCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @GetMapping("/{id}")
  public Mono<Response<GetPartnerCategoryWebResponse>> getPartnerCategoryById(@PathVariable String id) {
    return executor.execute(GetPartnerCategoryByIdCommand.class, id)
        .map(ResponseHelper::ok);
  }

  @PutMapping("/{id}")
  public Mono<Response<GetPartnerCategoryWebResponse>> updatePartnerCategoryById(@PathVariable String id,
      @RequestBody UpdatePartnerCategoryWebRequest request) {
    UpdatePartnerCategoryCommandRequest commandRequest = UpdatePartnerCategoryCommandRequest.builder()
        .id(id)
        .name(request.getName()
            .toUpperCase())
        .build();

    return executor.execute(UpdatePartnerCategoryCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @DeleteMapping("/{id}")
  public Mono<Response<Void>> deletePartnerCategoryById(@PathVariable String id) {
    return executor.execute(DeletePartnerCategoryByIdCommand.class, id)
        .map(ResponseHelper::ok);
  }
}
