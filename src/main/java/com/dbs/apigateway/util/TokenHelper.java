package com.dbs.apigateway.util;

import com.dbs.apigateway.constants.ApiConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import java.nio.charset.Charset;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TokenHelper {

  public static boolean decode(final String token) {
    try {
      final Jws<Claims> claimsJws = Jwts.parser()
          .setSigningKey(ApiConstants.SECRET.getBytes(Charset.forName("UTF-8")))
          .parseClaimsJws(token);
      final String username = (String) claimsJws.getBody().get(ApiConstants.USERNAME);
      return true;
    } catch (Exception e) {
      log.warn("Unable to get details from the token");
      return false;
    }
  }
}
