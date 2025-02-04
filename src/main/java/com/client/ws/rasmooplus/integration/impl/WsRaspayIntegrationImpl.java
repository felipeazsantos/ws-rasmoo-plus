package com.client.ws.rasmooplus.integration.impl;

import com.client.ws.rasmooplus.dto.wsraspay.CustomerDto;
import com.client.ws.rasmooplus.dto.wsraspay.OrderDto;
import com.client.ws.rasmooplus.dto.wsraspay.PaymentDto;
import com.client.ws.rasmooplus.integration.WsRaspayIntegration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Component
public class WsRaspayIntegrationImpl implements WsRaspayIntegration {

    private static final String WS_RASPAY_ENDPOINT = "http://localhost:8083/ws-raspay/v1";
    private static final HttpHeaders headers = getHttpHeaders();

    private final RestTemplate restTemplate;

    public WsRaspayIntegrationImpl() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public CustomerDto createCustomer(CustomerDto dto) {
        try {
            HttpEntity<CustomerDto> request = new HttpEntity<>(dto, headers);
            ResponseEntity<CustomerDto> response =
                    restTemplate.exchange(WS_RASPAY_ENDPOINT + "/customer", HttpMethod.POST, request, CustomerDto.class);
            return response.getBody();
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public OrderDto createOrder(OrderDto dto) {
        try {
            HttpEntity<OrderDto> request = new HttpEntity<>(dto, headers);
            ResponseEntity<OrderDto> response =
                    restTemplate.exchange(WS_RASPAY_ENDPOINT + "/order", HttpMethod.POST, request, OrderDto.class);
            return response.getBody();
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public Boolean processPayment(PaymentDto dto) {
        try {
            HttpEntity<PaymentDto> request = new HttpEntity<>(dto, headers);
            ResponseEntity<Boolean> response =
                    restTemplate.exchange(WS_RASPAY_ENDPOINT + "/payment/credit-card/", HttpMethod.POST, request, Boolean.class);
            return response.getBody();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private static HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String credentials = "rasmooplus:r@sm00";
        String base64Credentials = Base64.getEncoder().encodeToString(credentials.getBytes());
        headers.add("Authorization", "Basic " + base64Credentials);
        return headers;
    }
}
