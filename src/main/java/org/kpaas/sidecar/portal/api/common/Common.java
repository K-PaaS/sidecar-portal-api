package org.kpaas.sidecar.portal.api.common;

import org.cloudfoundry.reactor.DefaultConnectionContext;
import org.cloudfoundry.reactor.TokenProvider;
import org.cloudfoundry.reactor.client.ReactorCloudFoundryClient;
import org.kpaas.sidecar.portal.api.config.TokenGrantTokenProvider;
import org.kpaas.sidecar.portal.api.login.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Common {
    @Value("${sidecar.apiHost}")
    public String apiHost;

    @Value("${sidecar.tokenKind}")
    public String tokenKind;

    public DefaultConnectionContext defaultConnectionContext;

    @Autowired
    @Qualifier("authUtil")
    protected AuthUtil authUtil;

    public ReactorCloudFoundryClient cloudFoundryClient(TokenProvider tokenProvider) {
        defaultConnectionContext = DefaultConnectionContext.builder().apiHost(apiHost).skipSslValidation(true).build();
        return ReactorCloudFoundryClient.builder()
                .connectionContext(defaultConnectionContext)
                .tokenProvider(tokenProvider)
                .build();
    }

    public TokenProvider tokenProvider() {
        return tokenProvider(authUtil.sidecarAuth().getClusterToken());
    }

    public TokenProvider tokenProvider(String token) {
        String tokenText = tokenKind + " " + token;
        TokenProvider tokenProvider = new TokenGrantTokenProvider(tokenText);
        return tokenProvider;
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

    public String stringNullChecks(String elements){
        if ( elements == null || elements.isEmpty() ){
            elements = "";
        }
        return elements;
    }

    class CustomClass <E> {
        private E element;

        void set(E element) {
            this.element = element;
        }

        E get() {
            return element;
        }
    }
}
