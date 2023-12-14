package com.example.test.web.model.response.bom.production;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetBomProductionWebResponse {

  private String id;

  private String bomId;

  private Long qty;
}
