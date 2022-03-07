
package com.taulia;

/**
 *
 * @author ahmed-explorer
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author ahmed-explore
 */

@SpringBootApplication
@Configuration
@EnableAutoConfiguration  
public class EventLogApplication   extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(EventLogApplication.class, args);

    }
}
