package com.example.test.web.controller.bom;

import com.example.test.command.bom.production.CreateBomProductionCommand;
import com.example.test.command.model.bom.production.CreateBomProductionCommandRequest;
import com.example.test.web.model.request.bom.production.CreateBomProductionWebRequest;
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
@RequestMapping("/asis/bom/production")
public class BomProductionController extends BaseController {

  public BomProductionController(CommandExecutor executor) {
    super(executor);
  }

  @PostMapping
  public Mono<Response<Object>> createBomProduction(@RequestBody CreateBomProductionWebRequest request) {
    CreateBomProductionCommandRequest commandRequest = CreateBomProductionCommandRequest.builder()
        .companyId("657aa8b5b89eb0426b0e52d3")
        .bomId(request.getBomId())
        .qty(request.getQty())
        .build();

    return executor.execute(CreateBomProductionCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }
}
