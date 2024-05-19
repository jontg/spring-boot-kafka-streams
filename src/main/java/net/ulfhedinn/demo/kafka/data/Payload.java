package net.ulfhedinn.demo.kafka.data;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class Payload {
  private final Date timestamp;
  private final UUID orgId;
  private final String message;
}
