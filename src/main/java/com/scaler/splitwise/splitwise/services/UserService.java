package com.scaler.splitwise.splitwise.services;

import com.scaler.splitwise.splitwise.Exceptions.UserAlreadyExistsException;
import com.scaler.splitwise.splitwise.models.User;

public interface UserService {
    public User registerUser(String username, String mobile, String password) throws UserAlreadyExistsException;
}
