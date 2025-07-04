package com.scaler.splitwise.splitwise.commands;

import com.scaler.splitwise.splitwise.controllers.UserController;
import com.scaler.splitwise.splitwise.dtos.RegisterUserRequestDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RegisterUserCommand implements Command{
    private final UserController userController;

    public RegisterUserCommand(UserController userController) {
        this.userController = userController;
    }

    @Override
    public boolean matches(String input) {
        //Register vinsmokesanji 003 namisswwaann
        List<String> inputWords = List.of(input.split(" "));
        if(inputWords.size() == 4 && inputWords.get(0).equalsIgnoreCase(CommandKeywords.REGISTER_USER)){
            return true;
        }
        return false;
    }

    @Override
    public void execute(String input) {
        List<String> inputWords = List.of(input.split(" "));
        String username = inputWords.get(1);
        String mobile = inputWords.get(2);
        String password = inputWords.get(3);

        RegisterUserRequestDto registerUserRequestDto = new RegisterUserRequestDto();
        registerUserRequestDto.setUsername(username);
        registerUserRequestDto.setMobile(mobile);
        registerUserRequestDto.setPassword(password);

        userController.registerUser(registerUserRequestDto);
    }
}
