package com.example.test.web.controller.partner;

import com.example.test.command.model.partner.tag.CreatePartnerTagCommandRequest;
import com.example.test.command.model.partner.tag.GetAllPartnerTagCommandRequest;
import com.example.test.command.model.partner.tag.UpdatePartnerTagCommandRequest;
import com.example.test.command.partner.tag.CreatePartnerTagCommand;
import com.example.test.command.partner.tag.DeletePartnerTagByIdCommand;
import com.example.test.command.partner.tag.GetAllPartnerTagCommand;
import com.example.test.command.partner.tag.GetPartnerTagByIdCommand;
import com.example.test.command.partner.tag.UpdatePartnerTagCommand;
import com.example.test.web.model.request.partner.tag.CreatePartnerTagWebRequest;
import com.example.test.web.model.request.partner.tag.UpdatePartnerTagWebRequest;
import com.example.test.web.model.response.partner.tag.GetPartnerTagWebResponse;
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
@RequestMapping("/asis/partner/tag")
public class PartnerTagController extends BaseController {

  public PartnerTagController(CommandExecutor executor) {
    super(executor);
  }

  @PostMapping
  public Mono<Response<Object>> createPartnerTag(@RequestBody CreatePartnerTagWebRequest request) {
    CreatePartnerTagCommandRequest commandRequest = CreatePartnerTagCommandRequest.builder()
        .name(request.getName()
            .toUpperCase())
        .build();

    return executor.execute(CreatePartnerTagCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @GetMapping
  public Mono<Response<List<GetPartnerTagWebResponse>>> getAllPartnerTag(@RequestParam(required = false) String name,
      @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "-createdDate") String sortBy) {
    GetAllPartnerTagCommandRequest commandRequest = GetAllPartnerTagCommandRequest.builder()
        .name(name)
        .pageable(PagingHelper.from(page, size, sortBy))
        .build();

    return executor.execute(GetAllPartnerTagCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @GetMapping("/{id}")
  public Mono<Response<GetPartnerTagWebResponse>> getPartnerTagById(@PathVariable String id) {
    return executor.execute(GetPartnerTagByIdCommand.class, id)
        .map(ResponseHelper::ok);
  }

  @PutMapping("/{id}")
  public Mono<Response<GetPartnerTagWebResponse>> updatePartnerTagById(@PathVariable String id,
      @RequestBody UpdatePartnerTagWebRequest request) {
    UpdatePartnerTagCommandRequest commandRequest = UpdatePartnerTagCommandRequest.builder()
        .id(id)
        .name(request.getName()
            .toUpperCase())
        .build();

    return executor.execute(UpdatePartnerTagCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @DeleteMapping("/{id}")
  public Mono<Response<Void>> deletePartnerTagById(@PathVariable String id) {
    return executor.execute(DeletePartnerTagByIdCommand.class, id)
        .map(ResponseHelper::ok);
  }
}
