package com.example.test.command.bom;

import com.example.test.command.model.bom.GetAllBomCommandRequest;
import com.example.test.web.model.response.bom.GetBomWebResponse;
import com.solusinegeri.command.reactive.Command;
import org.springframework.data.domain.Page;

public interface GetAllBomCommand extends Command<GetAllBomCommandRequest, Page<GetBomWebResponse>> {}
