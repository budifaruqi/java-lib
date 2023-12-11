package com.example.test.command.product.category;

import com.example.test.web.model.response.product.category.GetProductCategoryWebResponse;
import com.solusinegeri.command.reactive.Command;

public interface GetProductCategoryByIdCommand extends Command<String, GetProductCategoryWebResponse> {}
