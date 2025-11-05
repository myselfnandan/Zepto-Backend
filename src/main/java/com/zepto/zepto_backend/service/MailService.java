package com.zepto.zepto_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.zepto.zepto_backend.enums.UserType;
import com.zepto.zepto_backend.model.User;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j //to log the error
@Service
public class MailService {

  @Autowired
  JavaMailSender javaMailSender;

  @Autowired
  TemplateEngine templateEngine;//loads the html content

  public void sendInviteMailToAdmin(User appAdmin, String role, String invitedUser){
    Context context = new Context();
    context.setVariable("appName", "Zepto");
    context.setVariable("adminName", appAdmin.getUsername());
    context.setVariable("maintName", invitedUser);
    context.setVariable("role", role);
    if(role.equals(com.zepto.zepto_backend.enums.UserType.ZEPTO_APP_ADMIN.toString())){
      context.setVariable("acceptUrl", "http://localhost:7718/api/v1/admin/invitation/accept/"+ appAdmin.getId());
      context.setVariable("rejectUrl", "http://localhost:7718/api/v1/admin/invitation/reject/"+ appAdmin.getId());
    }else{
      context.setVariable("acceptUrl", "http://localhost:7718/api/v2/warehouseadmin/invitation/accept/"+ appAdmin.getId());
      context.setVariable("rejectUrl", "http://localhost:7718/api/v2/warehouseadmin/invitation/reject/"+ appAdmin.getId());
    }
    context.setVariable("expiryDate", "10/11/2025");

    String htmlCode = templateEngine.process("invite-app-admin", context);

    //now set the html templete as mail body : we use mime message

    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

    try{
      mimeMessageHelper.setText(htmlCode, true);
      mimeMessageHelper.setTo(appAdmin.getEmail());
      if(role.equals(UserType.WAREHOUSE_ADMIN.toString())){
        mimeMessageHelper.setSubject("Zepto Ware House Admin Role Invitation");
      }else{
        mimeMessageHelper.setSubject("Zepto Admin Role Invitation");
      }
      javaMailSender.send(mimeMessage);
    }catch(Exception error){
      log.error(error.getMessage());
    }
    
  }
  
}
