package org.webapp.controllers;

import org.webapp.services.UserService;
import org.web.annotations.others.Controller;

import javax.inject.Inject;

@Controller(root = "/user")
public class UserController{

    private UserService userService;

    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

}
