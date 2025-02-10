package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.dto.PaymentProcessDto;
import com.client.ws.rasmooplus.dto.wsraspay.CreditCardDto;
import com.client.ws.rasmooplus.dto.wsraspay.OrderDto;
import com.client.ws.rasmooplus.dto.wsraspay.PaymentDto;
import com.client.ws.rasmooplus.enums.UserTypeEnum;
import com.client.ws.rasmooplus.exception.BusinessException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.integration.MailIntegration;
import com.client.ws.rasmooplus.integration.WsRaspayIntegration;
import com.client.ws.rasmooplus.mapper.UserPaymentInfoMapper;
import com.client.ws.rasmooplus.mapper.wsraspay.CreditCardMapper;
import com.client.ws.rasmooplus.mapper.wsraspay.CustomerMapper;
import com.client.ws.rasmooplus.mapper.wsraspay.OrderMapper;
import com.client.ws.rasmooplus.mapper.wsraspay.PaymentMapper;
import com.client.ws.rasmooplus.model.User;
import com.client.ws.rasmooplus.model.UserCredentials;
import com.client.ws.rasmooplus.model.UserPaymentInfo;
import com.client.ws.rasmooplus.model.UserType;
import com.client.ws.rasmooplus.repository.UserDetailsRepository;
import com.client.ws.rasmooplus.repository.UserPaymentInfoRepository;
import com.client.ws.rasmooplus.repository.UserRepository;
import com.client.ws.rasmooplus.repository.UserTypeRepository;
import com.client.ws.rasmooplus.service.PaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import com.client.ws.rasmooplus.dto.wsraspay.CustomerDto;

@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {

    @Value("${webservices.rasplus.default.password}")
    private String defaultPassword;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final UserPaymentInfoRepository userPaymentInfoRepository;
    private final WsRaspayIntegration wsRaspayIntegration;
//    private final MailIntegration mailIntegration;
    private final UserDetailsRepository userDetailsRepository;
    private final UserTypeRepository userTypeRepository;

    public PaymentInfoServiceImpl(UserRepository userRepository, UserPaymentInfoRepository userPaymentInfoRepository, WsRaspayIntegration wsRaspayIntegration, UserDetailsRepository userDetailsRepository, UserTypeRepository userTypeRepository) {
        this.userRepository = userRepository;
        this.userPaymentInfoRepository = userPaymentInfoRepository;
        this.wsRaspayIntegration = wsRaspayIntegration;
//        this.mailIntegration = mailIntegration;
        this.userDetailsRepository = userDetailsRepository;
        this.userTypeRepository = userTypeRepository;
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

        CustomerDto customerDto = wsRaspayIntegration.createCustomer(CustomerMapper.build(user));
        OrderDto orderDto = wsRaspayIntegration.createOrder(OrderMapper.build(customerDto.getId(), dto));

        CreditCardDto creditCardDto = CreditCardMapper.build(dto.getUserPaymentInfoDto(), customerDto.getCpf());
        PaymentDto paymentDto = PaymentMapper.build(customerDto.getId(), orderDto.getId(), creditCardDto);
        Boolean successPayment = wsRaspayIntegration.processPayment(paymentDto);

        if (successPayment) {
            UserPaymentInfo userPaymentInfo = UserPaymentInfoMapper.fromDtoToEntity(dto.getUserPaymentInfoDto(), user);
            userPaymentInfoRepository.save(userPaymentInfo);

            UserType userType = userTypeRepository.findById(UserTypeEnum.ALUNO.getId())
                    .orElseThrow(() -> new NotFoundException("Tipo de usuário não encontrado: " + UserTypeEnum.ALUNO.name()));

            UserCredentials userCredentials = new UserCredentials(null, user.getEmail(), passwordEncoder.encode(defaultPassword), userType);
            userDetailsRepository.save(userCredentials);

//            mailIntegration.send(user.getEmail(), "Usuario: %S - Senha: alunorasmoo", "Acesso Liberado");
            return true;
        }

        return false;
    }
}
