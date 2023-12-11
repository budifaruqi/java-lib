package com.example.test.command.category;


import com.example.test.command.model.category.CreateCategoryCommandRequest;
import com.solusinegeri.command.reactive.Command;

public interface CreateCategoryCommand extends Command<CreateCategoryCommandRequest, Void> {
}
