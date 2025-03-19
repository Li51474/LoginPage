package com.hati.aiformat.interceptors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hati.aiformat.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.获取请求头中的token
        String path = request.getRequestURI();
        if (path.contains("/user/login") || path.contains("/user/register") || path.contains("/article/view")) {
            return true; // 不拦截这些路径
        }
        String token = request.getHeader("Authorization");

        //2.判断token是否存在
        if (!StringUtils.hasLength(token) || !token.startsWith("Bearer ")) {
            response.setStatus(401);
            return false;
        }

        //3.解析token
        token = token.substring(7); // 去掉Bearer
        try {
            // 处理token是JSON格式的情况
            if (token.startsWith("{")) {
                JsonNode jsonNode = objectMapper.readTree(token);
                token = jsonNode.get("token").asText();
            }

            Map<String, Object> claims = jwtUtil.getClaimsFromToken(token);
            //4.验证token是否存在于redis
            String redisToken = stringRedisTemplate.opsForValue().get(token);
            if (redisToken == null) {
                response.setStatus(401);
                return false;
            }

            //5.把用户信息存入request
            request.setAttribute("user_id", claims.get("id"));
            request.setAttribute("username", claims.get("username"));
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }
    }
} 