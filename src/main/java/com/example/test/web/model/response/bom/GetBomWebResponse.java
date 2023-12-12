package com.example.test.web.model.response.bom;

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
public class GetBomWebResponse {

  private String id;

  private String productId;

  private String productName;

  private List<MaterialVO> materialList;
}
