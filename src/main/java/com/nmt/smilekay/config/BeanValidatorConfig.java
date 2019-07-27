package com.nmt.smilekay.config;

import com.nmt.smilekay.utils.BeanValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;

@Configuration
public class BeanValidatorConfig {
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
}
