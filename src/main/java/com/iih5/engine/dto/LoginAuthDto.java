/**
 * ---------------------------------------------------------------------------
 * 类名称   ：LoginAuthDto
 * 类描述   ：
 * 创建人   ： xue.yi
 * 创建时间： 2016/10/31 13:53
 * 版权拥有：星电商科技
 * ---------------------------------------------------------------------------
 */
package com.iih5.engine.dto;

import java.io.Serializable;

public class LoginAuthDto {
    private Integer loginType;
    private String name;
    private String pwd;
    private String token;

    public Integer getLoginType() {
        return loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
