/*    */
package com.solusinegeri.command.helper;
/*    */
/*    */

import com.solusinegeri.common.helper.CollectionHelper;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ public class PagingHelper
    /*    */ {

  /*    */
  public static Pageable from(int page, int size) {
    /* 21 */
    return from(page, size, null);
    /*    */
  }

  /*    */
  /*    */
  public static Pageable from(int page, int size, String sort) {
    /* 25 */
    return (Pageable) PageRequest.of(page - 1, size, createSort(sort));
    /*    */
  }

  /*    */
  /*    */
  private static Sort createSort(String sort) {
    /* 29 */
    return Optional.<String>ofNullable(sort)
        /* 30 */.filter(StringUtils::isNotBlank)
        /* 31 */.map(PagingHelper::splitSortRequest)
        /* 32 */.map(Arrays::asList)
        /* 33 */.map(PagingHelper::createSortOrders)
        /* 34 */.map(Sort::by)
        /* 35 */.orElseGet(Sort::unsorted);
    /*    */
  }

  /*    */
  /*    */
  private static Sort.Order createSortOrder(String sortRequest) {
    /* 39 */
    char prefix = sortRequest.charAt(0);
    /* 40 */
    if (CharUtils.compare(prefix, '+') == 0)
      /* 41 */ {
      return Sort.Order.asc(StringUtils.substring(sortRequest, 1));
    }
    /* 42 */
    if (CharUtils.compare(prefix, '-') == 0) {
      /* 43 */
      return Sort.Order.desc(StringUtils.substring(sortRequest, 1));
      /*    */
    }
    /* 45 */
    return Sort.Order.asc(sortRequest);
    /*    */
  }

  /*    */
  /*    */
  /*    */
  private static List<Sort.Order> createSortOrders(List<String> sortRequest) {
    /* 50 */
    return CollectionHelper.map(sortRequest, PagingHelper::createSortOrder);
    /*    */
  }

  /*    */
  /*    */
  private static String[] splitSortRequest(String sortRequest) {
    /* 54 */
    return StringUtils.split(sortRequest, ',');
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-command/0.5.13/abinarystar-spring-command-0.5.13.jar!/com/abinarystar/spring/command/helper/PagingHelper.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */