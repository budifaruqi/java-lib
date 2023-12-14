package com.example.test.command.purchaseRequest;

import com.example.test.command.model.purchaseRequest.GetPurchaseRequestByIdCommandRequest;
import com.example.test.web.model.response.purchaseRequest.GetPurchaseRequestWebResponse;
import com.solusinegeri.command.reactive.Command;

public interface GetPurchaseRequestByIdCommand
    extends Command<GetPurchaseRequestByIdCommandRequest, GetPurchaseRequestWebResponse> {}
