package com.mykare.in.service;

import com.mykare.in.request.UserRegistrationRequest;
import com.mykare.in.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface UserService {
    String userRegistration(UserRegistrationRequest userRegistrationRequest);

    String userLogin(UserRegistrationRequest userRegistrationRequest);

    List<UserResponseDto> getAllRegisteredUsers(String email);

    String deleteUserByEmail(String userName,String email);
}
