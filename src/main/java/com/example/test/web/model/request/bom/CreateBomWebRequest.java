package com.example.test.web.model.request.bom;

import com.example.test.common.vo.MaterialVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBomWebRequest {

  private String name;

  private String productId;

  private List<MaterialVO> materialList;
}
