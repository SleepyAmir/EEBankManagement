package com.sleepy.eebankmanagement.utils.interceptors;


import com.sleepy.eebankmanagement.utils.interceptors.annotations.Loggable;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.ws.rs.NameBinding;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Loggable
//@NameBinding
@Interceptor
public class LoggableInterceptor {
    @AroundInvoke
    public Object aroundInvoke(final InvocationContext invocationContext) throws Throwable {
        String  className = invocationContext.getTarget().getClass().getName();
        String methodName = invocationContext.getMethod().getName();
//        if(className.startsWith("com.mftplus.ee09.controller")){}

        log.info("Class : {} - Method {} started ", className, methodName);
        try {
            Object returnValue = invocationContext.proceed();
            log.info("Class : {} - Method {} finished ", className, methodName);
            return  returnValue;
//            return  new ApiResponse(200, returnValue);
        }catch (Exception e){
            log.error("Class : {} - Method {} throws {} ", className, methodName, e.getMessage());
//            return new ApiResponse(500, e.getMessage());
            return null;
        }
    }
}
