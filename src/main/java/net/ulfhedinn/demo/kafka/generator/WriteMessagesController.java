package net.ulfhedinn.demo.kafka.generator;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;

import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class WriteMessagesController {
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

  private final KafkaProducer producer;

  public WriteMessagesController(KafkaProducer producer) {
    this.producer = producer;
  }

  @Scheduled(fixedDelay = 5_000)
  public void writeMessageToKafka() {
    producer.sendMessage("jontg", "Test message with timestamp " + dateFormat.format(new Date()));
  }
}
