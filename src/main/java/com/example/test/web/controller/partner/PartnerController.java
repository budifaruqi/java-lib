package com.example.test.web.controller.partner;

import com.example.test.command.model.partner.CreatePartnerCommandRequest;
import com.example.test.command.model.partner.GetAllPartnerCommandRequest;
import com.example.test.command.model.partner.GetPartnerByIdCommandRequest;
import com.example.test.command.model.partner.UpdatePartnerCommandRequest;
import com.example.test.command.partner.CreatePartnerCommand;
import com.example.test.command.partner.DeletePartnerByIdCommand;
import com.example.test.command.partner.GetAllPartnerCommand;
import com.example.test.command.partner.GetPartnerByIdCommand;
import com.example.test.command.partner.UpdatePartnerCommand;
import com.example.test.web.model.request.partner.CreatePartnerWebRequest;
import com.example.test.web.model.request.partner.UpdatePartnerWebRequest;
import com.example.test.web.model.response.partner.GetPartnerWebResponse;
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
@RequestMapping("/asis/partner")
public class PartnerController extends BaseController {

  public PartnerController(CommandExecutor executor) {
    super(executor);
  }

  @PostMapping
  public Mono<Response<Object>> createPartner(@RequestBody CreatePartnerWebRequest request) {
    CreatePartnerCommandRequest commandRequest = CreatePartnerCommandRequest.builder()
        .name(request.getName()
            .toUpperCase())
        .categoryId(request.getCategoryId())
        .phone(request.getPhone())
        .email(request.getEmail())
        .address(request.getAddress())
        .picName(request.getPicName())
        .picPhone(request.getPicPhone())
        .picEmail(request.getPicEmail())
        .isVendor(request.getIsVendor())
        .isCustomer(request.getIsCustomer())
        .isInternal(request.getIsInternal())
        .companyId(request.getCompanyId())
        .build();
    return executor.execute(CreatePartnerCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @GetMapping
  public Mono<Response<List<GetPartnerWebResponse>>> getAllPartner(@RequestParam(required = false) String name,
      @RequestParam(required = false) String categoryId, @RequestParam(required = false) Boolean isVendor,
      @RequestParam(required = false) Boolean isCustomer, @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "-createdDate") String sortBy) {
    GetAllPartnerCommandRequest commandRequest = GetAllPartnerCommandRequest.builder()
        .name(name)
        .categoryId(categoryId)
        .isCustomer(isCustomer)
        .isVendor(isVendor)
        .pageable(PagingHelper.from(page, size, sortBy))
        .build();
    return executor.execute(GetAllPartnerCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @GetMapping("/{id}")
  public Mono<Response<GetPartnerWebResponse>> getPartnerById(@PathVariable String id) {
    GetPartnerByIdCommandRequest commandRequest = GetPartnerByIdCommandRequest.builder()
        .id(id)
        .build();
    return executor.execute(GetPartnerByIdCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @PutMapping("/{id}")
  public Mono<Response<GetPartnerWebResponse>> updatePartnerById(@PathVariable String id,
      @RequestBody UpdatePartnerWebRequest request) {
    UpdatePartnerCommandRequest commandRequest = UpdatePartnerCommandRequest.builder()
        .id(id)
        .name(request.getName()
            .toUpperCase())
        .categoryId(request.getCategoryId())
        .phone(request.getPhone())
        .email(request.getEmail())
        .address(request.getAddress())
        .picName(request.getPicName())
        .picPhone(request.getPicPhone())
        .picEmail(request.getPicEmail())
        .isVendor(request.getIsVendor())
        .isCustomer(request.getIsCustomer())
        .build();

    return executor.execute(UpdatePartnerCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @DeleteMapping("/{id}")
  public Mono<Response<Void>> deletePartnerById(@PathVariable String id) {
    return executor.execute(DeletePartnerByIdCommand.class, id)
        .map(ResponseHelper::ok);
  }
}
