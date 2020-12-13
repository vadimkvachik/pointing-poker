package com.gpsolutions.pointingpoker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PointingPokerApplication {

    public static void main(final String[] args) {
        SpringApplication.run(PointingPokerApplication.class, args);
    }

}
