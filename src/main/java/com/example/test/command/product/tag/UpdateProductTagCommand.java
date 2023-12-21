package com.example.test.command.product.tag;

import com.example.test.command.model.product.tag.UpdateProductTagCommandRequest;
import com.example.test.web.model.response.product.tag.GetProductTagWebResponse;
import com.solusinegeri.command.reactive.Command;

public interface UpdateProductTagCommand extends Command<UpdateProductTagCommandRequest, GetProductTagWebResponse> {}
