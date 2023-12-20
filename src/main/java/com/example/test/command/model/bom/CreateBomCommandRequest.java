package com.example.test.command.model.bom;

import com.example.test.common.vo.MaterialVO;
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
public class CreateBomCommandRequest {

  @NotBlank
  private String name;

  @NotBlank
  private String productId;

  @NotEmpty
  @Valid
  private List<MaterialVO> materialList;
}
