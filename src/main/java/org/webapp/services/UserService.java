package org.webapp.services;

import org.webapp.dao.UserDAO;

public interface UserService {

   UserDAO login(String userId);
   UserDAO register(UserDAO userDAO);
   boolean deleteUser(String userId);
   UserDAO updateUserInfo(UserDAO userDAO);
   String getMessage();
}
