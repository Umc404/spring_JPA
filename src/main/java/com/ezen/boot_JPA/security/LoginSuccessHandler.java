package com.ezen.boot_JPA.security;

import com.ezen.boot_JPA.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Getter
    @Setter
    private String authUrl;

    @Getter
    @Setter
    private String authEmail;

    // 성공 후 가야하는 경로 설정(객체)
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    // request 객체의 저장공간 : 직전 갔던 경로 저장
    private RequestCache requestCache = new HttpSessionRequestCache();

    @Autowired
    public UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        setAuthEmail(authentication.getName());
        setAuthUrl("/board/list");

        boolean isOk = userService.updateLastLogin(getAuthEmail());

        HttpSession ses = request.getSession();
        if(!isOk || ses == null) {
            return;
        } else {
            ses.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        log.info(">>> savedRequest > {}",savedRequest);
        redirectStrategy.sendRedirect(request, response,
                ( savedRequest != null ? savedRequest.getRedirectUrl() : getAuthUrl() ) );
    }
}
