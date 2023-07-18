/*    */
package com.solusinegeri.common.helper;
/*    */
/*    */

import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ public class CollectionHelper
    /*    */ {

  /*    */
  public static <T> List<T> add(Collection<T> collection, T addedElement) {
    /* 20 */
    return (List<T>) Stream.concat(toStream(collection), Stream.of(addedElement))
        /* 21 */.collect(Collectors.toList());
    /*    */
  }

  /*    */
  /*    */
  public static <T> T getFirst(Collection<T> collection) {
    /* 25 */
    return getFirst(collection, null);
    /*    */
  }

  /*    */
  /*    */
  public static <T> T getFirst(Collection<T> collection, T defaultValue) {
    /* 29 */
    return Optional.<Collection<T>>ofNullable(collection)
        /* 30 */.map(Collection::stream)
        /* 31 */.flatMap(Stream::findFirst)
        /* 32 */.orElse(defaultValue);
    /*    */
  }

  /*    */
  /*    */
  public static <T, S> List<S> map(Collection<T> collection, @NonNull Function<T, S> mapperFunction) {
    /* 36 */
    return (List<S>) toStream(collection)
        /* 37 */.<S>map(mapperFunction)
        /* 38 */.collect(Collectors.toList());
    /*    */
  }

  /*    */
  /*    */
  public static <T> List<T> remove(Collection<T> collection, T removedElement) {
    /* 42 */
    return (List<T>) toStream(collection)
        /* 43 */.filter(element -> !Objects.equals(element, removedElement))
        /* 44 */.collect(Collectors.toList());
    /*    */
  }

  /*    */
  /*    */
  public static <T> List<T> toListOrEmpty(T element) {
    /* 48 */
    return Optional.<T>ofNullable(element)
        /* 49 */.map(Collections::singletonList)
        /* 50 */.orElseGet(Collections::emptyList);
    /*    */
  }

  /*    */
  /*    */
  private static <T> Stream<T> toStream(Collection<T> collection) {
    /* 54 */
    return Optional.<Collection<T>>ofNullable(collection)
        /* 55 */.stream()
        /* 56 */.flatMap(Collection::stream);
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-common/0.5.13/abinarystar-spring-common-0.5.13.jar!/com/abinarystar/spring/common/helper/CollectionHelper.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */