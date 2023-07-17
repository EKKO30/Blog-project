package com.fw.aspect;

import com.alibaba.fastjson.JSON;
import com.fw.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

//切面类
@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(com.fw.annotation.SystemLog)")
    public void pt(){}


    /*
     *打印日志
     * ProceedingJoinPoint可以视作切入的方法
     */
    @Around("pt()")
    public Object printLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object ret;

        try {
            handlerBefore(proceedingJoinPoint);
            //执行方法的返回值
            ret = proceedingJoinPoint.proceed();
            handlerAfter(ret);
        } finally {
            // 结束后换行
            log.info("=======End=======" + System.lineSeparator());
        }
        return ret;
    }

    private void handlerAfter(Object ret) {
        // 打印出参
        log.info("Response       : {}", JSON.toJSONString(ret));
    }

    //方法执行前打印相关日志信息
    private void handlerBefore(ProceedingJoinPoint joinPoint) {

        //获取相关请求信息
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        //获得调用接口的注解中的备注信息
        SystemLog systemLog=getSystemLog(joinPoint) ;

        //返回当前请求的协议名称，例如HTTP或HTTPS
        String protocol = request.getScheme();
        // 获取主机名
        String host = request.getServerName();
        // 获取端口号
        int port = request.getServerPort();
        // 获取请求路径
        String path = request.getRequestURI();
        // 构建完整的接口访问路径
        String url = protocol + "://" + host + ":" + port + path;

        log.info("=======Start=======");
        // 打印请求 URL
        //log.info("URL            : {}",request.getRequestURI());   /link/getAllLink
        log.info("URL            : {}",url);//http://localhost:7777/link/getAllLink
        // 打印描述信息
        log.info("BusinessName   : {}",systemLog.Remark());
        // 打印 Http method
        log.info("HTTP Method    : {}",request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}",joinPoint.getSignature().getDeclaringTypeName(),joinPoint.getSignature().getName());
        // 打印请求的 IP
        log.info("IP             : {}",request.getRemoteHost());
        // 打印请求入参
        log.info("Request Args   : {}", JSON.toJSONString(joinPoint.getArgs()));
    }

    //获得调用接口的注解中的备注信息
    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        //将这个方法当作是一个对象
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //从对象中获得相关属性
        SystemLog annotation = signature.getMethod().getAnnotation(SystemLog.class);
        return annotation;
    }


}
