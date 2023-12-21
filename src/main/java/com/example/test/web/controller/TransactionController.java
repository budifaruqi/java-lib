package com.example.test.web.controller;

import com.example.test.command.model.transaction.CreateSellTransactionCommandRequest;
import com.example.test.command.model.transaction.CreateTransactionByPRIdCommandRequest;
import com.example.test.command.model.transaction.UpdateTransactionStatusByIdCommandRequest;
import com.example.test.command.transaction.CreateSellTransactionCommand;
import com.example.test.command.transaction.CreateTransactionByPRIdCommand;
import com.example.test.command.transaction.UpdateTransactionStatusByIdCommand;
import com.example.test.common.enums.TransactionStatus;
import com.example.test.web.model.request.transaction.CreateSellTransactionWebRequest;
import com.example.test.web.model.request.transaction.CreateTransactionByPRIdWebRequest;
import com.example.test.web.model.response.transaction.GetTransactionWebResponse;
import com.solusinegeri.command.reactive.executor.CommandExecutor;
import com.solusinegeri.common.helper.ResponseHelper;
import com.solusinegeri.common.model.web.response.Response;
import com.solusinegeri.web.controller.reactive.BaseController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/asis/transaction")
public class TransactionController extends BaseController {

  public TransactionController(CommandExecutor executor) {
    super(executor);
  }

  @PostMapping("/pr")
  public Mono<Response<Object>> createTransactionByPRId(@RequestBody CreateTransactionByPRIdWebRequest request) {
    CreateTransactionByPRIdCommandRequest commandRequest = CreateTransactionByPRIdCommandRequest.builder()
        .companyId("6583df30dabc3c382a43ba8b")
        .purchaseRequestId(request.getPurchaseRequestId())
        .productList(request.getProductList())
        .build();

    return executor.execute(CreateTransactionByPRIdCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @PostMapping("/sell")
  public Mono<Response<Object>> createSellTransaction(@RequestBody CreateSellTransactionWebRequest request) {
    CreateSellTransactionCommandRequest commandRequest = CreateSellTransactionCommandRequest.builder()
        .companyId(request.getCompanyId())
        .customerId(request.getCustomerId())
        .productList(request.getProductList())
        .note(request.getNote())
        .build();

    return executor.execute(CreateSellTransactionCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @PutMapping("/{id}")
  public Mono<Response<GetTransactionWebResponse>> UpdateTransactionStatusById(@PathVariable String id,
      @RequestParam TransactionStatus status) {
    UpdateTransactionStatusByIdCommandRequest commandRequest = UpdateTransactionStatusByIdCommandRequest.builder()
        .id(id)
        .status(status)
        .build();

    return executor.execute(UpdateTransactionStatusByIdCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }
}
