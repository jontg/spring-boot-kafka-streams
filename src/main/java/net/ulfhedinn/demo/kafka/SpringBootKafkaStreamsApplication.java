package net.ulfhedinn.demo.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = "net.ulfhedinn.demo.kafka.generator")
public class SpringBootKafkaStreamsApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootKafkaStreamsApplication.class, args);
  }

}
