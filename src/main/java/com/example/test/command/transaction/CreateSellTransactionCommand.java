package com.example.test.command.transaction;

import com.example.test.command.model.transaction.CreateSellTransactionCommandRequest;
import com.solusinegeri.command.reactive.Command;

public interface CreateSellTransactionCommand extends Command<CreateSellTransactionCommandRequest, Object> {}
