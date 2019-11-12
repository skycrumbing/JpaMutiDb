package com.example.jpademo.config.exception;

import com.example.jpademo.config.base.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @ClassName: MessageExceptionAspect
 * @Description: TODO
 * @Author: tantao
 * @CreateDate: 2019/11/12 14:10
 * @Version: 1.0
 */
@Aspect
@Component
public class MessageExceptionAspect {
    private static final Logger log = LoggerFactory.getLogger(MessageExceptionAspect.class);

    public MessageExceptionAspect() {
    }

    @Pointcut("within(@org.springframework.stereotype.Controller *) || within(@org.springframework.web.bind.annotation.RestController *)")
    public void aspect() {
    }

    @Around("aspect()")
    public Object invoke(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();
        } catch (MessageException var4) {
            log.error("service异常", var4);
            return new Result(var4.getCode(), var4.getMessage());
        } catch (Exception var5) {
            log.error("Exception", var5);
            MessageException messageException = this.getExceptionCause(var5.getCause());
            if(messageException != null) {
                return new Result(messageException.getCode(), messageException.getMessage());
            } else {
                throw var5;
            }
        }
    }

    public MessageException getExceptionCause(Throwable th) {
        return th == null?null:(th instanceof MessageException?(MessageException)th:this.getExceptionCause(th.getCause()));
    }
}

