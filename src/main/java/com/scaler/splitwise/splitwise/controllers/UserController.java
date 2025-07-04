package com.scaler.splitwise.splitwise.controllers;

import com.scaler.splitwise.splitwise.Exceptions.UserAlreadyExistsException;
import com.scaler.splitwise.splitwise.dtos.RegisterUserRequestDto;
import com.scaler.splitwise.splitwise.dtos.RegisterUserResponseDto;
import com.scaler.splitwise.splitwise.models.User;
import com.scaler.splitwise.splitwise.services.UserService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public RegisterUserResponseDto registerUser(RegisterUserRequestDto registerUserRequestDto){
        User user;
        RegisterUserResponseDto registerUserResponseDto = new RegisterUserResponseDto();
        try{
            user = userService.registerUser(
                    registerUserRequestDto.getUsername(),
                    registerUserRequestDto.getMobile(),
                    registerUserRequestDto.getPassword()
            );
            registerUserResponseDto.setUserId(user.getId());
            registerUserResponseDto.setStatus("SUCCESS");
        }catch (UserAlreadyExistsException userAlreadyExistsException){
            registerUserResponseDto.setStatus("FAIL");
            registerUserResponseDto.setMessage(userAlreadyExistsException.getMessage());
        }

        return registerUserResponseDto;
    }
}
