package com.dbs.apigateway.service;
import org.springframework.stereotype.Service;


public interface GatewayService {


  /**
   * Method to save gateway url information
   *
   * @param id id of request
   * @param url url of request
   */
  void saveGatewayRequest(final String id, final String url);
}
