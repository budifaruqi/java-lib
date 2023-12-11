package com.example.test.command.impl.category;

import com.example.test.command.category.GetCategoryCommand;
import com.example.test.command.model.category.GetCategoryCommandRequest;
import com.example.test.repository.CategoryRepository;
import com.example.test.repository.model.Category;
import com.example.test.web.model.response.category.GetCategoryWebResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GetCategoryCommandImpl implements GetCategoryCommand {

  private final CategoryRepository categoryRepository;

  public GetCategoryCommandImpl(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Override
  public Mono<List<GetCategoryWebResponse>> execute(GetCategoryCommandRequest request) {
    return Flux.defer(() -> findCategory(request))
        .map(this::toGetWebResponse)
        .collectList();
  }

  private Flux<Category> findCategory(GetCategoryCommandRequest request) {
    return categoryRepository.findAllByDeletedFalse();
  }

  private GetCategoryWebResponse toGetWebResponse(Category category) {
    return GetCategoryWebResponse.builder()
        .id(category.getId())
        .name(category.getName())
        .build();
  }
}
