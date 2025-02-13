package com.client.ws.rasmooplus.service;

import com.client.ws.rasmooplus.model.mysql.UserType;
import com.client.ws.rasmooplus.repository.mysql.UserTypeRepository;
import com.client.ws.rasmooplus.service.impl.UserTypeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserTypeServiceTest {

    @Mock
    private UserTypeRepository userTypeRepository;

    @InjectMocks
    private UserTypeServiceImpl userTypeService;

    @Test
    void given_findAll_when_thereAreDataInDataBase_then_returnAllData() {
        List<UserType> userTypeList = new ArrayList<>();
        UserType userType1 = new UserType(1L, "Professor", "Professor da plataforma");
        UserType userType2 = new UserType(2L, "Aluno", "Aluno da plataforma");
        UserType userType3 = new UserType(3L, "Administrador", "Funcionário");

        userTypeList.add(userType1);
        userTypeList.add(userType2);
        userTypeList.add(userType3);

        Mockito.when(userTypeRepository.findAll()).thenReturn(userTypeList);
        var result = userTypeService.findAll();
        System.out.println(result);
        Assertions.assertThat(result).isNotEmpty();
        Assertions.assertThat(result).hasSize(3);
    }
}
