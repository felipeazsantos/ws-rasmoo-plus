package com.client.ws.rasmooplus.integration.impl;

import com.client.ws.rasmooplus.dto.wsraspay.CustomerDto;
import com.client.ws.rasmooplus.dto.wsraspay.OrderDto;
import com.client.ws.rasmooplus.dto.wsraspay.PaymentDto;
import com.client.ws.rasmooplus.integration.WsRaspayIntegration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Component
public class WsRaspayIntegrationImpl implements WsRaspayIntegration {

    @Value("${webservices.raspay.host}")
    private static String raspayHost;

    @Value("${webservices.raspay.customer}")
    private static String raspayCustomer;

    @Value("${webservices.raspay.order}")
    private static String raspayOrder;

    @Value("${webservices.raspay.payment}")
    private static String raspayPayment;

    @Value("${webservices.raspay.username}")
    private static String raspayUsername;

    @Value("${webservices.raspay.password}")
    private static String raspayPassword;

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
                    restTemplate.exchange(raspayHost + raspayCustomer, HttpMethod.POST, request, CustomerDto.class);
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
                    restTemplate.exchange(raspayHost + raspayOrder, HttpMethod.POST, request, OrderDto.class);
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
                    restTemplate.exchange(raspayHost + raspayPayment, HttpMethod.POST, request, Boolean.class);
            return response.getBody();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private static HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String credentials = raspayUsername + ":" + raspayPassword;
        String base64Credentials = Base64.getEncoder().encodeToString(credentials.getBytes());
        headers.add("Authorization", "Basic " + base64Credentials);
        return headers;
    }
}
