package com.company.services;

import com.company.exceptions.DataNotFoundException;
import com.company.forms.UserLoginForm;
import com.company.forms.UserRegisterForm;
import com.company.models.User;

public interface IUserService {
    User register(UserRegisterForm userRegisterForm) throws DataNotFoundException;

    String login(String phoneNumber, String password);
}
