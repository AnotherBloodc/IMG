package com.example.SeetLeft.Interceptor;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

//HandlerInterceptor：Spring MVC框架提供的拦截器接口
public class Loginlnterceptor implements HandlerInterceptor {


    /**
     * 拦截器：带有拦截器的接口被请求访问时，会先经过拦截器，再经过控制器，最后经过视图解析器，返回给前端
     *
     * @param request  接收前端请求对象
     * @param response 响应前端请求对象
     * @param handler  处理器对象
     * @return true：放行，false：拦截
     * @throws Exception 异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("当前请求正在访问带有拦截器的接口");

        return true;
    }
}
