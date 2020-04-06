package com.dbs.apigateway.service.impl;

import com.dbs.apigateway.entity.GatewayInfo;
import com.dbs.apigateway.repository.GatewayInfoRepository;
import com.dbs.apigateway.service.GatewayService;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GatewayServiceImpl implements GatewayService {

  @Autowired
  GatewayInfoRepository gatewayInfoRepository;

  @Override
  public void saveGatewayRequest(final String requestId, final String url) {
    final Optional<GatewayInfo> optionalGatewayInfo = this.gatewayInfoRepository
        .getAllByRequestId(requestId);
    if(optionalGatewayInfo.isPresent()){
      return;
    }
    GatewayInfo gatewayInfo = new GatewayInfo();
    gatewayInfo.setUrl(url);
    gatewayInfo.setRequestId(requestId);
    gatewayInfo.setCreatedAt(new Date());
    this.gatewayInfoRepository.save(gatewayInfo);
  }
}
