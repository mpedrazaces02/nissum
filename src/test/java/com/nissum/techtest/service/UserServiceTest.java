package com.nissum.techtest.service;

import com.nissum.techtest.common.EmailValidator;
import com.nissum.techtest.common.PasswordValidator;
import com.nissum.techtest.dto.CreatePhoneRequestDTO;
import com.nissum.techtest.dto.CreateUserRequestDTO;
import com.nissum.techtest.dto.CreatedUserResponseDTO;
import com.nissum.techtest.entity.PhoneEntity;
import com.nissum.techtest.entity.UserEntity;
import com.nissum.techtest.exception.EmailException;
import com.nissum.techtest.exception.PasswordException;
import com.nissum.techtest.repository.IPhoneRepository;
import com.nissum.techtest.repository.IUserRepository;
import com.nissum.techtest.util.MappingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IPhoneRepository phoneRepository;

    @Mock
    private MappingService mappingService;

    @Mock
    private PasswordValidator passwordValidator;

    @Mock
    private EmailValidator emailValidator;

    @InjectMocks
    private UserService userService;

    @Test
    void testCreateUser_Success() throws PasswordException, EmailException {

        UserEntity savedUser = getUserEntity();

        when(passwordValidator.isValidPassword(getCreatedUserRequestDTO().getPassword())).thenReturn(true);
        when(emailValidator.isValidEmail(getCreatedUserRequestDTO().getEmail())).thenReturn(true);
        when(userRepository.existsByEmail(getCreatedUserRequestDTO().getEmail())).thenReturn(false);

        when(userRepository.save(any())).thenReturn(savedUser);
        when(phoneRepository.saveAll(any())).thenReturn(savedUser.getPhones());

        List<PhoneEntity> savedPhones = List.of(getPhoneEntity());

        when(mappingService.mapList(any(), eq(PhoneEntity.class))).thenReturn(savedPhones);
        when(mappingService.map(any(), eq(CreatedUserResponseDTO.class))).thenReturn(getCreatedUserResponse());

        CreatedUserResponseDTO result = userService.createUser(getCreatedUserRequestDTO());

        assertNotNull(result);
        verify(userRepository, times(1)).save(any());
        verify(phoneRepository, times(1)).saveAll(List.of(getPhoneEntity()));
        verify(mappingService).map(savedUser, CreatedUserResponseDTO.class);

    }

    @Test
    void testCreateUser_InvalidPassword() {
        CreateUserRequestDTO userDto = new CreateUserRequestDTO();
        userDto.setPassword("WeakPassword");

        when(passwordValidator.isValidPassword(userDto.getPassword())).thenReturn(false);

        assertThrows(PasswordException.class, () -> userService.createUser(userDto));
    }

    @Test
    void testCreateUser_InvalidEmail() {
        CreateUserRequestDTO userDto = new CreateUserRequestDTO();
        userDto.setEmail("invalidEmail");

        when(passwordValidator.isValidPassword(userDto.getPassword())).thenReturn(true);
        when(emailValidator.isValidEmail(userDto.getEmail())).thenReturn(false);

        assertThrows(EmailException.class, () -> userService.createUser(userDto));
    }

    @Test
    void testCreateUser_EmailAlreadyExists() {
        CreateUserRequestDTO userDto = new CreateUserRequestDTO();
        userDto.setEmail("existingEmail");

        when(passwordValidator.isValidPassword(userDto.getPassword())).thenReturn(true);
        when(emailValidator.isValidEmail(userDto.getEmail())).thenReturn(true);
        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(true);

        assertThrows(EmailException.class, () -> userService.createUser(userDto));
    }

    private CreateUserRequestDTO getCreatedUserRequestDTO(){
        return CreateUserRequestDTO.builder()
                .name("Test user")
                .email("mpedrazaces@gmail.com")
                .password("Expell1armus*.0")
                .phones(List.of(getCreatedPhoneRequestDTO()))
                .build();
    }

    private CreatePhoneRequestDTO getCreatedPhoneRequestDTO(){
        return CreatePhoneRequestDTO.builder()
                .number("3506241533")
                .cityCode(1)
                .countryCode(57)
                .build();
    }

    private UserEntity getUserEntity(){
        return UserEntity.builder()
                .name("Test user")
                .email("mpedrazaces@gmail.com")
                .password("Expell1armus*.0")
                .isActive(true)
                .timeCreated(LocalDateTime.now())
                .timeModified(LocalDateTime.now())
                .timeLastLogin(LocalDateTime.now())
                .build();
    }

    private PhoneEntity getPhoneEntity(){
        return PhoneEntity.builder()
                .number("3506241533")
                .isActive(true)
                .cityCode(1)
                .countryCode(57)
                .user(new UserEntity())
                .build();
    }

    private CreatedUserResponseDTO getCreatedUserResponse(){
        return CreatedUserResponseDTO.builder()
                .id(UUID.fromString("480c0b7d-8af9-46ff-8225-5c11028285db"))
                .token("630d4906-5f5d-44b2-98e8-2f06a6c0ca36")
                .isActive(true)
                .timeCreated(LocalDateTime.now())
                .timeModified(LocalDateTime.now())
                .timeLastLogin(LocalDateTime.now())
                .build();
    }


}
