package com.speer.notes.controllers;
import com.speer.notes.exception.RateLimitExceededException;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class RateLimitAspect {

    private final Map<String, Long> lastRequestTimeMap = new ConcurrentHashMap<>();

    @Before("@annotation(rateLimited)")
    public void checkRateLimit(JoinPoint joinPoint, RateLimited rateLimited) {
        HttpServletRequest request = getRequest(joinPoint);

        if (request != null) {
            String ipAddress = request.getRemoteAddr();

            if (!isAllowed(ipAddress, rateLimited.value())) {
                throw new RateLimitExceededException("Rate limit exceeded.");
            }
        }
    }

    private boolean isAllowed(String ipAddress, int rateLimit) {
        long currentTime = System.currentTimeMillis();
        long lastRequestTime = lastRequestTimeMap.getOrDefault(ipAddress, 0L);

        if (currentTime - lastRequestTime > rateLimit * 1000) {
            lastRequestTimeMap.put(ipAddress, currentTime);
            return true;
        }

        return false; // Rate limit exceeded
    }

    private HttpServletRequest getRequest(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest) {
                return (HttpServletRequest) arg;
            }
        }
        return null;
    }
}

