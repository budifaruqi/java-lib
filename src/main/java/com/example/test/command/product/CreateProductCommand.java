package com.example.test.command.product;

import com.example.test.command.model.product.CreateProductCommandRequest;
import com.solusinegeri.command.reactive.Command;

public interface CreateProductCommand extends Command<CreateProductCommandRequest, Object> {}
