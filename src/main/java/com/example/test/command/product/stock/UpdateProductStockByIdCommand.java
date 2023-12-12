package com.example.test.command.product.stock;

import com.example.test.command.model.product.stock.UpdateProductStockByIdCommandRequest;
import com.example.test.web.model.response.product.stock.GetProductStockWebResponse;
import com.solusinegeri.command.reactive.Command;

public interface UpdateProductStockByIdCommand
    extends Command<UpdateProductStockByIdCommandRequest, GetProductStockWebResponse> {}
