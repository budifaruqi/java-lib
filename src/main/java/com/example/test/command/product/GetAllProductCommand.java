package com.example.test.command.product;

import com.example.test.command.model.product.GetProductCommandRequest;
import com.example.test.web.model.response.product.GetProductWebResponse;
import com.solusinegeri.command.reactive.Command;
import org.springframework.data.domain.Page;

public interface GetAllProductCommand extends Command<GetProductCommandRequest, Page<GetProductWebResponse>> {}
