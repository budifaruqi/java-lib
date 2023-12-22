package com.example.test.command.transaction;

import com.example.test.command.model.transaction.ConfirmTransactionByIdCommandRequest;
import com.example.test.web.model.response.transaction.GetTransactionWebResponse;
import com.solusinegeri.command.reactive.Command;

public interface ConfirmTransactionByIdCommand
    extends Command<ConfirmTransactionByIdCommandRequest, GetTransactionWebResponse> {}
