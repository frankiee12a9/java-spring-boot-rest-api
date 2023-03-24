package com.github.frankiie.springboot.domain.management.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.github.frankiie.springboot.domain.management.entity.UserRequestInfo;

@Service
public class UserRequestHandlerService {
  
  public Map<String, UserRequestInfo> userRequestStorage;

  public UserRequestHandlerService() {
    userRequestStorage = new HashMap<>();
  }

  public boolean userRequestStored(String usrId, UserRequestInfo val) {
    userRequestStorage.put(usrId, val);

    return userRequestStorage.get(usrId).equals(null) ? false : true;
  }
}
