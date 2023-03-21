package com.cgi.sophos.client;

import com.azure.identity.OnBehalfOfCredential;
import com.azure.identity.OnBehalfOfCredentialBuilder;
import com.cgi.sophos.exception.UnauthorizedException;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GraphClientBuilder {

  @Value("${spring.cloud.azure.active-directory.profile.tenant-id}")
  private String tenantId;

  @Value("${spring.cloud.azure.active-directory.credential.client-id}")
  private String clientId;

  @Value("${spring.cloud.azure.active-directory.credential.client-secret}")
  private String clientSecret;

  @Value(
      "#{'${spring.cloud.azure.active-directory.authorization-clients.graph.scopes}'.split(',')}")
  private List<String> scopes;

  @Value("${auth.header-name}")
  private String authHeaderName;

  public com.microsoft.graph.requests.GraphServiceClient<Request> getClient(HttpServletRequest request) {
    String authToken = getTokenFromHeader(request);
    if (StringUtils.isEmpty(authToken)) {
      log.error("No valid Authorization header present");
      throw new UnauthorizedException("No Authorization header present");
    }

    OnBehalfOfCredential onBehalfOfCredential =
        new OnBehalfOfCredentialBuilder()
            .tenantId(this.tenantId)
            .clientId(this.clientId)
            .clientSecret(this.clientSecret)
            .userAssertion(authToken)
            .build();
    TokenCredentialAuthProvider tokenCredentialAuthProvider =
        new TokenCredentialAuthProvider(scopes, onBehalfOfCredential);

    return com.microsoft.graph.requests.GraphServiceClient.builder()
        .authenticationProvider(tokenCredentialAuthProvider)
        .buildClient();
  }

  String getTokenFromHeader(HttpServletRequest request) {
    var authHeader = request.getHeader(authHeaderName);

    // Enable request Auth header to contain Bearer or bearer
    if (!StringUtils.isEmpty(authHeader)) {
      if (authHeader.startsWith("Bearer ")) {
        return authHeader.replace("Bearer", "").trim();
      } else if (authHeader.startsWith("bearer ")) {
        return authHeader.replace("bearer", "").trim();
      }
    }
    return null;
  }
}
