package metanet.springboot.restapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                //client_id
                .withClient("myApp")
                //client_secret
                .secret(this.passwordEncoder.encode("pass"))
                //password - password cridential grant 방식, refresh_token - access token 갱신할때
                .authorizedGrantTypes("password", "refresh_token")
                //접근범위
                .scopes("read", "write")
                //access token 만료시간 600초 이므로 10분
                .accessTokenValiditySeconds(10 * 60)
                //refresh token 만료시간 3600초 이므로 1시간
                .refreshTokenValiditySeconds(6 * 10 * 60);
    }


}
