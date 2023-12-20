package com.example.test.command.model.purchaseRequest;

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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePurchaseRequestCommandRequest {

  @NotBlank
  private String customerId;

  @NotBlank
  private String vendorId;

  @NotEmpty
  @Valid
  private List<ProductRequest> productList;

  private String note;
}
