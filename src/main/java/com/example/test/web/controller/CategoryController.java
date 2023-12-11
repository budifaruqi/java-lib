package com.example.test.web.controller;


import com.example.test.command.category.CreateCategoryCommand;
import com.example.test.command.category.GetCategoryCommand;
import com.example.test.command.model.category.CreateCategoryCommandRequest;
import com.example.test.command.model.category.GetCategoryCommandRequest;
import com.example.test.command.product.CreateProductCommand;
import com.example.test.web.model.request.category.CreateCategoryWebRequest;
import com.example.test.web.model.response.category.GetCategoryWebResponse;
import com.solusinegeri.command.reactive.executor.CommandExecutor;
import com.solusinegeri.common.helper.ResponseHelper;
import com.solusinegeri.common.model.web.response.Response;
import com.solusinegeri.web.controller.reactive.BaseController;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/tes/category")
public class CategoryController extends BaseController {

    public CategoryController(CommandExecutor executor, CreateProductCommand createProductCommand) {
        super(executor);
    }

    @PostMapping
    public Mono<Response<Void>> createCategory(@RequestBody CreateCategoryWebRequest request) {
        CreateCategoryCommandRequest commandRequest = CreateCategoryCommandRequest.builder()
                .name(request.getName())
                .build();

        return executor.execute(CreateCategoryCommand.class, commandRequest)
                .map(ResponseHelper::ok);
    }

    @GetMapping
    public Mono<Response<List<GetCategoryWebResponse>>> getCategory() {
        GetCategoryCommandRequest commandRequest = GetCategoryCommandRequest.builder()
                .build();

        return executor.execute(GetCategoryCommand.class, commandRequest)
                .map(ResponseHelper::ok);
    }
}
