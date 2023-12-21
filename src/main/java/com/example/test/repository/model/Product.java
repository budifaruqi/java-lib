package com.example.test.repository.model;

import com.example.test.common.constant.CollectionName;
import com.example.test.common.vo.TagVO;
import com.solusinegeri.data.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = CollectionName.PRODUCT)
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product extends BaseEntity {

  @Id
  private String id;

  private String categoryId;

  private String brandId;

  private String name;

  private String code;

  private String sku;

  private String unitOfMeasure;

  private Boolean isActive;

  private String description;

  private List<String> imageUrls;

  private List<String> companyShare;

  private List<TagVO> productTagList;
}
