package com.example.test.web.model.response.bom.production;

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
public class GetBomProductionWebResponse {

  private String id;

  private String companyId;

  private String transactionId;

  private String bomId;

  private String name;

  private String productName;

  private List<MaterialVO> materialList;

  private Long qty;

  private Long amount;
}
