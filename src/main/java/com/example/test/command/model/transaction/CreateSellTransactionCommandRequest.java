package com.example.test.command.model.transaction;

import com.example.test.common.vo.ProductRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateSellTransactionCommandRequest {

  @NotBlank
  private String companyId;

  @NotBlank
  private String customerId;

  @NotEmpty
  @Valid
  private List<ProductRequest> productList;

  private String note;
}
