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

  public WriteMessagesController() {
    log.info("=--= Initializing WriteMessagesController =------=");
  }

  @Scheduled(fixedDelay = 5_000)
  public void writeMessageToKafka() {
    log.info("The time is now {}", dateFormat.format(new Date()));
  }
}
