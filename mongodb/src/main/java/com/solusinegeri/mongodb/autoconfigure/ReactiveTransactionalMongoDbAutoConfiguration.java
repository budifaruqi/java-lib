/*    */
package com.solusinegeri.mongodb.autoconfigure;
/*    */
/*    */

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.ReactiveMongoTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.reactive.TransactionalOperator;

/*    */
/*    */
@Configuration
/*    */
@ConditionalOnProperty(name = {"abinarystar.data.mongodb.transactional-enabled"}, matchIfMissing = true)
/*    */
@EnableTransactionManagement
/*    */ public class ReactiveTransactionalMongoDbAutoConfiguration
    /*    */ {

  /*    */
  @Bean
  /*    */
  @ConditionalOnMissingBean
  /*    */ public ReactiveTransactionManager transactionManager(ReactiveMongoDatabaseFactory databaseFactory) {
    /* 21 */
    return (ReactiveTransactionManager) new ReactiveMongoTransactionManager(databaseFactory);
    /*    */
  }

  /*    */
  /*    */
  @Bean
  /*    */
  @ConditionalOnMissingBean
  /*    */ public TransactionalOperator transactionalOperator(ReactiveTransactionManager transactionManager) {
    /* 27 */
    return TransactionalOperator.create(transactionManager);
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-data-mongodb/0.5.13/abinarystar-spring-data-mongodb-0.5.13.jar!/com/abinarystar/spring/data/mongodb/autoconfigure/ReactiveTransactionalMongoDbAutoConfiguration.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */