package org.vrang.springboot.demo.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.vrang.springboot.demo.config.auth.dto.SessionUser;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver
{
    private final HttpSession httpSession;
    
    @Override
    public boolean supportsParameter(MethodParameter parameter)
    {
        boolean isLoginUserAnnotation = Objects.nonNull(parameter.getParameterAnnotation(LoginUser.class));
        boolean isUserClass           = SessionUser.class.equals(parameter.getParameterType( ));
        return isLoginUserAnnotation && isUserClass;
    }
    
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception
    {
        return httpSession.getAttribute("user");
    }
}
