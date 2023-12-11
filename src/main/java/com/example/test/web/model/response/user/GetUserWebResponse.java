package com.example.test.web.model.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserWebResponse {

  private String name;

  private String email;

  private String street;

  private String city;
}
