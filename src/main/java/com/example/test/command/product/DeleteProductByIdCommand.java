package com.example.test.command.product;

import com.example.test.command.model.product.DeleteProductByIdCommandRequest;
import com.solusinegeri.command.reactive.Command;

public interface DeleteProductByIdCommand extends Command<DeleteProductByIdCommandRequest, Void> {}
