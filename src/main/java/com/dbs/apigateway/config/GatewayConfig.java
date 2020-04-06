package com.dbs.apigateway.config;


import static com.dbs.apigateway.constants.ApiConstants.RETRY_COUNT;
import static com.dbs.apigateway.constants.ApiConstants.ROUTE_PATH;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

@Configuration
public class GatewayConfig {

  @Autowired
  CustomFilter customFilter;

  @Bean
  public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
    return builder.routes().route(r -> r.path(ROUTE_PATH).filters(f -> {
      f.filter(customFilter);
      f.retry(x -> {
        x.setMethods(HttpMethod.GET, HttpMethod.POST, HttpMethod.PATCH, HttpMethod.DELETE);
        x.setRetries(RETRY_COUNT);
        x.setExceptions(Throwable.class);
        x.setStatuses(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.NOT_FOUND,
            HttpStatus.REQUEST_TIMEOUT);
      });
      return f;
    }).uri("no://op")).build();
  }
}
