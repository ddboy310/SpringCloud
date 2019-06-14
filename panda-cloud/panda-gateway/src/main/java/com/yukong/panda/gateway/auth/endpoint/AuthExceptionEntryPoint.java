package com.yukong.panda.gateway.auth.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yukong.panda.common.enums.ResponseCodeEnum;
import com.yukong.panda.common.util.ApiResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: yukong
 * @date: 2018/10/12 10:31
 * @description: 自定义AuthExceptionEntryPoint用于tokan校验失败返回信息
 */
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException)
            throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        ApiResult<String> result = new ApiResult<>(authException, ResponseCodeEnum.PERMISSION_DEFINED);
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
