package metanet.springboot.restapi.config;

import lombok.RequiredArgsConstructor;
import metanet.springboot.restapi.accounts.AccountService;
import metanet.springboot.restapi.common.AppProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenStore tokenStore;
    private final AccountService accountService;
    private final AppProperties appProperties;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(accountService)
                .tokenStore(tokenStore);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                //client_id
                .withClient(appProperties.getClientId())
                //client_secret
                .secret(this.passwordEncoder.encode(appProperties.getClientSecret()))
                //password - password cridential grant ??????, refresh_token - access token ????????????
                .authorizedGrantTypes("password", "refresh_token")
                //????????????
                .scopes("read", "write")
                //access token ???????????? 600??? ????????? 10???
                .accessTokenValiditySeconds(10 * 60)
                //refresh token ???????????? 3600??? ????????? 1??????
                .refreshTokenValiditySeconds(6 * 10 * 60);
    }


}
