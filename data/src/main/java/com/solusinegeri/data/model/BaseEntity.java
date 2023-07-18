package com.solusinegeri.data.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import java.util.Date;

@Data
public abstract class BaseEntity {

  @CreatedBy
  protected String createdBy;

  @CreatedDate
  protected Date createdDate;

  protected boolean deleted;

  @LastModifiedBy
  protected String lastModifiedBy;

  @LastModifiedDate
  protected Date lastModifiedDate;

  @Version
  private Long version;
}