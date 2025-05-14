package com.example.salonmanage;

import com.example.salonmanage.File.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
@EnableScheduling
public class SemoconectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SemoconectApplication.class, args);
    }

}
