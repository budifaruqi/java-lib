package com.solusinegeri.common.importfilter;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationImportFilter;
import org.springframework.boot.autoconfigure.AutoConfigurationMetadata;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class CustomAutoConfigurationImportFilter implements AutoConfigurationImportFilter {

  private final Set<String> FILTER;

  @Autowired
  public CustomAutoConfigurationImportFilter(Set<String> filter) {
    this.FILTER = filter;
  }

  @Override
  public boolean[] match(String[] classNames, AutoConfigurationMetadata autoConfigurationMetadata) {
    boolean[] matches = new boolean[classNames.length];

    for (int i = 0; i < classNames.length; ++i) {
      Optional<String> className = Optional.ofNullable(classNames[i]);
      matches[i] = className.map(FILTER::contains)
          .map(BooleanUtils::negate)
          .orElse(false);
    }

    return matches;
  }
}


