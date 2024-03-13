package org.kpaas.sidecar.portal.api.common;

import org.cloudfoundry.reactor.DefaultConnectionContext;
import org.cloudfoundry.reactor.TokenProvider;
import org.cloudfoundry.reactor.client.ReactorCloudFoundryClient;
import org.kpaas.sidecar.portal.api.config.TokenGrantTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Common {
    @Value("${sidecar.apiHost}")
    public String apiHost;

    @Value("${sidecar.tokenKind}")
    public String tokenKind;

    @Value("${sidecar.token}")
    public String token;


    @Bean
    public ReactorCloudFoundryClient cloudFoundryClient(TokenProvider tokenProvider) {
        DefaultConnectionContext defaultConnectionContext = DefaultConnectionContext.builder().apiHost(apiHost).skipSslValidation(true).build();
        return ReactorCloudFoundryClient.builder()
                .connectionContext(defaultConnectionContext)
                .tokenProvider(tokenProvider)
                .build();
    }

    @Bean
    public TokenProvider tokenProvider() {
        String tokenText = tokenKind + " " + token;
        TokenProvider tokenProvider = new TokenGrantTokenProvider(tokenText);
        return tokenProvider;
    }

    public TokenProvider tokenProvider(String token) {
        return tokenProvider();
    }

    /*
    * List<String> 이 null이면 객체 선언 후 리턴
    */
    public List<String> stringListNullCheck(List<String> elements){
        if ( elements == null || elements.isEmpty() ){
            elements = new ArrayList<>();
        }
        return elements;
    }

    /*
     * List<Integer> 이 null이면 객체 선언 후 리턴
     */
    public List<Integer> integerListNullCheck(List<Integer> elements){
        if ( elements == null || elements.isEmpty() ){
            elements = new ArrayList<>();
        }
        return elements;
    }

    /*
     * String 이 null이면 객체 선언 후 리턴
     */
    public String stringNullCheck(String elements){
        if ( elements == null || elements.isEmpty() ){
            elements = "";
        }
        return elements;
    }
}
