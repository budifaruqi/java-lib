package com.example.test.client.model.response;

import com.example.test.common.vo.AddressVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserClientResponse {

  private String id;

  private String name;

  private String email;

  private AddressVO address;
}
