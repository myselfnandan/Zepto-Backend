package com.zepto.zepto_backend.configuration;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;

@Configuration
public class AppConfiguration {
  @Bean
  public JavaMailSender createJavaMailSender(){
    JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();//child class of javamailsender helps to ceate a bean of javamailsender;
    javaMailSender.setHost("smtp.gmail.com");
    javaMailSender.setPort(587);
    javaMailSender.setUsername("myselfnandan04@gmail.com");
    javaMailSender.setPassword("nvidcukwzopfklyf");
    Properties prop = javaMailSender.getJavaMailProperties();
    prop.put("mail.smtp.auth", "true");//connects to the mail
    prop.put("mail.smtp.starttls.enable", "true");//establish secure network 
    return javaMailSender;
  }

  @Bean
  public TemplateEngine createTemplateEngine(){
    return new TemplateEngine();
  }
  
}
