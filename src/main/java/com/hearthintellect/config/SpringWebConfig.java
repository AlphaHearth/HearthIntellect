package com.hearthintellect.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hearthintellect.json.*;
import com.hearthintellect.model.Mechanic;
import com.hearthintellect.model.Patch;
import com.hearthintellect.security.MD5TokenIDGenerator;
import com.hearthintellect.security.PasswordEncoder;
import com.hearthintellect.security.SaltedMD5Encoder;
import com.hearthintellect.security.TokenIDGenerator;
import com.hearthintellect.utils.LocaleString;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Spring configuration class for REST controllers.
 */
@EnableWebMvc
@Configuration
@ComponentScan("com.hearthintellect.controller")
public class SpringWebConfig extends WebMvcConfigurerAdapter {

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(LocaleString.class, new LocaleStringTypeAdapter())
                .registerTypeAdapter(Mechanic.class, new MechanicDeserializer())
                .registerTypeAdapter(Patch.class, new PatchDeserializer())
                .setPrettyPrinting()
                .create();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new SaltedMD5Encoder();
    }

    @Bean
    public TokenIDGenerator tokenIDGenerator() {
        return new MD5TokenIDGenerator();
    }

    @Bean
    public GsonHttpMessageConverter gsonHttpMessageConverter(Gson gson) {
        GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
        converter.setGson(gson);
        return converter;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(gsonHttpMessageConverter(gson()));
        super.configureMessageConverters(converters);
    }
}
