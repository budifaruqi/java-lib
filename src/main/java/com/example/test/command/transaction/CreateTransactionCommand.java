package com.example.test.command.transaction;

import com.example.test.command.model.transaction.CreateTransactionCommandRequest;
import com.solusinegeri.command.reactive.Command;

public interface CreateTransactionCommand extends Command<CreateTransactionCommandRequest, Object> {}
