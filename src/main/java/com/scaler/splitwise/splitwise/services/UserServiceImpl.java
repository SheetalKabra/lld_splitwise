package com.scaler.splitwise.splitwise.services;

import com.scaler.splitwise.splitwise.Exceptions.UserAlreadyExistsException;
import com.scaler.splitwise.splitwise.models.User;
import com.scaler.splitwise.splitwise.models.UserStatus;
import com.scaler.splitwise.splitwise.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User registerUser(String username, String mobile, String password) throws UserAlreadyExistsException {
        Optional<User> optionalUser = userRepository.findByMobile(mobile);
        if(optionalUser.isPresent()){
            if(optionalUser.get().getUserStatus().equals(UserStatus.ACTIVE)){
                throw new UserAlreadyExistsException("User with mobile number " + mobile + " already exists.");
            }else{
                // If the user exists but is not active, we can activate them from Invited mode
                User existingUser = optionalUser.get();
                existingUser.setUserStatus(UserStatus.ACTIVE);
                existingUser.setName(username);
                existingUser.setPassword(password);
                return userRepository.save(existingUser);
            }

        }

        User user = new User();
        user.setName(username);
        user.setEmail(username);
        user.setMobile(mobile);
        user.setPassword(password);
        user.setUserStatus(UserStatus.ACTIVE);
        return userRepository.save(user);

    }
}
