package com.dbs.apigateway.entity;

import com.dbs.apigateway.constants.EntityConstants;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = EntityConstants.GATEWAY_INFO_TABLE)
@Data
public class GatewayInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = EntityConstants.REQUEST_ID)
  private String requestId;

  @Column(name = EntityConstants.URL)
  private String url;

  @Column(name = EntityConstants.CREATED_AT)
  private Date createdAt;

}
