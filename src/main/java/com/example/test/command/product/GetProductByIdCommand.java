package com.example.test.command.product;

import com.example.test.command.model.product.GetProductByIdCommandRequest;
import com.example.test.web.model.response.product.GetProductWebResponse;
import com.solusinegeri.command.reactive.Command;

public interface GetProductByIdCommand extends Command<GetProductByIdCommandRequest, GetProductWebResponse> {}
