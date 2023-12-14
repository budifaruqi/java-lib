package com.example.test.web.model.request.bom.production;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBomProductionWebRequest {

  private String bomId;

  private Long qty;
}
