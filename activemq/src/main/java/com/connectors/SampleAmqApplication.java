
package com.connectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class SampleAmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleAmqApplication.class, args);
    }
}
