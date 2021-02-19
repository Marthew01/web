package com.web.admin.config;

import com.web.admin.interceptor.LoginInterceptors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhang-rongyao
 * @version V 1.0
 * @Package com.fapiao.layui.config
 * @date 2021/1/6/006 9:38
 */
@Configuration
public class MyWebMvcConfigurer {

    @Autowired
    LoginInterceptors loginInterceptors;

    /**
     * 资源映射器和拦截器
     * @return
     */
    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer(){
            //拦截器
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                InterceptorRegistration loginRegistry = registry.addInterceptor(loginInterceptors);
                loginRegistry
                        //拦截路径
                        .addPathPatterns("/**")
                        //排序拦截路径
                        .addPathPatterns("/authc/**")
                        //放行请求静态资源请求
                        .excludePathPatterns(Arrays.asList("/","/login","/static/**","/webjars/**"));
            }

            //资源映射
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {

                /**
                 * http://localhost:8088/index.html  ==> /templates/footerbar.html
                 * http://localhost:8088/css/starter.css  ==> /static/css/starter.css
                 * 允许访问静态资源
                 */
                registry
                        //请求拦截路径
                        .addResourceHandler("/**")
                        //真实地址
                        .addResourceLocations("classpath:/static/")
                        .addResourceLocations("classpath:/templates/");
            }

            /**
             * 允许跨域
             */
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("*")
                        .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH")
                        .allowCredentials(true).maxAge(3600);
            }

            /**
             * 视图控制
             * @param registry
             */
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {

                registry.addViewController("/logout").setViewName("forward:/login");
                //无权限访问页面
                registry.addViewController("/403").setViewName("/common/403");

                //设置ViewController的优先级,将此处的优先级设为最高,当存在相同映射时依然优先执行
                registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
            }


            @Override
            public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
                // 注册Spring data jpa pageable的参数分解器
                resolvers.add(new PageableHandlerMethodArgumentResolver());
            }

        };
    }



}
