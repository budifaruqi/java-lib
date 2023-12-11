package com.example.test.command.impl.category;

import com.example.test.command.category.CreateCategoryCommand;
import com.example.test.command.model.category.CreateCategoryCommandRequest;
import com.example.test.repository.CategoryRepository;
import com.example.test.repository.model.Category;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CreateCategoryCommandImpl implements CreateCategoryCommand {

  private final CategoryRepository categoryRepository;

  public CreateCategoryCommandImpl(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Override
  public Mono<Void> execute(CreateCategoryCommandRequest request) {
    return Mono.fromSupplier(() -> createCategory(request))
        .flatMap(categoryRepository::save)
        .then();
  }

  private Category createCategory(CreateCategoryCommandRequest request) {
    return Category.builder()
        .name(request.getName())
        .build();
  }
}
