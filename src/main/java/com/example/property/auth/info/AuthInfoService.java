package com.example.property.auth.info;

import com.example.property.exception.exceptions.NotAuthorizedException;
import com.example.property.model.User;
import com.example.property.service.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

@Service
@AllArgsConstructor
public class AuthInfoService {
    private final UserRepository userRepository;
    private final AuthInfoMapper authInfoMapper;

    public User getUserByAuthentication(Authentication authentication) {
        if (authentication == null) throw new NotAuthorizedException();
        String username = authentication.getName();
        return userRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    @ResponseBody
    public String getAuthInfo(Authentication authentication) {
        User user = getUserByAuthentication(authentication);
        AuthInfo authInfo;
        if (user == null) {
            authInfo = AuthInfo.builder()
                    .status("error")
                    .code(401)
                    .details("auth details error")
                    .build();
            return authInfoMapper.toAuthInfoDTO(authInfo).getJson();
        }
        authInfo = AuthInfo.builder()
                .status("ok")
                .code(200)
                .id(user.getId())
                .username(user.getUsername())
                .roles(user.getRoles().toString())
                .details("success get auth info")
                .build();
        return authInfoMapper.toAuthInfoDTO(authInfo).getJson();
    }
}
