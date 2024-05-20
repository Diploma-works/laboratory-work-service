package com.vko.labworkproducer.mapper;

import com.vko.labworkproducer.dto.AuthDto;
import com.vko.labworkproducer.entity.Role;
import com.vko.labworkproducer.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User authDtoToUser(AuthDto dto) {
        return User.builder()
                .username(dto.username())
                .password(dto.password())
                .role(Role.USER)
                .build();
    }

}
