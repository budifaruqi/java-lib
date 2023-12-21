package com.example.test.web.model.response.partner.tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetPartnerTagWebResponse {

  private String id;

  private String name;
}
