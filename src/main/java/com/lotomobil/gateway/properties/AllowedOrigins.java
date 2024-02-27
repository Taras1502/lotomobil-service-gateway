package com.lotomobil.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "auth")
public class AllowedOrigins {
  private List<String> allowedOrigins;
}
