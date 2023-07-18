package com.solusinegeri.data.autoconfigure;

import com.solusinegeri.data.advice.DataExceptionControllerAdvice;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DataExceptionControllerAdvice.class})
public class DataAutoConfiguration {}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-data/0.5.13/abinarystar-spring-data-0.5.13.jar!/com/abinarystar/spring/data/autoconfigure/DataAutoConfiguration.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */