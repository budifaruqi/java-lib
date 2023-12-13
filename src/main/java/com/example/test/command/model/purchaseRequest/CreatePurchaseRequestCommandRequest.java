package com.example.test.command.model.purchaseRequest;

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
