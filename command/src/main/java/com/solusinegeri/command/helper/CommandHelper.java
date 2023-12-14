/*    */
package com.solusinegeri.command.helper;

import jakarta.validation.groups.Default;

import java.util.ArrayList;
import java.util.List;

public class CommandHelper {

  public static Class<?>[] joinValidationGroup(List<Class<?>> validationGroup1, List<Class<?>> validationGroup2) {
    /* 14 */
    List<Class<?>> validationGroup = new ArrayList<>();
    /* 15 */
    validationGroup.addAll(validationGroup1);
    /* 16 */
    validationGroup.addAll(validationGroup2);
    /* 17 */
    validationGroup.add(Default.class);

    return (Class[]) validationGroup.toArray(new Class[0]);
  }

  private CommandHelper() {
  }
}