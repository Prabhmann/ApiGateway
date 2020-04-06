package com.dbs.apigateway.config;

import com.dbs.apigateway.constants.ApiConstants;
import com.dbs.apigateway.service.GatewayService;
import com.dbs.apigateway.util.TokenHelper;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.RouteToRequestUrlFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Component
@Log4j2
class CustomFilter implements GatewayFilter, Ordered {

  @Autowired
  GatewayService gatewayService;

  @Override
  public int getOrder() {
    return RouteToRequestUrlFilter.ROUTE_TO_URL_FILTER_ORDER + 1;
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

    try {
      final boolean isAuthenticated = this.verifyToken(exchange);
      if (!isAuthenticated) {
        log.debug(ApiConstants.UNAUTHORIZED_USER);
        throw new RuntimeException(ApiConstants.UNAUTHORIZED_USER);
      }
    } catch (IOException e) {
      log.debug(ApiConstants.UNAUTHORIZED_USER);
      throw new RuntimeException(ApiConstants.UNAUTHORIZED_USER);
    }

    final String uriHost = exchange.getRequest().getURI().getPath();
    final String removePattern = uriHost.replace(ApiConstants.REMOVE_PATTERN, StringUtils.EMPTY);
    final String serviceName = removePattern.substring(0, removePattern.indexOf("/"));
    final URI newUri = UriComponentsBuilder.fromUri(exchange.getRequest().getURI())
        .host(serviceName)
        .replacePath(uriHost.replace(ApiConstants.REMOVE_PATTERN + serviceName, StringUtils.EMPTY))
        .port(ApiConstants.SERVICE_PORT).build().toUri();
    exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR,
        newUri);

    try {
      this.gatewayService
          .saveGatewayRequest(exchange.getRequest().getId(), newUri.toURL().toString());
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    return chain.filter(exchange);
  }

  private boolean verifyToken(final ServerWebExchange exchange) throws IOException {
    final HttpHeaders headers = exchange.getRequest().getHeaders();
    if (ObjectUtils.allNotNull(headers.get(ApiConstants.ACCESS_TOKEN),
        headers.get(ApiConstants.ACCESS_TOKEN).get(0))) {
      final String token = headers.get(ApiConstants.ACCESS_TOKEN).get(0);
      return TokenHelper.decode(token);
    } else {
      throw new RuntimeException("User Not Authenticated");
    }

  }
}
