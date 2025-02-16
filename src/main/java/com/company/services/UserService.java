package com.company.services;

import com.company.components.JwtTokenUtil;
import com.company.exceptions.DataNotFoundException;
import com.company.forms.UserRegisterForm;
import com.company.models.Role;
import com.company.models.User;
import com.company.repositories.RoleRepository;
import com.company.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

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
            String encodePassword=passwordEncoder.encode(password);
            user.setPassword(password);
        }
        return userRepository.save(user);
    }

    @Override
    public String login(String phoneNumber, String password) throws DataNotFoundException {
        Optional<User> optionalUser=userRepository.findByPhoneNumber(phoneNumber);
        if(optionalUser.isEmpty()){
            throw new DataNotFoundException("Invalid phone number or password");
        }
        User existingUser=optionalUser.get();

        // check password
        if(existingUser.getFacebookAccountId()==0 && existingUser.getGoogleAccountId()==0){
            if(!passwordEncoder.matches(password,existingUser.getPassword())){
                throw new BadCredentialsException("Wrong phone number or password");
            }
        }

        //authenticate with java spring security
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(
                phoneNumber,password
        );
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }
}
