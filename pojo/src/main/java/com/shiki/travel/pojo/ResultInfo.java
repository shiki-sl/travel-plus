package com.shiki.travel.pojo;

import lombok.*;

import java.io.Serializable;

/**
 * @author shiki
 * 用于封装后端返回前端数据对象
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResultInfo implements Serializable {
    /**
     * 后端返回结果正常为true，发生异常返回false
     */
    private boolean flag;
    /**
     * 后端返回结果数据对象
     */
    private Object data;
    /**
     * 发生异常的错误消息
     */
    private String errorMsg;

}
