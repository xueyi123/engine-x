/**
 * ---------------------------------------------------------------------------
 * 类名称   ：BaseVo
 * 类描述   ：
 * 创建人   ： xue.yi
 * 创建时间： 2016/10/31 13:57
 * 版权拥有：星电商科技
 * ---------------------------------------------------------------------------
 */
package com.iih5.engine.dto;

import com.iih5.engine.constant.ErrorCode;

import java.io.Serializable;

public class BaseVo implements Serializable{
    private String msg = "OK";
    private Integer code = ErrorCode.SUCCESS;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String toSerialize(){
        return "{\"msg\":\""+msg+"\",\"code\":"+code+"}";
    }
}
