package com.example.test.web.model.request.partner.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePartnerCategoryWebRequest {

  private String name;

  private Boolean isPoint;

  private Boolean isSPK;
}
