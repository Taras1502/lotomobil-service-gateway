package com.lotomobil.gateway.config;

import com.lotomobil.gateway.properties.AllowedOrigins;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class CorsConfig {
  private static final Long CORS_MAX_AGE = (long) 36000;

  public static final String TOKEN_KEY_AUTHENTICATION = "Authentication";
  public static final String TOKEN_KEY_REFRESH = "Refresh";
  public static final String TOKEN_KEY_PIN_AUTHENTICATION = "PIN-Authentication";
  public static final String TOKEN_KEY_OTP_AUTHENTICATION = "OTP-Authentication";
  public static final String TOKEN_KEY_API_KEY_AUTHENTICATION = "API-Authentication";
  public static final String TOKEN_KEY_CAPTCHA = "captcha-token";
  public static final String TOKEN_KEY_DEVICE_ID = "device-id";
  public static final String HEADER_AGENT = "agent";
  public static final String APPLICATION = "application";

  private final AllowedOrigins allowedOrigins;

  @Bean
  public CorsWebFilter corsConfiguration() {
    org.springframework.web.cors.CorsConfiguration config = new org.springframework.web.cors.CorsConfiguration();
    val allowHeaders = Arrays.asList("X-Requested-With", "X-CSRF-TOKEN",
      TOKEN_KEY_AUTHENTICATION,
      TOKEN_KEY_API_KEY_AUTHENTICATION,
      TOKEN_KEY_OTP_AUTHENTICATION,
      TOKEN_KEY_DEVICE_ID,
      HEADER_AGENT,
      APPLICATION,
      TOKEN_KEY_PIN_AUTHENTICATION,
      TOKEN_KEY_REFRESH,
      TOKEN_KEY_CAPTCHA,
      HttpHeaders.ACCEPT, HttpHeaders.CONTENT_TYPE, HttpHeaders.ORIGIN,
      HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,
      HttpHeaders.ACCESS_CONTROL_MAX_AGE, HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS);

    config.setAllowCredentials(true);
    allowedOrigins.getAllowedOrigins().forEach(config::addAllowedOrigin);
    config.setMaxAge(CORS_MAX_AGE);

    config.setAllowedHeaders(allowHeaders);
    config.setExposedHeaders(allowHeaders);

    config.addAllowedMethod(HttpMethod.GET);
    config.addAllowedMethod(HttpMethod.POST);
    config.addAllowedMethod(HttpMethod.PUT);
    config.addAllowedMethod(HttpMethod.DELETE);
    config.addAllowedMethod(HttpMethod.OPTIONS);

    val source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
    source.registerCorsConfiguration("/**", config);

    return new CorsWebFilter(source);
  }
}
