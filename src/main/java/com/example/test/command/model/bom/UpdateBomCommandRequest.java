package com.example.test.command.model.bom;

import com.example.test.common.vo.MaterialVO;
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
public class UpdateBomCommandRequest {

  @NotBlank
  private String id;
  
  @NotEmpty
  @Valid
  private List<MaterialVO> materialList;
}
