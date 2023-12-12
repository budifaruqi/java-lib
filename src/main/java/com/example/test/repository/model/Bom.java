package com.example.test.repository.model;

import com.example.test.common.constant.CollectionName;
import com.example.test.common.vo.MaterialVO;
import com.solusinegeri.data.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = CollectionName.BOM)
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bom extends BaseEntity {

  @Id
  private String id;

  private String productId;

  private List<MaterialVO> materialList;
}
