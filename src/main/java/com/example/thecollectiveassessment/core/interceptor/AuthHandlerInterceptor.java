package com.example.thecollectiveassessment.core.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthHandlerInterceptor implements HandlerInterceptor {

    private final String SECRET_KEY_VALUE = "secret-key-value";

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception arg3) throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView model) throws Exception {
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        if(request.getRequestURI().contains("swagger")){
            return true;
        }

        if (request.getHeader("secret-key") == null || !request.getHeader("secret-key").equals(SECRET_KEY_VALUE)) {
            response.getWriter().write("Unauthorized");
            response.setStatus(401);
            return false;
        }
        return true;
    }
}
