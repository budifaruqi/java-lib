/*    */
package com.solusinegeri.mongodb.autoconfigure;
/*    */
/*    */

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/*    */
/*    */
@Configuration
/*    */
@ConditionalOnProperty(name = {"abinarystar.data.mongodb.transactional-enabled"}, matchIfMissing = true)
/*    */
@EnableTransactionManagement
/*    */ public class TransactionalMongoDbAutoConfiguration
    /*    */ {

  /*    */
  @Bean
  /*    */
  @ConditionalOnMissingBean
  /*    */ public TransactionManager transactionManager(MongoDatabaseFactory databaseFactory) {
    /* 20 */
    return (TransactionManager) new MongoTransactionManager(databaseFactory);
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-data-mongodb/0.5.13/abinarystar-spring-data-mongodb-0.5.13.jar!/com/abinarystar/spring/data/mongodb/autoconfigure/TransactionalMongoDbAutoConfiguration.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */