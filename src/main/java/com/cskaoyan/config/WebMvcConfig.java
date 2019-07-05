package com.cskaoyan.config;

import com.cskaoyan.converter.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    DateConverter dateConverter;
    @Autowired
    ConfigurableConversionService configurableConversionService;
    public void addConverter(){
        configurableConversionService.addConverter(dateConverter);
    }

}
