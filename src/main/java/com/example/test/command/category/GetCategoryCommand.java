package com.example.test.command.category;


import com.example.test.command.model.category.GetCategoryCommandRequest;
import com.example.test.web.model.response.category.GetCategoryWebResponse;
import com.solusinegeri.command.reactive.Command;

import java.util.List;

public interface GetCategoryCommand extends Command<GetCategoryCommandRequest, List<GetCategoryWebResponse>> {
}
