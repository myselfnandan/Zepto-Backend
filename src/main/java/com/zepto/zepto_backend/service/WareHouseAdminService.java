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
public class WareHouseAdminService {

  @Autowired
  UserService userService;

  @Autowired
  MappingUtility mappingUtility;

  @Autowired
  MailService mailService;

  @Autowired
  InvitationStatus invitationStatus;
  
  public void inviteWareHouseAdmin(AdminRequestBody adminRequestBody, UUID userid){

    //check if user exist or not
    User admin = userService.getUserById(userid);

    if(admin == null){
      throw new UserNotFoundException(String.format("User with id %s dosen't exist", userid.toString()));
    }

    if(!userService.isMaint(admin) && !userService.isAdmin(admin)){
      throw new UnauthorizedException(String.format("User with ID %s dosen't have access to perform this operation", userid.toString()));
    }

    //mapping utilities 
    User warehouseadmin = mappingUtility.mapAdminToUser(adminRequestBody, UserType.WAREHOUSE_ADMIN.toString());
    userService.saveUser(warehouseadmin); //save the warehouse admin data into database

    //send invite mail
    mailService.sendInviteMailToAdmin(warehouseadmin, UserType.WAREHOUSE_ADMIN.toString(), admin.getUsername());
  }

  public void wareHouseAdminAccepted(UUID userId){
    invitationStatus.updateAcceptStatus(userId);
  }

  public void wareHouseAdminRejected(UUID userId){
    invitationStatus.updateRejectStatus(userId);
  }
}
