package com.solusinegeri.mongodb.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("abinarystar.data.mongodb")
public class MongoDbProperties {

  private boolean transactionalEnabled = true;
}
