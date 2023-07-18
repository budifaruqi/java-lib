/*     */
package com.solusinegeri.mongodb.helper;
/*     */
/*     */

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.regex.Pattern;

/*     */
/*     */
/*     */
/*     */ public class QueryBuilder
    /*     */ {

  /*     */   private final Pageable pageable;

  /*  21 */   private final Criteria criteria = new Criteria();

  private Criteria getCriteria() {
    return this.criteria;
  }

  /*     */
  private Pageable getPageable() {
    /*  23 */
    return this.pageable;
    /*     */
  }

  /*     */
  private QueryBuilder() {
    /*  26 */
    this.pageable = null;
    /*     */
  }

  /*     */
  /*     */
  private QueryBuilder(Pageable pageable) {
    /*  30 */
    this.pageable = pageable;
    /*     */
  }

  /*     */
  /*     */
  public static QueryBuilder create() {
    /*  34 */
    return new QueryBuilder();
    /*     */
  }

  /*     */
  /*     */
  public static QueryBuilder create(Pageable pageable) {
    /*  38 */
    return new QueryBuilder(pageable);
    /*     */
  }

  /*     */
  /*     */
  public QueryBuilder andBetween(String key, Object start, Object end) {
    /*  42 */
    return andBetween(key, start, end, true, false);
    /*     */
  }

  /*     */
  /*     */
  public QueryBuilder andBetween(String key, Object start, Object end, boolean startInclusive, boolean endInclusive) {
    /*  46 */
    if (Objects.nonNull(start) && Objects.nonNull(end)) {
      /*  47 */
      if (startInclusive && endInclusive) {
        /*  48 */
        this.criteria.and(key)
            /*  49 */.gte(start)
            /*  50 */.lte(end);
        /*  51 */
      } else if (startInclusive) {
        /*  52 */
        this.criteria.and(key)
            /*  53 */.gte(start)
            /*  54 */.lt(end);
        /*  55 */
      } else if (endInclusive) {
        /*  56 */
        this.criteria.and(key)
            /*  57 */.gt(start)
            /*  58 */.lte(end);
        /*     */
      } else {
        /*  60 */
        this.criteria.and(key)
            /*  61 */.gt(start)
            /*  62 */.lt(end);
        /*     */
      }
      /*  64 */
    } else if (Objects.nonNull(start)) {
      /*  65 */
      if (startInclusive) {
        /*  66 */
        this.criteria.and(key)
            /*  67 */.gte(start);
        /*     */
      } else {
        /*  69 */
        this.criteria.and(key)
            /*  70 */.gt(start);
        /*     */
      }
      /*  72 */
    } else if (Objects.nonNull(end)) {
      /*  73 */
      if (endInclusive) {
        /*  74 */
        this.criteria.and(key)
            /*  75 */.lte(end);
        /*     */
      } else {
        /*  77 */
        this.criteria.and(key)
            /*  78 */.lt(end);
        /*     */
      }
      /*     */
    }
    /*  81 */
    return this;
    /*     */
  }

  /*     */
  /*     */
  public QueryBuilder andBetweenExclusive(String key, Object start, Object end) {
    /*  85 */
    return andBetween(key, start, end, false, false);
    /*     */
  }

  /*     */
  /*     */
  public QueryBuilder andBetweenInclusive(String key, Object start, Object end) {
    /*  89 */
    return andBetween(key, start, end, true, true);
    /*     */
  }

  /*     */
  /*     */
  public QueryBuilder andEqual(String key, Object value) {
    /*  93 */
    if (Objects.nonNull(value)) {
      /*  94 */
      this.criteria.and(key)
          /*  95 */.is(value);
      /*     */
    }
    /*  97 */
    return this;
    /*     */
  }

  /*     */
  /*     */
  public QueryBuilder andGreaterThan(String key, Object value) {
    /* 101 */
    if (Objects.nonNull(value)) {
      /* 102 */
      this.criteria.and(key)
          /* 103 */.gt(value);
      /*     */
    }
    /* 105 */
    return this;
    /*     */
  }

  /*     */
  /*     */
  public QueryBuilder andGreaterThanOrEqual(String key, Object value) {
    /* 109 */
    if (Objects.nonNull(value)) {
      /* 110 */
      this.criteria.and(key)
          /* 111 */.gte(value);
      /*     */
    }
    /* 113 */
    return this;
    /*     */
  }

  /*     */
  /*     */
  public QueryBuilder andIn(String key, Object value) {
    /* 117 */
    return andIn(key, Collections.singleton(value));
    /*     */
  }

  /*     */
  /*     */
  public QueryBuilder andIn(String key, Collection<?> collection) {
    /* 121 */
    if (CollectionUtils.isNotEmpty(collection)) {
      /* 122 */
      this.criteria.and(key)
          /* 123 */.in(collection);
      /*     */
    }
    /* 125 */
    return this;
    /*     */
  }

  /*     */
  /*     */
  public QueryBuilder andLessThan(String key, Object value) {
    /* 129 */
    if (Objects.nonNull(value)) {
      /* 130 */
      this.criteria.and(key)
          /* 131 */.lt(value);
      /*     */
    }
    /* 133 */
    return this;
    /*     */
  }

  /*     */
  /*     */
  public QueryBuilder andLessThanOrEqual(String key, Object value) {
    /* 137 */
    if (Objects.nonNull(value)) {
      /* 138 */
      this.criteria.and(key)
          /* 139 */.lte(value);
      /*     */
    }
    /* 141 */
    return this;
    /*     */
  }

  /*     */
  /*     */
  public QueryBuilder andLike(String key, String value) {
    /* 145 */
    return andLike(key, value, 0);
    /*     */
  }

  /*     */
  /*     */
  public QueryBuilder andLikeIgnoreCase(String key, String value) {
    /* 149 */
    return andLike(key, value, 2);
    /*     */
  }

  /*     */
  /*     */
  public QueryBuilder andStartsWith(String key, String value) {
    /* 153 */
    return andStartsWith(key, value, 0);
    /*     */
  }

  /*     */
  /*     */
  public QueryBuilder andStartsWithIgnoreCase(String key, String value) {
    /* 157 */
    return andStartsWith(key, value, 2);
    /*     */
  }

  /*     */
  /*     */
  public Query build() {
    /* 161 */
    Query query = Query.query((CriteriaDefinition) this.criteria);
    /* 162 */
    if (Objects.nonNull(this.pageable)) {
      /* 163 */
      query.with(this.pageable);
      /*     */
    }
    /* 165 */
    return query;
    /*     */
  }

  /*     */
  /*     */
  public QueryBuilder custom(Consumer<Criteria> function) {
    /* 169 */
    function.accept(this.criteria);
    /* 170 */
    return this;
    /*     */
  }

  /*     */
  /*     */
  public QueryBuilder or(QueryBuilder... queryBuilders) {
    /* 174 */
    if (ArrayUtils.isNotEmpty((Object[]) queryBuilders)) {
      /* 175 */
      this.criteria.orOperator(getCriteriaList(queryBuilders));
      /*     */
    }
    /* 177 */
    return this;
    /*     */
  }

  /*     */
  /*     */
  private QueryBuilder andLike(String key, String value, int flags) {
    /* 181 */
    if (Objects.nonNull(value)) {
      /* 182 */
      this.criteria.and(key)
          /* 183 */.regex(Pattern.compile(Pattern.quote(value), flags));
      /*     */
    }
    /* 185 */
    return this;
    /*     */
  }

  /*     */
  /*     */
  private QueryBuilder andStartsWith(String key, String value, int flags) {
    /* 189 */
    if (Objects.nonNull(value)) {
      /* 190 */
      this.criteria.and(key)
          /* 191 */.regex(Pattern.compile("^" + Pattern.quote(value), flags));
      /*     */
    }
    /* 193 */
    return this;
    /*     */
  }

  /*     */
  /*     */
  private Criteria[] getCriteriaList(QueryBuilder[] queryBuilders) {
    /* 197 */
    return (Criteria[]) Arrays.<QueryBuilder>stream(queryBuilders)
        /* 198 */.map(QueryBuilder::getCriteria)
        /* 199 */.toArray(x$0 -> new Criteria[x$0]);
    /*     */
  }
  /*     */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-data-mongodb/0.5.13/abinarystar-spring-data-mongodb-0.5.13.jar!/com/abinarystar/spring/data/mongodb/helper/QueryBuilder.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */