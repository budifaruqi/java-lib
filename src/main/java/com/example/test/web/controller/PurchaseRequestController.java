package com.example.test.web.controller;

import com.example.test.command.model.purchaseRequest.CreatePurchaseRequestCommandRequest;
import com.example.test.command.model.purchaseRequest.GetAllPurchaseRequestCommandRequest;
import com.example.test.command.model.purchaseRequest.GetPurchaseRequestByIdCommandRequest;
import com.example.test.command.model.purchaseRequest.UpdatePurchaseRequestByIdCommandRequest;
import com.example.test.command.model.purchaseRequest.UpdatePurchaseRequestStatusByIdCommandRequest;
import com.example.test.command.purchaseRequest.CreatePurchaseRequestCommand;
import com.example.test.command.purchaseRequest.GetAllPurchaseRequestCommand;
import com.example.test.command.purchaseRequest.GetPurchaseRequestByIdCommand;
import com.example.test.command.purchaseRequest.UpdatePurchaseRequestCommand;
import com.example.test.command.purchaseRequest.UpdatePurchaseRequestStatusByIdCommand;
import com.example.test.common.enums.PRStatus;
import com.example.test.web.model.request.purchaseRequest.CreatePurchaseRequestWebRequest;
import com.example.test.web.model.request.purchaseRequest.UpdatePurchaseRequestByIdWebRequest;
import com.example.test.web.model.response.purchaseRequest.GetPurchaseRequestWebResponse;
import com.solusinegeri.command.helper.PagingHelper;
import com.solusinegeri.command.reactive.executor.CommandExecutor;
import com.solusinegeri.common.helper.ResponseHelper;
import com.solusinegeri.common.model.web.response.Response;
import com.solusinegeri.web.controller.reactive.BaseController;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/asis/purchase_request")
public class PurchaseRequestController extends BaseController {

  public PurchaseRequestController(CommandExecutor executor) {
    super(executor);
  }

  @PostMapping
  public Mono<Response<Object>> createPurchaseRequest(@RequestBody CreatePurchaseRequestWebRequest request) {
    CreatePurchaseRequestCommandRequest commandRequest = CreatePurchaseRequestCommandRequest.builder()
        .customerId("6583df8ddabc3c382a43ba90")
        .vendorId(request.getVendorId())
        .productList(request.getProductList())
        .note(request.getNote())
        .build();

    return executor.execute(CreatePurchaseRequestCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @GetMapping
  public Mono<Response<List<GetPurchaseRequestWebResponse>>> getAllPurchaseRequest(
      @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "-createdDate") String sortBy, @RequestParam(required = false) PRStatus status,
      @RequestParam(required = false) String vendorId, @RequestParam(required = false) String customerId,
      @RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate)
      throws ParseException {
    GetAllPurchaseRequestCommandRequest commandRequest = GetAllPurchaseRequestCommandRequest.builder()
        .customerId(customerId)
        .vendorId(vendorId)
        .status(status)
        .startDate(dateFormatter(startDate, 0))
        .endDate(dateFormatter(endDate, 1439))
        .pageable(PagingHelper.from(page, size, sortBy))
        .build();

    return executor.execute(GetAllPurchaseRequestCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @GetMapping("/{id}")
  public Mono<Response<GetPurchaseRequestWebResponse>> getPurchaseRequestById(@PathVariable String id) {
    GetPurchaseRequestByIdCommandRequest commandRequest = GetPurchaseRequestByIdCommandRequest.builder()
        .id(id)
        .build();

    return executor.execute(GetPurchaseRequestByIdCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @PutMapping("/{id}")
  public Mono<Response<Object>> updatePurchaseRequestById(@PathVariable String id,
      @RequestBody UpdatePurchaseRequestByIdWebRequest request) {
    UpdatePurchaseRequestByIdCommandRequest commandRequest = UpdatePurchaseRequestByIdCommandRequest.builder()
        .id(id)
        .productList(request.getProductList())
        .note(request.getNote())
        .build();

    return executor.execute(UpdatePurchaseRequestCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @PutMapping("/status/{id}")
  public Mono<Response<Object>> updatePurchaseRequestStatusById(@PathVariable String id,
      @RequestParam PRStatus status) {
    UpdatePurchaseRequestStatusByIdCommandRequest commandRequest =
        UpdatePurchaseRequestStatusByIdCommandRequest.builder()
            .id(id)
            .status(status)
            .build();

    return executor.execute(UpdatePurchaseRequestStatusByIdCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  private Date dateFormatter(String date, int i) throws ParseException {
    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    if (date == null) {
      return null;
    } else {
      Date date1 = sdf.parse(date);
      return DateUtils.addMinutes(date1, i);
    }
  }
}
