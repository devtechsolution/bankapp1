package org.as.devtechsolution.gatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Aditya Srivastva
 */

@Order(1)
@Component
public class RequestTraceFilter implements GlobalFilter {

 private static final Logger logger = LoggerFactory.getLogger(RequestTraceFilter.class);

 @Autowired
 FilterUtility filterUtility;


 /**
  * This method is a filter that adds a correlation id to the request if one doesn't exist.
  * If a correlation id already exists, it will be logged at the DEBUG level.
  * The correlation id is generated using {@link #generateCorrelationId()}.
  *
  * @param exchange the ServerWebExchange being processed
  * @param chain the GatewayFilterChain
  * @return a Mono that filters the request
  */

 @Override
 public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
  HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
  if (isCorrelationIdPresent(requestHeaders)) {
   logger.debug("bankapp-correlation-id found in RequestTraceFilter : {}",
           filterUtility.getCorrelationId(requestHeaders));
  } else {
   String correlationID = generateCorrelationId();
   exchange = filterUtility.setCorrelationId(exchange, correlationID);
   logger.debug("bankapp-correlation-id generated in RequestTraceFilter : {}", correlationID);
  }
  return chain.filter(exchange);
 }

 /**
  * Checks if the correlation id is present in the request headers.
  * If present, then return true, else return false.
  *
  * @param requestHeaders the HttpHeaders object containing the request headers
  * @return true if the correlation id is present, false otherwise
  */
 private boolean isCorrelationIdPresent(HttpHeaders requestHeaders) {
  if (filterUtility.getCorrelationId(requestHeaders) != null) {
   return true;
  } else {
   return false;
  }
 }

 private String generateCorrelationId() {
  return java.util.UUID.randomUUID().toString();
 }

}
