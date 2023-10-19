package com.nissum.techtest.service;

import com.nissum.techtest.common.EmailValidator;
import com.nissum.techtest.common.PasswordValidator;
import com.nissum.techtest.dto.CreateUserRequestDTO;
import com.nissum.techtest.dto.CreatedUserResponseDTO;
import com.nissum.techtest.dto.UserResponseDTO;
import com.nissum.techtest.entity.PhoneEntity;
import com.nissum.techtest.entity.UserEntity;
import com.nissum.techtest.exception.EmailException;
import com.nissum.techtest.exception.PasswordException;
import com.nissum.techtest.repository.IPhoneRepository;
import com.nissum.techtest.repository.IUserRepository;
import com.nissum.techtest.util.MappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    @Autowired
    private MappingService mappingService;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IPhoneRepository phoneRepository;

    @Autowired
    private PasswordValidator passwordValidator;

    @Autowired
    private EmailValidator emailValidator;

    @Transactional
    public CreatedUserResponseDTO createUser(CreateUserRequestDTO userDto) throws PasswordException, EmailException {

        if (!passwordValidator.isValidPassword(userDto.getPassword())) {
            throw new PasswordException(PasswordException.INVALID_PASSWORD);
        }

        if (!emailValidator.isValidEmail(userDto.getEmail())) {
            throw new EmailException(EmailException.INVALID_EMAIL);
        }

        if(userRepository.existsByEmail(userDto.getEmail())){
            throw new EmailException(EmailException.EXISTS_EMAIL);
        }

        UserEntity user = UserEntity.builder()
                .name(userDto.getName())
                .password(userDto.getEmail())
                .email(userDto.getEmail())
                .timeCreated(LocalDateTime.now())
                .timeModified(LocalDateTime.now())
                .timeLastLogin(LocalDateTime.now())
                .isActive(true)
                .build();

        user = userRepository.save(user);

        List<PhoneEntity> phones = mappingService.mapList(userDto.getPhones(), PhoneEntity.class);

        UserEntity userEntity = UserEntity.builder()
                .id(user.getId())
                .build();

        phones.stream().map(phone -> {
            phone.setUser(userEntity);
            phone.setIsActive(true);
            return phone;
        }).collect(Collectors.toList());

        phoneRepository.saveAll(phones);

        return mappingService.map(user, CreatedUserResponseDTO.class);

    }

    public List<UserResponseDTO> getUsers(){
        return mappingService.mapList(userRepository.findAll(), UserResponseDTO.class);
    }
}
