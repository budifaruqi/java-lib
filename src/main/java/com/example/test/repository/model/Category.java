package com.example.test.repository.model;


import com.example.test.common.constant.CollectionName;
import com.solusinegeri.data.model.BaseEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = CollectionName.CATEGORY)
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category extends BaseEntity {

    @Id
    private String id;

    private String name;
}
