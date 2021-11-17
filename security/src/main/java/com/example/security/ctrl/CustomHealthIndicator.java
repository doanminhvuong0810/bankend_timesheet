package com.example.security.ctrl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * @author Pham Duc Manh
 *
 */
@Component
public class CustomHealthIndicator implements HealthIndicator{

  @Override
  public Health health() {
    try {
      URL siteURL = new URL("http://localhost:9010/api/v2/guest/users/listuser");
      HttpURLConnection connection = (HttpURLConnection)siteURL.openConnection();
      connection.setRequestMethod("GET");
      connection.connect();
      int code = connection.getResponseCode();
      if (code == 200) {
        return Health.up().build();
      }
      else {
        return Health.down().withDetail("Error", "Service is down").build();
      }
    }
    catch(Exception e) {
      return Health.down().withDetail("Error", "Service Unavailable").build();
    }
    
    
    
//    try(Stream<Path> paths = Files.walk(Paths.get("F:\\hocspringcloud\\test1"))){
//      paths.filter(Files::isRegularFile)
//           .forEach(System.out::println);
//      return Health.up().build();
//    } catch (IOException e) {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//      return Health.down().withDetail("FileAccess", "N").build();
//    }  
    }

}
