package com.example.test.command.bom.production;

import com.example.test.command.model.bom.production.GetAllBomProductionCommandRequest;
import com.example.test.web.model.response.bom.production.GetBomProductionWebResponse;
import com.solusinegeri.command.reactive.Command;
import org.springframework.data.domain.Page;

public interface GetAllBomProductionCommand
    extends Command<GetAllBomProductionCommandRequest, Page<GetBomProductionWebResponse>> {}
