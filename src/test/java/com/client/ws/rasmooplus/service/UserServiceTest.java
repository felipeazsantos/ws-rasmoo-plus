package com.client.ws.rasmooplus.service;

import com.client.ws.rasmooplus.dto.UserDto;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.model.mysql.User;
import com.client.ws.rasmooplus.model.mysql.UserType;
import com.client.ws.rasmooplus.repository.mysql.UserRepository;
import com.client.ws.rasmooplus.repository.mysql.UserTypeRepository;
import com.client.ws.rasmooplus.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserTypeRepository userTypeRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDto userDto;

    @BeforeEach
    void loadUserDto() {
        userDto = new UserDto();
        userDto.setEmail("felipe@gmail.com");
        userDto.setCpf("548.555.830-57");
        userDto.setUserTypeId(1L);
    }

    @Test
    void given_create_when_idIsNullAndUserTypeIsFound_then_returnUserCreated() {
        UserType userType = getUserType();

        when(userTypeRepository.findById(1L)).thenReturn(Optional.of(userType));

        User user = getUser(userType);

        when(userRepository.save(any(User.class))).thenReturn(user);

        Assertions.assertEquals(user, userService.create(userDto));

        verify(userTypeRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void given_create_when_idIsNotNull_then_throwBadRequestException() {
        userDto.setId(1L);

        Assertions.assertThrows(BadRequestException.class, () -> userService.create(userDto));
        verify(userTypeRepository, times(0)).findById(any());
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void given_create_when_idIsNullAndUserTypeNotFound_then_throwNotFoundException() {
        when(userTypeRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> userService.create(userDto));
        verify(userTypeRepository, times(1)).findById(1L);
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void given_uploadPhoto_when_thereIsUserAndFileAndItIsPNGorJPEG_then_updatePhotoAndReturnUser() throws IOException {
        FileInputStream fis = new FileInputStream("src/test/resources/static/javaLogo.png");
        MockMultipartFile file = new MockMultipartFile("file", "javaLogo.png", MediaType.MULTIPART_FORM_DATA_VALUE,
                fis);

        UserType userType = getUserType();
        User user = getUser(userType);

        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        User userReturned = userService.uploadPhoto(2L, file);
        assertNotNull(userReturned);
        assertNotNull(userReturned.getPhoto());
        assertEquals("javaLogo.png", userReturned.getPhotoName());

        verify(userRepository, times(1)).findById(2L);
    }

    @Test
    void given_uploadPhoto_when_thereIsUserAndFileAndItIsNotPNGorJPEG_then_throwBadRequestException() throws IOException {
        FileInputStream fis = new FileInputStream("src/test/resources/static/javaLogo.png");
        MockMultipartFile file = new MockMultipartFile("file", "javaLogo.txt", MediaType.MULTIPART_FORM_DATA_VALUE,
                fis);

        assertThrows(BadRequestException.class, () -> userService.uploadPhoto(2L, file));
        verify(userRepository, times(0)).findById(any());
    }

    private static UserType getUserType() {
        return new UserType(1L, "Aluno", "Aluno da plataforma");
    }

    private User getUser(UserType userType) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setCpf(userDto.getCpf());
        user.setDtSubscription(userDto.getDtSubscription());
        user.setDtExpiration(userDto.getDtExpiration());
        user.setUserType(userType);
        return user;
    }
}
