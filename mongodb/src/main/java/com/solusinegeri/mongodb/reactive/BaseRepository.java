/*    */
package com.solusinegeri.mongodb.reactive;
/*    */
/*    */

import com.solusinegeri.mongodb.helper.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

/*    */
/*    */
/*    */ public abstract class BaseRepository<T>
    /*    */ {

  /*    */   protected final ReactiveMongoTemplate template;

  /*    */   private final Class<T> documentClass;

  /*    */
  /*    */
  public BaseRepository(ReactiveMongoTemplate template, Class<T> documentClass) {
    /* 23 */
    this.template = template;
    /* 24 */
    this.documentClass = documentClass;
    /*    */
  }

  /*    */
  /*    */
  protected <Q extends QueryBuilder> Mono<Page<T>> find(Q queryBuilder, Pageable pageable) {
    /* 28 */
    return find(queryBuilder.build(), pageable);
    /*    */
  }

  /*    */
  /*    */
  protected Mono<Page<T>> find(Query query, Pageable pageable) {
    return this.template.find(Query.of(query)
            .with(pageable), this.documentClass)
        .collect(Collectors.toList())
        .zipWith(this.template.count(Query.of(this.getCountQuery(query)), this.documentClass))
        .map((tuple) -> {
          return new PageImpl((List) tuple.getT1(), pageable, (Long) tuple.getT2());
        });
  }

  /*    */
  /*    */
  protected <Q extends QueryBuilder> Mono<T> findAndModify(Q queryBuilder, Update update) {
    /* 40 */
    return findAndModify(queryBuilder.build(), update);
    /*    */
  }

  /*    */
  /*    */
  /*    */
  protected Mono<T> findAndModify(Query query, Update update) {
    /* 45 */
    FindAndModifyOptions options = FindAndModifyOptions.options()
        .returnNew(true);
    /* 46 */
    return findAndModify(query, update, options);
    /*    */
  }

  /*    */
  /*    */
  /*    */
  protected <Q extends QueryBuilder> Mono<T> findAndModify(Q queryBuilder, Update update,
      FindAndModifyOptions options) {
    /* 51 */
    return findAndModify(queryBuilder.build(), update, options);
    /*    */
  }

  /*    */
  /*    */
  protected Mono<T> findAndModify(Query query, Update update, FindAndModifyOptions options) {
    /* 55 */
    return this.template.findAndModify(query, (UpdateDefinition) update, options, this.documentClass);
    /*    */
  }

  /*    */
  /*    */
  protected <Q extends QueryBuilder> Mono<T> findOne(Q queryBuilder) {
    /* 59 */
    return findOne(queryBuilder.build());
    /*    */
  }

  /*    */
  /*    */
  protected Mono<T> findOne(Query query) {
    /* 63 */
    return this.template.findOne(query, this.documentClass);
    /*    */
  }

  /*    */
  /*    */
  protected <Q extends QueryBuilder> Mono<Void> remove(Q queryBuilder) {
    /* 67 */
    return remove(queryBuilder.build());
    /*    */
  }

  /*    */
  /*    */
  protected Mono<Void> remove(Query query) {
    /* 71 */
    return this.template.remove(query, this.documentClass)
        /* 72 */.then();
    /*    */
  }

  /*    */
  /*    */
  protected <Q extends QueryBuilder> Mono<Void> updateFirst(Q queryBuilder, Update update) {
    /* 76 */
    return updateFirst(queryBuilder.build(), update);
    /*    */
  }

  /*    */
  /*    */
  protected Mono<Void> updateFirst(Query query, Update update) {
    /* 80 */
    return this.template.updateFirst(query, (UpdateDefinition) update, this.documentClass)
        /* 81 */.then();
    /*    */
  }

  /*    */
  /*    */
  protected <Q extends QueryBuilder> Mono<Void> updateMulti(Q queryBuilder, Update update) {
    /* 85 */
    return updateMulti(queryBuilder.build(), update);
    /*    */
  }

  /*    */
  /*    */
  protected Mono<Void> updateMulti(Query query, Update update) {
    /* 89 */
    return this.template.updateMulti(query, (UpdateDefinition) update, this.documentClass)
        /* 90 */.then();
    /*    */
  }

  /*    */
  /*    */
  private Query getCountQuery(Query query) {
    /* 94 */
    return query.limit(0)
        /* 95 */.skip(0L);
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-data-mongodb/0.5.13/abinarystar-spring-data-mongodb-0.5.13.jar!/com/abinarystar/spring/data/mongodb/reactive/BaseRepository.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */