package com.example.o.config;


import com.example.o.model.Authority;
import com.example.o.repository.ConsumerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomTokenEnhancer implements TokenEnhancer {

    @Autowired
    private com.example.o.repository.ConsumerRepository ConsumerRepository;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        User oauthConsumer = (User) authentication.getPrincipal();
        final Map<String, Object> additionalInfo = new HashMap<>();


        com.example.o.model.Consumer consumer = ConsumerRepository.getByUsername(oauthConsumer.getUsername());

        if (consumer != null) {
            additionalInfo.put("id", consumer.getId());
            additionalInfo.put("first_name", consumer.getFirst_name());
            additionalInfo.put("last_name", consumer.getLast_name());
            additionalInfo.put("username", consumer.getUsername());
            HashMap<Integer, String> roles = new HashMap<>();
            for (Authority authority : consumer.getAuthorities()) {
                roles.put(authority.getId(), authority.getName());
            }
            additionalInfo.put("roles", roles);

            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        }


        return accessToken;
    }

}