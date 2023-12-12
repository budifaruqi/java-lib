package com.example.test.command.bom;

import com.example.test.command.model.bom.GetBomByIdCommandRequest;
import com.example.test.web.model.response.bom.GetBomWebResponse;
import com.solusinegeri.command.reactive.Command;

public interface GetBomByIdCommand extends Command<GetBomByIdCommandRequest, GetBomWebResponse> {}
