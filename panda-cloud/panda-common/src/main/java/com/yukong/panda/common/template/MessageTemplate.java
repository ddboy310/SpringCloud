package com.yukong.panda.common.template;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: yukong
 * @date: 2018/11/30 17:15
 * 消息发送基础模板
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageTemplate implements Serializable {

    private static final long serialVersionUID =1133111414L;


    /**
     * 消息通道
     */
    private String channel;

}
