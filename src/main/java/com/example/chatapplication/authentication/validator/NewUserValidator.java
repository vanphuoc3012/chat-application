package com.example.chatapplication.authentication.validator;

import com.example.chatapplication.authentication.model.User;
import com.example.chatapplication.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class NewUserValidator implements Validator {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if(userRepository.existsById(user.getUsername())) {
            errors.rejectValue("username", "new.account.username.already.exists");
        }
    }
}
