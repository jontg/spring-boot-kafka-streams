package net.ulfhedinn.demo.kafka.shared;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PropertyLogger {
    public PropertyLogger(
            @Value("${spring.application.name}")
            String applicationName
    ) {
        log.info("Starting application " + applicationName);
    }
}
