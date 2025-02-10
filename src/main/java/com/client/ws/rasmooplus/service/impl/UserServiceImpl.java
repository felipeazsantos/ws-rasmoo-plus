package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.dto.UserDto;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.mapper.UserMapper;
import com.client.ws.rasmooplus.model.mysql.User;
import com.client.ws.rasmooplus.model.mysql.UserType;
import com.client.ws.rasmooplus.model.redis.RecoveryCode;
import com.client.ws.rasmooplus.repository.mysql.UserRepository;
import com.client.ws.rasmooplus.repository.mysql.UserTypeRepository;
import com.client.ws.rasmooplus.repository.redis.RecoveryCodeRepository;
import com.client.ws.rasmooplus.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;
    private final RecoveryCodeRepository recoveryCodeRepository;

    public UserServiceImpl(UserRepository userRepository, UserTypeRepository userTypeRepository, RecoveryCodeRepository recoveryCodeRepository) {
        this.userRepository = userRepository;
        this.userTypeRepository = userTypeRepository;
        this.recoveryCodeRepository = recoveryCodeRepository;
    }

    @Override
    public User create(UserDto dto) {
        if (Objects.nonNull(dto.getId())) {
            throw new BadRequestException("id deve ser nulo");
        }

        var userTypeOpt = userTypeRepository.findById(dto.getUserTypeId());
        if (userTypeOpt.isEmpty()) {
            throw new NotFoundException("userTypeId não encontrado");
        }

        UserType userType = userTypeOpt.get();
        User user = UserMapper.fromDtoToEntity(dto, userType, null);

        return userRepository.save(user);
    }

    @Override
    public Object sendRecoveryCode(String email) {
        String code = String.format("%4d", new Random().nextInt(10000));

        RecoveryCode recoveryCode = new RecoveryCode(null, email, code, LocalDateTime.now());
        recoveryCodeRepository.save(recoveryCode);
        return null;
    }
}
