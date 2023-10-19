package com.nissum.techtest.controller;

import com.nissum.techtest.common.JsonUtils;
import com.nissum.techtest.dto.CreatePhoneRequestDTO;
import com.nissum.techtest.dto.CreateUserRequestDTO;
import com.nissum.techtest.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    private static final String USER_CONTROLLER_PATH_URI = "/users/v1";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testCreateUser() throws Exception {

        CreatePhoneRequestDTO phoneRequestDTO = CreatePhoneRequestDTO.builder()
                .number("3506241533")
                .cityCode(1)
                .countryCode(57)
                .build();

        CreateUserRequestDTO userDTO = CreateUserRequestDTO.builder()
                .name("Test user")
                .email("mpedrazaces@gmail.com")
                .password("Expell1armus*.0")
                .phones(List.of(phoneRequestDTO))
                .build();

        mockMvc.perform(patch(USER_CONTROLLER_PATH_URI )
                        .content(JsonUtils.convertToJson(userDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void getUsers() throws Exception {
        mockMvc.perform(get(USER_CONTROLLER_PATH_URI )
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
