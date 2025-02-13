package com.mykare.in.serviceimpl;

import com.mykare.in.entity.RoleMst;
import com.mykare.in.entity.Users;
import com.mykare.in.enums.RoleType;
import com.mykare.in.repository.RoleMstRepository;
import com.mykare.in.repository.UsersRepository;
import com.mykare.in.request.UserRegistrationRequest;
import com.mykare.in.response.UserResponseDto;
import com.mykare.in.service.UserService;
import com.mykare.in.utils.CommonFunctionUtils;
import com.mykare.in.utils.DataValidations;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl  implements UserService {

    private final  UsersRepository usersRepository;

    private final RoleMstRepository roleMstRepository;

    private final PasswordEncoder passwordEncoder;

    private final CommonFunctionUtils commonFunctionUtils;

    @Override
    public String userRegistration(UserRegistrationRequest userRegistrationRequest) {

        if (userRegistrationRequest == null ||
                !DataValidations.isValidString(userRegistrationRequest.getName()) ||
                !DataValidations.isValidString(userRegistrationRequest.getEmail()) ||
                !DataValidations.isValidString(userRegistrationRequest.getGender()) ||
                !DataValidations.isValidString(userRegistrationRequest.getPassword())) {
            throw new IllegalArgumentException("Invalid input: Name, Email, Gender, and Password are required.");
        }
        if (usersRepository.findByEmail(userRegistrationRequest.getEmail()).isPresent()) {
            throw new IllegalStateException("Email already exists. Please use a different email.");
        }
        RoleMst roleMst = roleMstRepository.findByRoleName(userRegistrationRequest.getRoleName());

        Users user = Users.builder()
                .name(userRegistrationRequest.getName())
                .email(userRegistrationRequest.getEmail())
                .gender(userRegistrationRequest.getGender())
                .password(passwordEncoder.encode(userRegistrationRequest.getPassword()))
                .createdBy("ADMIN")
                .createdAt(commonFunctionUtils.getCurrentTimeStamp())
                .role(roleMst)
                .ipAddr(commonFunctionUtils.getIp())
                .country(commonFunctionUtils.getCountry(commonFunctionUtils.getIp()))
                .build();

        usersRepository.save(user);

        return "User registered successfully!";
    }

    @Override
    public String userLogin(UserRegistrationRequest userRegistrationRequest) {

        Optional<Users> optionalUser = usersRepository.findByEmail(userRegistrationRequest.getEmail());

        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();

            if (passwordEncoder.matches(userRegistrationRequest.getPassword(), user.getPassword())) {
                return "Login successful";
            } else {
                return "Invalid password";
            }
        }
        return "Invalid email";
    }

    @Override
    public List<UserResponseDto> getAllRegisteredUsers(String email) {

        Optional<Users> optionalUser = usersRepository.findByEmail(email);
        System.out.println(optionalUser.get().getRole().getRoleName());
        if(optionalUser.get().getRole().getRoleName()==RoleType.ROLE_ADMIN)
        {
            List<Users> users = usersRepository.findAll();
            return users.stream()
                    .filter(u -> u.getRole().getRoleName().equals(RoleType.ROLE_USER))
                    .map(UserResponseDto::new)
                    .collect(Collectors.toList());

        }

        return Collections.emptyList();
    }

    @Override
    public String deleteUserByEmail(String userName,String email)
    {

        Optional<Users> optionalUser = usersRepository.findByEmail(userName);
        System.out.println(optionalUser.get().getRole().getRoleName());
        if(optionalUser.get().getRole().getRoleName()==RoleType.ROLE_ADMIN)
        {
            if (!DataValidations.isValidString(email)) {
                return "Email can't be empty";
            }
            Optional<Users> deletedUser = usersRepository.findByEmail(email);

            return deletedUser.map(user -> {
                usersRepository.delete(user);
                return "User Deleted Successfully";
            }).orElse("User Not Present");
        }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have the required permissions to delete this user.");

        }
    }

}
