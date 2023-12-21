package com.example.test.command.product.tag;

import com.example.test.web.model.response.product.tag.GetProductTagWebResponse;
import com.solusinegeri.command.reactive.Command;

public interface GetProductTagByIdCommand extends Command<String, GetProductTagWebResponse> {}
