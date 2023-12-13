package com.example.test.command.purchaseRequest;

import com.example.test.command.model.purchaseRequest.GetAllPurchaseRequestCommandRequest;
import com.example.test.web.model.response.purchaseRequest.GetPurchaseRequestWebResponse;
import com.solusinegeri.command.reactive.Command;
import org.springframework.data.domain.Page;

public interface GetAllPurchaseRequestCommand
    extends Command<GetAllPurchaseRequestCommandRequest, Page<GetPurchaseRequestWebResponse>> {}
