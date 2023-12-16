package com.example.test.web.controller;

import com.example.test.command.model.transaction.CreateTransactionCommandRequest;
import com.example.test.command.transaction.CreateTransactionCommand;
import com.example.test.web.model.request.transaction.CreateTransactionWebRequest;
import com.solusinegeri.command.reactive.executor.CommandExecutor;
import com.solusinegeri.common.helper.ResponseHelper;
import com.solusinegeri.common.model.web.response.Response;
import com.solusinegeri.web.controller.reactive.BaseController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/asis/transaction")
public class TransactionController extends BaseController {

  public TransactionController(CommandExecutor executor) {
    super(executor);
  }

  @PostMapping
  public Mono<Response<Object>> createTransaction(@RequestBody CreateTransactionWebRequest request) {
    CreateTransactionCommandRequest commandRequest = CreateTransactionCommandRequest.builder()
        .companyId("657aa8b5b89eb0426b0e52d3")
        .purchaseRequestId(request.getPurchaseRequestId())
        .productList(request.getProductList())
        .build();

    return executor.execute(CreateTransactionCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }
}
