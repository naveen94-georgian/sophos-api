package com.cgi.sophos.common.config;

import com.azure.identity.OnBehalfOfCredential;
import com.azure.identity.OnBehalfOfCredentialBuilder;
import com.cgi.sophos.common.config.security.AuthenticationFacade;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.requests.GraphServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LocalGraphClient {

    private final AuthenticationFacade authenticationFacade;

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

    private GraphServiceClient<Request> graphClient = null;

    public GraphServiceClient<Request> getClient() {
        if (graphClient != null) {
            return graphClient;
        }

        OnBehalfOfCredential onBehalfOfCredential =
                new OnBehalfOfCredentialBuilder()
                        .tenantId(this.tenantId)
                        .clientId(this.clientId)
                        .clientSecret(this.clientSecret)
                        .userAssertion(authenticationFacade.getAuthenticationToken())
                        .build();
        TokenCredentialAuthProvider tokenCredentialAuthProvider =
                new TokenCredentialAuthProvider(scopes, onBehalfOfCredential);

        graphClient = com.microsoft.graph.requests.GraphServiceClient.builder()
                .authenticationProvider(tokenCredentialAuthProvider)
                .buildClient();
        return graphClient;
    }
}
