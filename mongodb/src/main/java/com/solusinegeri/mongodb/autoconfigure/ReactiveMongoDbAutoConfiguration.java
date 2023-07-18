package com.solusinegeri.mongodb.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@EnableReactiveMongoAuditing
@Import({ReactiveTransactionalMongoDbAutoConfiguration.class})
public class ReactiveMongoDbAutoConfiguration {}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-data-mongodb/0.5.13/abinarystar-spring-data-mongodb-0.5.13.jar!/com/abinarystar/spring/data/mongodb/autoconfigure/ReactiveMongoDbAutoConfiguration.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */