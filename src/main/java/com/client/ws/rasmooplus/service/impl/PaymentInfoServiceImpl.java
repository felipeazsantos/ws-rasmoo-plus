package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.dto.PaymentProcessDto;
import com.client.ws.rasmooplus.dto.wsraspay.CreditCardDto;
import com.client.ws.rasmooplus.dto.wsraspay.CustomerDto;
import com.client.ws.rasmooplus.dto.wsraspay.OrderDto;
import com.client.ws.rasmooplus.dto.wsraspay.PaymentDto;
import com.client.ws.rasmooplus.enums.UserTypeEnum;
import com.client.ws.rasmooplus.exception.BusinessException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.integration.WsRaspayIntegration;
import com.client.ws.rasmooplus.mapper.UserPaymentInfoMapper;
import com.client.ws.rasmooplus.mapper.wsraspay.CreditCardMapper;
import com.client.ws.rasmooplus.mapper.wsraspay.CustomerMapper;
import com.client.ws.rasmooplus.mapper.wsraspay.OrderMapper;
import com.client.ws.rasmooplus.mapper.wsraspay.PaymentMapper;
import com.client.ws.rasmooplus.model.mysql.*;
import com.client.ws.rasmooplus.repository.mysql.*;
import com.client.ws.rasmooplus.service.PaymentInfoService;
import com.client.ws.rasmooplus.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {

    @Value("${webservices.rasplus.default.password}")
    private String defaultPassword;

    private final UserRepository userRepository;
    private final UserPaymentInfoRepository userPaymentInfoRepository;
    private final WsRaspayIntegration wsRaspayIntegration;
//    private final MailIntegration mailIntegration;
    private final UserDetailsRepository userDetailsRepository;
    private final UserTypeRepository userTypeRepository;
    private final SubscriptionTypeRepository subscriptionTypeRepository;

    public PaymentInfoServiceImpl(UserRepository userRepository, UserPaymentInfoRepository userPaymentInfoRepository, WsRaspayIntegration wsRaspayIntegration, UserDetailsRepository userDetailsRepository, UserTypeRepository userTypeRepository, SubscriptionTypeRepository subscriptionTypeRepository) {
        this.userRepository = userRepository;
        this.userPaymentInfoRepository = userPaymentInfoRepository;
        this.wsRaspayIntegration = wsRaspayIntegration;
//        this.mailIntegration = mailIntegration;
        this.userDetailsRepository = userDetailsRepository;
        this.userTypeRepository = userTypeRepository;
        this.subscriptionTypeRepository = subscriptionTypeRepository;
    }

    @Override
    public Boolean process(PaymentProcessDto dto) {
        var userOpt = userRepository.findById(dto.getUserPaymentInfoDto().getUserId());
        if (userOpt.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado");
        }

        User user = userOpt.get();
        if (Objects.nonNull(user.getSubscriptionType())) {
            throw new BusinessException("Pagamento não pode ser processado pois usuário já possui assinatura ativa");
        }

        Boolean successPayment = processPayment(dto, user);
        return processUserCredentials(dto, successPayment, user);
    }

    private boolean processUserCredentials(PaymentProcessDto dto, Boolean successPayment, User user) {
        if (successPayment) {
            createUserPaymentInfo(dto, user);
            createUserCredentials(user);

            updateUserSubscriptionType(dto, user);
//            mailIntegration.send(user.getEmail(), "Usuario: %S - Senha: alunorasmoo", "Acesso Liberado");
            return true;
        }
        return false;
    }

    private Boolean processPayment(PaymentProcessDto dto, User user) {
        CustomerDto customerDto = wsRaspayIntegration.createCustomer(CustomerMapper.build(user));
        OrderDto orderDto = wsRaspayIntegration.createOrder(OrderMapper.build(customerDto.getId(), dto));

        CreditCardDto creditCardDto = CreditCardMapper.build(dto.getUserPaymentInfoDto(), customerDto.getCpf());
        PaymentDto paymentDto = PaymentMapper.build(customerDto.getId(), orderDto.getId(), creditCardDto);
        return wsRaspayIntegration.processPayment(paymentDto);
    }

    private void createUserPaymentInfo(PaymentProcessDto dto, User user) {
        UserPaymentInfo userPaymentInfo = UserPaymentInfoMapper.fromDtoToEntity(dto.getUserPaymentInfoDto(), user);
        userPaymentInfoRepository.save(userPaymentInfo);
    }

    private void createUserCredentials(User user) {
        UserType userType = userTypeRepository.findById(UserTypeEnum.ALUNO.getId())
                .orElseThrow(() -> new NotFoundException("userType não encontrado: " + UserTypeEnum.ALUNO.name()));

        UserCredentials userCredentials = new UserCredentials(null, user.getEmail(), PasswordUtils.encode(defaultPassword), userType);
        userDetailsRepository.save(userCredentials);
    }

    private void updateUserSubscriptionType(PaymentProcessDto dto, User user) {
        SubscriptionType subscriptionType = subscriptionTypeRepository.findByProductKey(dto.getProductKey())
                .orElseThrow(() -> new NotFoundException("SubscriptionType não encontrado: " + dto.getProductKey()));

        user.setSubscriptionType(subscriptionType);
        userRepository.save(user);
    }

}
