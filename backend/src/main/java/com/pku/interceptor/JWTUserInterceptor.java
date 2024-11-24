package com.pku.interceptor;

import com.pku.constant.JwtClaimsConstant;
import com.pku.context.BaseContext;
import com.pku.properties.JwtProperties;
import com.pku.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class JWTUserInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtProperties jwtProperties;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("用户拦截器生效");
        if(!(handler instanceof HandlerMethod)){return true;}
        String token = request.getHeader("token");
        log.info("token: " + token);
        try {
            Jws<Claims> claimsJws = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
            int userId = (Integer)claimsJws.getPayload().get(JwtClaimsConstant.USER_ID);
            log.info("userId: " + userId);
            BaseContext.setCurrentId(userId);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        BaseContext.removeCurrentId();
    }
}
