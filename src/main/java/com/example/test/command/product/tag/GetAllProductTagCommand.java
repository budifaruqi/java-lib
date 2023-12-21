package com.example.test.command.product.tag;

import com.example.test.command.model.product.tag.GetAllProductTagCommandRequest;
import com.example.test.web.model.response.product.tag.GetProductTagWebResponse;
import com.solusinegeri.command.reactive.Command;
import org.springframework.data.domain.Page;

public interface GetAllProductTagCommand
    extends Command<GetAllProductTagCommandRequest, Page<GetProductTagWebResponse>> {}
