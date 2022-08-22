package com.matching.project.config;

import com.matching.project.entity.User;
import com.matching.project.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtTokenService jwtTokenService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authorization = ((HttpServletRequest)request).getHeader("Authorization");
        // Authorized Bearer Token
        if (authorization != null)
        {
            if (Pattern.matches("^Bearer .*", authorization)) {
                String token = authorization.replaceAll("^Bearer( )*", "");
                //토근의 존재 유무, 토큰 유효성 체크, 토큰 유효기간 체크
                if (jwtTokenService.verifyToken(token)) {
                    Authentication auth = jwtTokenService.getAuthentication(token);
                    User user = (User)auth.getPrincipal();
                    if (user.isWithdrawal()) {
                        log.error("{} : Withdrawal User", user.getEmail());
                    } else if (user.isBlock()) {
                        log.error("{} : Blocked User", user.getEmail());
                    } else {
                        SecurityContextHolder.getContext().setAuthentication(auth);
                        log.info("{} : Valid Jwt Token", user.getEmail());
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }
}