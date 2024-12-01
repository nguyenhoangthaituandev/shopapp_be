package com.company.services;

import com.company.exceptions.DataNotFoundException;
import com.company.forms.UserRegisterForm;
import com.company.models.Role;
import com.company.models.User;
import com.company.repositories.RoleRepository;
import com.company.repositories.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository,RoleRepository roleRepository){
        this.userRepository=userRepository;
        this.roleRepository=roleRepository;
    }

    @Override
    public User register(UserRegisterForm userRegisterForm) throws DataNotFoundException {
        String phoneNumber=userRegisterForm.getPhoneNumber();
        if(userRepository.existsByPhoneNumber(phoneNumber)){
            throw new DataIntegrityViolationException("Phone number already exists");
        }
        // convert UserRegisterForm => UserEntity
        User user=User.builder()
                .fullName(userRegisterForm.getFullName())
                .phoneNumber(userRegisterForm.getPhoneNumber())
                .password(userRegisterForm.getPassword())
                .address(userRegisterForm.getAddress())
                .dateOfBirth(userRegisterForm.getDateOfBirth())
                .facebookAccountId(userRegisterForm.getFacebookAccountId())
                .googleAccountId(userRegisterForm.getGoogleAccountId())
                .build();
        Role role=roleRepository.findById(userRegisterForm.getRoleId())
                .orElseThrow(()-> new DataNotFoundException("Role not found"));
        user.setRole(role);

        // check dang nhap normal
        if(userRegisterForm.getFacebookAccountId()==0 && userRegisterForm.getGoogleAccountId()==0){
            String password=userRegisterForm.getPassword();
            //String encodePassword=passwordEncoder.encode(password);
            user.setPassword(password);
        }
        return userRepository.save(user);
    }

    @Override
    public String login(String phoneNumber, String password) {
        // TODO
        return null;
    }
}
