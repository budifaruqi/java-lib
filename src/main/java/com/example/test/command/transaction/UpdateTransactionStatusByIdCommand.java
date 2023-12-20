package com.example.test.command.transaction;

import com.example.test.command.model.transaction.UpdateTransactionStatusByIdCommandRequest;
import com.example.test.web.model.response.transaction.GetTransactionWebResponse;
import com.solusinegeri.command.reactive.Command;

public interface UpdateTransactionStatusByIdCommand
    extends Command<UpdateTransactionStatusByIdCommandRequest, GetTransactionWebResponse> {}
