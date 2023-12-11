package com.example.test.command.product.category;

import com.example.test.command.model.product.category.GetAllProductCategoryCommandRequest;
import com.example.test.web.model.response.product.category.GetProductCategoryWebResponse;
import com.solusinegeri.command.reactive.Command;
import org.springframework.data.domain.Page;

public interface GetAllProductCategoryCommand
    extends Command<GetAllProductCategoryCommandRequest, Page<GetProductCategoryWebResponse>> {}
