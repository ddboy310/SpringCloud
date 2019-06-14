package com.yukong.panda.auth.handler;

import com.yukong.panda.auth.exception.CustomOauth2Exception;
import com.yukong.panda.common.constants.MqQueueNameConstant;
import com.yukong.panda.common.constants.PandaServiceNameConstants;
import com.yukong.panda.common.dto.SysLogDTO;
import com.yukong.panda.common.enums.OperationStatusEnum;
import com.yukong.panda.common.enums.SysLogTypeEnum;
import com.yukong.panda.common.util.UrlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: yukong
 * @date: 2018/10/12 10:25
 * @description: oauth2异常处理器
 */

@Component("customWebResponseExceptionTranslator")
@Slf4j
public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String username = request.getParameter("username");
        SysLogDTO sysLogDTO = new SysLogDTO();
        sysLogDTO
                .setCreateBy(username)
                .setRequestUri(request.getRequestURI())
                .setUserAgent(UrlUtil.getRemoteHost(request))
                .setType(SysLogTypeEnum.LOGIN.getCode())
                .setStatus(OperationStatusEnum.FAIL.getCode())
                .setModuleName("auth认证模块")
                .setActionName("登录")
                .setServiceId(PandaServiceNameConstants.PANDA_AUTH)
                .setRemoteAddr(UrlUtil.getRemoteHost(request))
                .setMethod(request.getMethod())
                .setException(UrlUtil.getTrace(e));
        rabbitTemplate.convertAndSend(MqQueueNameConstant.SYS_LOG_QUEUE, sysLogDTO);
        log.error(e.getStackTrace().toString());
        OAuth2Exception oAuth2Exception = (OAuth2Exception) e;
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new CustomOauth2Exception(oAuth2Exception.getMessage()));
    }
}
