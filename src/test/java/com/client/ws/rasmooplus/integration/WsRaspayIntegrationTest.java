package com.client.ws.rasmooplus.integration;

import com.client.ws.rasmooplus.dto.wsraspay.CustomerDto;
import com.client.ws.rasmooplus.exception.HttpClientException;
import com.client.ws.rasmooplus.integration.impl.WsRaspayIntegrationImpl;
import org.apache.coyote.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WsRaspayIntegrationTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WsRaspayIntegrationImpl wsRaspayIntegration;

    private static final String raspayUsername = "rasmooplus";
    private static final String raspayPassword = "r@sm00";
    private static HttpHeaders headers;

    private CustomerDto dto;
    private HttpEntity<CustomerDto> request;


    @BeforeAll
    public static void loadHeaders() {
        headers = getHttpHeaders();
    }

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(wsRaspayIntegration, "headers", headers);
        ReflectionTestUtils.setField(wsRaspayIntegration, "raspayHost", "http://localhost:8080");
        ReflectionTestUtils.setField(wsRaspayIntegration, "raspayCustomer", "/customer");
        dto = new CustomerDto();
        dto.setCpf("11111111111");
        request = new HttpEntity<>(dto, headers);
    }

    @Test
    void given_createCustomer_when_apiResponseIs201Created_then_returnCustomerDto() {
        when(restTemplate.exchange("http://localhost:8080/customer", HttpMethod.POST, request, CustomerDto.class))
                .thenReturn(ResponseEntity.of(Optional.of(dto)));
        assertEquals(dto, wsRaspayIntegration.createCustomer(dto));
        verify(restTemplate, times(1)).exchange("http://localhost:8080/customer", HttpMethod.POST, request, CustomerDto.class);
    }

    @Test
    void given_createCustomer_when_apiResponseError_then_throwHttpClientException() {
        when(restTemplate.exchange("http://localhost:8080/customer", HttpMethod.POST, request, CustomerDto.class))
                .thenThrow(new RuntimeException("server down"));
        try {
            wsRaspayIntegration.createCustomer(dto);
        } catch (Exception ex) {
            assertEquals(HttpClientException.class, ex.getClass());
            assertEquals("server down", ex.getLocalizedMessage());
        }
        verify(restTemplate, times(1)).exchange("http://localhost:8080/customer", HttpMethod.POST, request, CustomerDto.class);
    }

    @Test
    void given_createCustomer_when_apiResponseIsBadRequest_then_returnNull() {
        when(restTemplate.exchange("http://localhost:8080/customer", HttpMethod.POST, request, CustomerDto.class))
                .thenReturn(ResponseEntity.badRequest().build());
        assertNull(wsRaspayIntegration.createCustomer(dto));
    }

    private static HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String credentials = raspayUsername + ":" + raspayPassword;
        String base64Credentials = Base64.getEncoder().encodeToString(credentials.getBytes());
        headers.add("Authorization", "Basic " + base64Credentials);
        return headers;
    }
}
