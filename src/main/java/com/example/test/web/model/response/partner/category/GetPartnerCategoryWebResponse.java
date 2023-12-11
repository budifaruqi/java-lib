package com.example.test.web.model.response.partner.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetPartnerCategoryWebResponse {

  private String id;

  private String name;

  private Boolean isPoint;

  private Boolean isSPK;
}
