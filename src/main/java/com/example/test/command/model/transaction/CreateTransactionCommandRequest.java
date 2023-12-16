package com.example.test.command.model.transaction;

import com.example.test.common.vo.ProductRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTransactionCommandRequest {

  @NotBlank
  private String companyId;

  @NotBlank
  private String purchaseRequestId;

  @NotEmpty
  @Valid
  private List<ProductRequest> productList;
}
