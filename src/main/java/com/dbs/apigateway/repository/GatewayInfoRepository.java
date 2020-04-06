package com.dbs.apigateway.repository;

import com.dbs.apigateway.entity.GatewayInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatewayInfoRepository extends JpaRepository<GatewayInfo,Long> {

  Optional<GatewayInfo> getAllByRequestId(final String requestId);
}
