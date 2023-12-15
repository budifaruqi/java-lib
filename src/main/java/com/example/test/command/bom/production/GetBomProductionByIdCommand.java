package com.example.test.command.bom.production;

import com.example.test.command.model.bom.production.GetBomProductionByIdCommandRequest;
import com.example.test.web.model.response.bom.production.GetBomProductionWebResponse;
import com.solusinegeri.command.reactive.Command;

public interface GetBomProductionByIdCommand
    extends Command<GetBomProductionByIdCommandRequest, GetBomProductionWebResponse> {}
