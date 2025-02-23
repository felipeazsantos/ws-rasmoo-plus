package com.client.ws.rasmooplus.controller;

import com.client.ws.rasmooplus.model.mysql.User;
import com.client.ws.rasmooplus.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(profiles = "test")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    void given_uploadPhoto_when_receiveMultiPartFile_then_return200Ok() throws Exception {
        FileInputStream fis = new FileInputStream("src/test/resources/static/javaLogo.png");
        MockMultipartFile file = new MockMultipartFile("file", "javaLogo.png", MediaType.MULTIPART_FORM_DATA_VALUE,
                fis);

        Mockito.when(userService.uploadPhoto(1L, file)).thenReturn(new User());

        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/user/1/upload-photo");
        builder.with(request -> {
            request.setMethod(HttpMethod.PATCH.name());
            return request;
        });

        mockMvc.perform(builder.file(file))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(userService, times(1)).uploadPhoto(1L, file);
    }

    @Test
    void given_downloadPhoto_when_thereIsPhotoInDatabase_then_return200Ok() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/user/2/download-photo")
                        .contentType(MediaType.IMAGE_PNG)
                        .contentType(MediaType.IMAGE_JPEG))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(userService, times(1)).downloadPhoto(2L);
    }
}