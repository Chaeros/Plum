package com.springboot.plum.config.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

// 예제 13.17
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;


    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest,
                                    HttpServletResponse servletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        System.out.println("tt="+jwtTokenProvider.resolveToken(servletRequest));
        String jwt =servletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println("jwt="+jwt);
        System.out.println(servletRequest.getHeader("Authorization"));
        String token = jwtTokenProvider.resolveToken(servletRequest);
        LOGGER.info("[doFilterInternal] token 값 추출 완료. token : {}", token);
        System.out.println(token);

        LOGGER.info("[doFilterInternal] token 값 유효성 체크 시작");
        if (token != null && jwtTokenProvider.validateToken(token)) {
            String key = "JWT_TOKEN:" + jwtTokenProvider.getUsername(token);
            String storedToken = redisTemplate.opsForValue().get(key);

            //**로그인 여부 체크**
            if(redisTemplate.hasKey(key) && storedToken != null) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                LOGGER.info("[doFilterInternal] token 값 유효성 체크 완료");
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getJwtFromRequest(HttpServletRequest servletRequest) {
        String bearerToken = servletRequest.getHeader("Authorization");
        return null;
    }
}