package com.nmt.smilekay.config;

import com.nmt.smilekay.utils.BeanValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.client.RestTemplate;

import javax.validation.Validator;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author mingtao.ni
 */
@Component
public class SkBean {
    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public BeanValidator beanValidator() {
        BeanValidator beanValidator = new BeanValidator();
        beanValidator.setValidator(validator());
        return beanValidator;
    }

    /**
     * 解决中文乱码问题
     *
     * @return RestTemplate
     */
    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> list = restTemplate.getMessageConverters();
        for (HttpMessageConverter<?> httpMessageConverter : list) {
            if (httpMessageConverter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) httpMessageConverter).setDefaultCharset(Charset.forName("UTF-8"));
            }
        }
        return restTemplate;
    }
}
