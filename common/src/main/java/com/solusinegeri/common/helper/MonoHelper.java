/*    */
package com.solusinegeri.common.helper;
/*    */
/*    */

import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuple4;
import reactor.util.function.Tuple5;
import reactor.util.function.Tuple6;
import reactor.util.function.Tuple7;
import reactor.util.function.Tuple8;
import reactor.util.function.Tuples;

public class MonoHelper {

  public static <T1, T2> Mono<Tuple2<T1, T2>> zip(Mono<? extends T1> t1, Mono<? extends T2> t2) {
    return t1.flatMap((r1) -> {
      return t2.map((r2) -> {
        return Tuples.of(r1, r2);
      });
    });
  }

  public static <T1, T2, T3> Mono<Tuple3<T1, T2, T3>> zip(Mono<? extends T1> t1, Mono<? extends T2> t2,
      Mono<? extends T3> t3) {
    return t1.flatMap((r1) -> {
      return t2.flatMap((r2) -> {
        return t3.map((r3) -> {
          return Tuples.of(r1, r2, r3);
        });
      });
    });
  }

  public static <T1, T2, T3, T4> Mono<Tuple4<T1, T2, T3, T4>> zip(Mono<? extends T1> t1, Mono<? extends T2> t2,
      Mono<? extends T3> t3, Mono<? extends T4> t4) {
    return t1.flatMap((r1) -> {
      return t2.flatMap((r2) -> {
        return t3.flatMap((r3) -> {
          return t4.map((r4) -> {
            return Tuples.of(r1, r2, r3, r4);
          });
        });
      });
    });
  }

  public static <T1, T2, T3, T4, T5> Mono<Tuple5<T1, T2, T3, T4, T5>> zip(Mono<? extends T1> t1, Mono<? extends T2> t2,
      Mono<? extends T3> t3, Mono<? extends T4> t4, Mono<? extends T5> t5) {
    return t1.flatMap((r1) -> {
      return t2.flatMap((r2) -> {
        return t3.flatMap((r3) -> {
          return t4.flatMap((r4) -> {
            return t5.map((r5) -> {
              return Tuples.of(r1, r2, r3, r4, r5);
            });
          });
        });
      });
    });
  }

  public static <T1, T2, T3, T4, T5, T6> Mono<Tuple6<T1, T2, T3, T4, T5, T6>> zip(Mono<? extends T1> t1,
      Mono<? extends T2> t2, Mono<? extends T3> t3, Mono<? extends T4> t4, Mono<? extends T5> t5,
      Mono<? extends T6> t6) {
    return t1.flatMap((r1) -> {
      return t2.flatMap((r2) -> {
        return t3.flatMap((r3) -> {
          return t4.flatMap((r4) -> {
            return t5.flatMap((r5) -> {
              return t6.map((r6) -> {
                return Tuples.of(r1, r2, r3, r4, r5, r6);
              });
            });
          });
        });
      });
    });
  }

  public static <T1, T2, T3, T4, T5, T6, T7> Mono<Tuple7<T1, T2, T3, T4, T5, T6, T7>> zip(Mono<? extends T1> t1,
      Mono<? extends T2> t2, Mono<? extends T3> t3, Mono<? extends T4> t4, Mono<? extends T5> t5, Mono<? extends T6> t6,
      Mono<? extends T7> t7) {
    return t1.flatMap((r1) -> {
      return t2.flatMap((r2) -> {
        return t3.flatMap((r3) -> {
          return t4.flatMap((r4) -> {
            return t5.flatMap((r5) -> {
              return t6.flatMap((r6) -> {
                return t7.map((r7) -> {
                  return Tuples.of(r1, r2, r3, r4, r5, r6, r7);
                });
              });
            });
          });
        });
      });
    });
  }

  public static <T1, T2, T3, T4, T5, T6, T7, T8> Mono<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> zip(Mono<? extends T1> t1,
      Mono<? extends T2> t2, Mono<? extends T3> t3, Mono<? extends T4> t4, Mono<? extends T5> t5, Mono<? extends T6> t6,
      Mono<? extends T7> t7, Mono<? extends T8> t8) {
    return t1.flatMap((r1) -> {
      return t2.flatMap((r2) -> {
        return t3.flatMap((r3) -> {
          return t4.flatMap((r4) -> {
            return t5.flatMap((r5) -> {
              return t6.flatMap((r6) -> {
                return t7.flatMap((r7) -> {
                  return t8.map((r8) -> {
                    return Tuples.of(r1, r2, r3, r4, r5, r6, r7, r8);
                  });
                });
              });
            });
          });
        });
      });
    });
  }

  private MonoHelper() {
  }
}