package com.fw.Handler.Security;

import com.alibaba.fastjson.JSON;
import com.fw.entity.ResponseResult;
import com.fw.enums.AppHttpCodeEnum;
import com.fw.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//同一处理授权失败
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        e.printStackTrace();
        ResponseResult result=ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
    }
}
