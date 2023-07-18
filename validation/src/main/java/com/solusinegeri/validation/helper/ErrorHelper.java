/*    */
package com.solusinegeri.validation.helper;
/*    */
/*    */

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
public class ErrorHelper {

  private static final String CODE = "code";

  private static final String LIST_ELEMENT = ".<list element>";

  private static final String PATH = "path";

  public static <T> List<String> getCodes(Set<ConstraintViolation<T>> violations) {
    return Optional.ofNullable(violations)
        .orElseGet(Collections::emptySet)
        .stream()
        .map(ErrorHelper::getCode)
        .filter(Objects::nonNull)
        .distinct()
        .collect(Collectors.toList());
  }

  public static <T> Map<String, List<String>> getDetail(Set<ConstraintViolation<T>> violations) {
    return Optional.ofNullable(violations)
        .orElseGet(Collections::emptySet)
        .stream()
        .filter(violation -> StringUtils.isNotBlank(violation.getMessage()))
        .collect(Collectors.groupingBy(ErrorHelper::getPropertyPath,
            Collectors.mapping(ConstraintViolation::getMessage, Collectors.toList())));
  }

  @Nullable
  private static <T> String getCode(@NonNull ConstraintViolation<T> violation) {
    return getCustomProperty(violation, CODE);
  }

  @Nullable
  private static <T> String getCustomProperty(@NonNull ConstraintViolation<T> violation, String name) {
    return (String) violation.getConstraintDescriptor()
        .getAttributes()
        .get(name);
  }

  private static <T> String getDefaultPropertyPath(@NonNull ConstraintViolation<T> violation) {
    return Optional.ofNullable(violation.getPropertyPath())
        .map(Path::toString)
        .map(ErrorHelper::removeListElement)
        .orElse("");
  }

  @Nullable
  private static <T> String getPath(@NonNull ConstraintViolation<T> violation) {
    return getCustomProperty(violation, PATH);
  }

  private static <T> String getPropertyPath(@NonNull ConstraintViolation<T> violation) {
    return Optional.ofNullable(getPath(violation))
        .filter(StringUtils::isNotBlank)
        .orElseGet(() -> getDefaultPropertyPath(violation));
  }

  private static String removeListElement(String path) {
    return StringUtils.substringBefore(path, LIST_ELEMENT);
  }

  private ErrorHelper() {
  }
}
