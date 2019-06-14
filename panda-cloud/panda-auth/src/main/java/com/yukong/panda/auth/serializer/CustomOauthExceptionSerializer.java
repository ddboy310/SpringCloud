package com.yukong.panda.auth.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.yukong.panda.auth.exception.CustomOauth2Exception;
import com.yukong.panda.common.enums.ApiErrorCodeEnum;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * @author: yukong
 * @date: 2018/10/12 10:19
 * @description: 异常json序列化方式
 */
public class CustomOauthExceptionSerializer extends StdSerializer<CustomOauth2Exception> {
    protected CustomOauthExceptionSerializer() {
        super(CustomOauth2Exception.class);
    }

    @Override
    public  void serialize(CustomOauth2Exception value, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        gen.writeStartObject();
        gen.writeNumberField("code", ApiErrorCodeEnum.AUTHORIZED_FAILED.getCode());
        gen.writeStringField("message", ApiErrorCodeEnum.AUTHORIZED_FAILED.getMsg());
        gen.writeStringField("data", null);
        if (value.getAdditionalInformation()!=null) {
            for (Map.Entry<String, String> entry : value.getAdditionalInformation().entrySet()) {
                String key = entry.getKey();
                String add = entry.getValue();
                gen.writeStringField(key, add);
            }
        }
        gen.writeEndObject();
    }
}
