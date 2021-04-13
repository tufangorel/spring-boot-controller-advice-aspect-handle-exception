package com.company.customerinfo.config.aspect;


import com.company.customerinfo.model.Log;
import com.company.customerinfo.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Autowired
    private LogService logService;

    @Around("execution(* com.company.customerinfo.service.*.*(..))")
    public Object analyzeMethodExecutionTimes(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
    {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        //Get intercepted method details
        String insidePackage = methodSignature.getDeclaringType().getPackageName();
        String insideClass = methodSignature.getDeclaringType().getSimpleName();
        String method = methodSignature.getName();

        final StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();

        LOGGER.info("Total time for method execution " + insidePackage + "."+ insideClass + "." + method + " "
                    + "took --> " + stopWatch.getTotalTimeMillis() + " ms");

        return result;
    }

    @Before("execution(* com.company.customerinfo.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {

        // Log method name
        LOGGER.info("Invoke method: " + joinPoint.getSignature().toLongString());

        // Log arguments
        if(joinPoint.getArgs() != null) {
            for(Object obj : joinPoint.getArgs()) {
                LOGGER.info("Argument: " + obj.toString());
            }
        }

    }

    @AfterThrowing(pointcut = "execution(* com.company.customerinfo.service.*.*(..))", throwing = "ex")
    public void logAfterThrowingServiceLayer(JoinPoint joinPoint, Throwable ex) {
        // Log the exception message
        LOGGER.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), ex.getCause() != null ? ex.getCause() : "NULL");

        Log log = new Log();
        log.setMessage(ex.getCause().toString());
        logService.save(log);
    }

}
