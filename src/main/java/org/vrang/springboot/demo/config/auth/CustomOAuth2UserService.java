package org.vrang.springboot.demo.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.vrang.springboot.demo.config.auth.dto.OAuthAttributes;
import org.vrang.springboot.demo.config.auth.dto.SessionUser;
import org.vrang.springboot.demo.web.domain.user.User;
import org.vrang.springboot.demo.web.domain.user.UserRepository;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>
{
    private final HttpSession httpSession;
    
    private final UserRepository userRepository;
    
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException
    {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService( );
        
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        
        String registrationId = userRequest.getClientRegistration( )
                                           .getRegistrationId( );
        String userNameAttributeName = userRequest.getClientRegistration( )
                                                  .getProviderDetails( )
                                                  .getUserInfoEndpoint( )
                                                  .getUserNameAttributeName( );
        Map<String, Object> attributes      = oAuth2User.getAttributes( );
        OAuthAttributes     oAuthAttributes = OAuthAttributes.of(registrationId, userNameAttributeName, attributes);
        
        User user = saveOrUpdate(oAuthAttributes);
        
        httpSession.setAttribute("user", new SessionUser(user));
        
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey( ))),
                oAuthAttributes.getAttributes( ), oAuthAttributes.getNameAttributeKey( ));
    }
    
    private User saveOrUpdate(OAuthAttributes attributes)
    {
        User user = userRepository.findByEmail(attributes.getEmail( ))
                                  .map(entity -> entity.update(attributes.getName( ), attributes.getPicture( )))
                                  .orElse(attributes.toEntity( ));
        
        return userRepository.save(user);
    }
}
