package com.scaler.splitwise.splitwise.commands;

import org.springframework.stereotype.Component;

@Component
public class AddGroupCommand implements Command{
    @Override
    public boolean matches(String input) {
        return false;
    }

    @Override
    public void execute(String input) {
        return;
    }
}
