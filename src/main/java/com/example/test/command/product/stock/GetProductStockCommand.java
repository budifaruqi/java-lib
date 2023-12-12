package com.example.test.command.product.stock;

import com.example.test.command.model.product.stock.GetProductStockCommandRequest;
import com.example.test.web.model.response.product.stock.GetProductStockWebResponse;
import com.solusinegeri.command.reactive.Command;
import org.springframework.data.domain.Page;

public interface GetProductStockCommand
    extends Command<GetProductStockCommandRequest, Page<GetProductStockWebResponse>> {}
