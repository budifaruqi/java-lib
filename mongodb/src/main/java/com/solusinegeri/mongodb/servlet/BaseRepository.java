/*    */
package com.solusinegeri.mongodb.servlet;
/*    */
/*    */

import com.solusinegeri.mongodb.helper.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.UpdateDefinition;

import java.util.List;

/*    */
/*    */
/*    */
/*    */
/*    */ public abstract class BaseRepository<T>
    /*    */ {

  /*    */   protected final MongoTemplate template;

  /*    */   private final Class<T> documentClass;

  /*    */
  /*    */
  public BaseRepository(MongoTemplate template, Class<T> documentClass) {
    /* 22 */
    this.template = template;
    /* 23 */
    this.documentClass = documentClass;
    /*    */
  }

  /*    */
  /*    */
  protected <Q extends QueryBuilder> Page<T> find(Q queryBuilder, Pageable pageable) {
    /* 27 */
    return find(queryBuilder.build(), pageable);
    /*    */
  }

  /*    */
  /*    */
  protected Page<T> find(Query query, Pageable pageable) {
    List<T> documents = this.template.find(Query.of(query)
        .with(pageable), this.documentClass);
    long count = this.template.count(Query.of(this.getCountQuery(query)), this.documentClass);
    return new PageImpl(documents, pageable, count);
  }

  /*    */
  /*    */
  protected <Q extends QueryBuilder> T findAndModify(Q queryBuilder, Update update) {
    /* 38 */
    return findAndModify(queryBuilder.build(), update);
    /*    */
  }

  /*    */
  /*    */
  /*    */
  protected T findAndModify(Query query, Update update) {
    /* 43 */
    FindAndModifyOptions options = FindAndModifyOptions.options()
        .returnNew(true);
    /* 44 */
    return findAndModify(query, update, options);
    /*    */
  }

  /*    */
  /*    */
  protected <Q extends QueryBuilder> T findAndModify(Q queryBuilder, Update update, FindAndModifyOptions options) {
    /* 48 */
    return findAndModify(queryBuilder.build(), update, options);
    /*    */
  }

  /*    */
  /*    */
  protected T findAndModify(Query query, Update update, FindAndModifyOptions options) {
    /* 52 */
    return (T) this.template.findAndModify(query, (UpdateDefinition) update, options, this.documentClass);
    /*    */
  }

  /*    */
  /*    */
  protected <Q extends QueryBuilder> T findOne(Q queryBuilder) {
    /* 56 */
    return findOne(queryBuilder.build());
    /*    */
  }

  /*    */
  /*    */
  protected T findOne(Query query) {
    /* 60 */
    return (T) this.template.findOne(query, this.documentClass);
    /*    */
  }

  /*    */
  /*    */
  protected <Q extends QueryBuilder> void remove(Q queryBuilder) {
    /* 64 */
    remove(queryBuilder.build());
    /*    */
  }

  /*    */
  /*    */
  protected void remove(Query query) {
    /* 68 */
    this.template.remove(query, this.documentClass);
    /*    */
  }

  /*    */
  /*    */
  protected <Q extends QueryBuilder> void updateFirst(Q queryBuilder, Update update) {
    /* 72 */
    updateFirst(queryBuilder.build(), update);
    /*    */
  }

  /*    */
  /*    */
  protected void updateFirst(Query query, Update update) {
    /* 76 */
    this.template.updateFirst(query, (UpdateDefinition) update, this.documentClass);
    /*    */
  }

  /*    */
  /*    */
  protected <Q extends QueryBuilder> void updateMulti(Q queryBuilder, Update update) {
    /* 80 */
    updateMulti(queryBuilder.build(), update);
    /*    */
  }

  /*    */
  /*    */
  protected void updateMulti(Query query, Update update) {
    /* 84 */
    this.template.updateMulti(query, (UpdateDefinition) update, this.documentClass);
    /*    */
  }

  /*    */
  /*    */
  private Query getCountQuery(Query query) {
    /* 88 */
    return query.limit(0)
        /* 89 */.skip(0L);
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-data-mongodb/0.5.13/abinarystar-spring-data-mongodb-0.5.13.jar!/com/abinarystar/spring/data/mongodb/servlet/BaseRepository.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */