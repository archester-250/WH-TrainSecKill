package com.pku.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pku.interceptor.JWTUserInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
@Slf4j
public class WebMVCConfiguration extends WebMvcConfigurationSupport {
    @Autowired
    private JWTUserInterceptor jwtUserInterceptor;
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtUserInterceptor)
                .addPathPatterns("/user/**")
                .excludePathPatterns("/user/user/register", "/user/user/login", "/user/user/info", "/public-key", "/user/seckill/sec", "/user/admin/**");
    }
    //"/user/seckill/sec"
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(new ObjectMapper());
        converters.add(converter);
    }
}
