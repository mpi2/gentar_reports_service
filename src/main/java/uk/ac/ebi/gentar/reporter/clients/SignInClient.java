package uk.ac.ebi.gentar.reporter.clients;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class SignInClient {
    public String getAuthorizationToken(String userName, String password){
        //to get authorization token we need to send a post
        //http://127.0.0.1:8080/auth/signin
        //http://localhost:8080/auth/signin  ???
        RestTemplate signInRestTemplate = new RestTemplate();
        HttpHeaders signinHeaders= new HttpHeaders();
        signinHeaders.setContentType(MediaType.APPLICATION_JSON);
        String body="{\"userName\":\""+userName+"\",\"password\":\""+password+"\"}";
        String signInUrl="http://127.0.0.1:8080/auth/signin";
        Map<String, String> params = new HashMap<>();
        HttpEntity<String> entity = new HttpEntity<>(body, signinHeaders);
        ResponseEntity<SignIn> signInResponse = signInRestTemplate.exchange(signInUrl, HttpMethod.POST, entity,
                new ParameterizedTypeReference<SignIn>() {
                }, params);
        System.out.println("signInresponse="+signInResponse);
        String accessToken=signInResponse.getBody().getAccessToken();
        return accessToken;
    }
}
