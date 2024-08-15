package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import java.time.ZoneId;
import java.util.TimeZone;

@SpringBootApplication
public class WebApplication extends SpringBootServletInitializer {
    private static final Logger logger = LoggerFactory.getLogger(WebApplication.class);

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("America/Los_Angeles")));
        SpringApplication.run(WebApplication.class, args);
    }


}
