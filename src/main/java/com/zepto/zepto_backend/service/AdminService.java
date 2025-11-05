package com.zepto.zepto_backend.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zepto.zepto_backend.dtos.AdminRequestBody;
import com.zepto.zepto_backend.enums.UserType;
import com.zepto.zepto_backend.exception.UnauthorizedException;
import com.zepto.zepto_backend.exception.UserNotFoundException;
import com.zepto.zepto_backend.model.User;
import com.zepto.zepto_backend.utility.MappingUtility;

@Service
public class AdminService {
  @Autowired
  UserService userService;

  @Autowired
  MappingUtility mappingUtility;

  @Autowired  
  MailService mailService;

  @Autowired
  InvitationStatus invitationStatus;

  public void inviteAdmin(AdminRequestBody adminRequestBody, UUID userId){
    //First Map the details and call save function

    User admin = mappingUtility.mapAdminToUser(adminRequestBody, com.zepto.zepto_backend.enums.UserType.ZEPTO_APP_ADMIN.toString());
    admin = userService.saveUser(admin);

    User maint = userService.getUserById(userId);

    if(maint == null){
      throw new UserNotFoundException(String.format("User with id %s not found", userId.toString()));
    }
    if(!userService.isMaint(maint)){
      throw new UnauthorizedException(String.format("User with id %s is not authorized to perform this operation",userId.toString()));
    }

    mailService.sendInviteMailToAdmin(admin, UserType.ZEPTO_APP_ADMIN.toString(), admin.getUsername());
  }

  public void adminAcceptStatus(UUID userId){
    invitationStatus.updateAcceptStatus(userId);
  }

  public void adminRejectStatus(UUID userId){
    invitationStatus.updateRejectStatus(userId);
  }
}
