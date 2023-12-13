package com.example.test.command.model.purchaseRequest;

import com.example.test.common.enums.PRStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAllPurchaseRequestCommandRequest {

  private String customerId;

  private String vendorId;

  private PRStatus status;

  private Date startDate;

  private Date endDate;

  private Pageable pageable;
}
