package org.kpaas.sidecar.portal.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class SidecarApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(SidecarApiApplication.class, args);
    }
}