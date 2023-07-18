package com.solusinegeri.kafka.producer.servlet;

public interface KafkaProducer {

  void send(String paramString, Object paramObject);

  void send(String paramString1, String paramString2, Object paramObject);
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-kafka/0.5.13/abinarystar-spring-kafka-0.5.13.jar!/com/abinarystar/spring/kafka/producer/servlet/KafkaProducer.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */