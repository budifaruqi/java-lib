package com.example.test.command.product.category;

import com.example.test.command.model.product.category.UpdateProductCategoryCommandRequest;
import com.example.test.web.model.response.product.category.GetProductCategoryWebResponse;
import com.solusinegeri.command.reactive.Command;

public interface UpdateProductCategoryCommand
    extends Command<UpdateProductCategoryCommandRequest, GetProductCategoryWebResponse> {}
