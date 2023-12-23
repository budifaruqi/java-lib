package com.example.test.web.controller.bom;

import com.example.test.command.bom.production.CreateBomProductionCommand;
import com.example.test.command.bom.production.GetAllBomProductionCommand;
import com.example.test.command.bom.production.GetBomProductionByIdCommand;
import com.example.test.command.model.bom.production.CreateBomProductionCommandRequest;
import com.example.test.command.model.bom.production.GetAllBomProductionCommandRequest;
import com.example.test.command.model.bom.production.GetBomProductionByIdCommandRequest;
import com.example.test.web.model.request.bom.production.CreateBomProductionWebRequest;
import com.example.test.web.model.response.bom.production.GetBomProductionWebResponse;
import com.solusinegeri.command.helper.PagingHelper;
import com.solusinegeri.command.reactive.executor.CommandExecutor;
import com.solusinegeri.common.helper.ResponseHelper;
import com.solusinegeri.common.model.web.response.Response;
import com.solusinegeri.web.controller.reactive.BaseController;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/asis/bom/production")
public class BomProductionController extends BaseController {

  public BomProductionController(CommandExecutor executor) {
    super(executor);
  }

  @PostMapping
  public Mono<Response<Object>> createBomProduction(@RequestBody CreateBomProductionWebRequest request) {
    CreateBomProductionCommandRequest commandRequest = CreateBomProductionCommandRequest.builder()
        .companyId("6583df30dabc3c382a43ba8b")
        .bomId(request.getBomId())
        .qty(request.getQty())
        .build();

    return executor.execute(CreateBomProductionCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @GetMapping
  public Mono<Response<List<GetBomProductionWebResponse>>> getAllBomProduction(
      @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "-createdDate") String sortBy, @RequestParam(required = false) String startDate,
      @RequestParam(required = false) String endDate, @RequestParam(required = false) String transactionId)
      throws ParseException {
    GetAllBomProductionCommandRequest commandRequest = GetAllBomProductionCommandRequest.builder()
        .companyId("657aa8b5b89eb0426b0e52d3")
        .transactionId(transactionId)
        .startDate(dateFormatter(startDate, 0))
        .endDate(dateFormatter(endDate, 1439))
        .pageable(PagingHelper.from(page, size, sortBy))
        .build();

    return executor.execute(GetAllBomProductionCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @GetMapping("/{id}")
  public Mono<Response<GetBomProductionWebResponse>> getBomProductionById(@PathVariable String id) {
    GetBomProductionByIdCommandRequest commandRequest = GetBomProductionByIdCommandRequest.builder()
        .companyId("657aa8b5b89eb0426b0e52d3")
        .id(id)
        .build();

    return executor.execute(GetBomProductionByIdCommand.class, commandRequest)
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
