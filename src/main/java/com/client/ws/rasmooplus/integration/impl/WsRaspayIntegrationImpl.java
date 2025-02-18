package com.client.ws.rasmooplus.integration.impl;

import com.client.ws.rasmooplus.dto.wsraspay.CustomerDto;
import com.client.ws.rasmooplus.dto.wsraspay.OrderDto;
import com.client.ws.rasmooplus.dto.wsraspay.PaymentDto;
import com.client.ws.rasmooplus.exception.HttpClientException;
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
    private String raspayHost;

    @Value("${webservices.raspay.customer}")
    private String raspayCustomer;

    @Value("${webservices.raspay.order}")
    private String raspayOrder;

    @Value("${webservices.raspay.payment}")
    private String raspayPayment;

    @Value("${webservices.raspay.username}")
    private String raspayUsername;

    @Value("${webservices.raspay.password}")
    private String raspayPassword;

    private final HttpHeaders headers;

    private final RestTemplate restTemplate;

    public WsRaspayIntegrationImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.headers =  getHttpHeaders();
    }

    @Override
    public CustomerDto createCustomer(CustomerDto dto) {
        try {
            HttpEntity<CustomerDto> request = new HttpEntity<>(dto, headers);
            ResponseEntity<CustomerDto> response =
                    restTemplate.exchange(raspayHost + raspayCustomer, HttpMethod.POST, request, CustomerDto.class);
            return response.getBody();
        } catch (Exception ex) {
            throw new HttpClientException(ex.getLocalizedMessage());
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

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String credentials = raspayUsername + ":" + raspayPassword;
        String base64Credentials = Base64.getEncoder().encodeToString(credentials.getBytes());
        headers.add("Authorization", "Basic " + base64Credentials);
        return headers;
    }
}
