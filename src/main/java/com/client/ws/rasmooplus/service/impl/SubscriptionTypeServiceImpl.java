package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.dto.SubscriptionTypeDto;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.model.SubscriptionType;
import com.client.ws.rasmooplus.repository.SubscriptionTypeRepository;
import com.client.ws.rasmooplus.service.SubscriptionTypeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SubscriptionTypeServiceImpl implements SubscriptionTypeService {

    private final SubscriptionTypeRepository subscriptionTypeRepository;

    public SubscriptionTypeServiceImpl(SubscriptionTypeRepository subscriptionTypeRepository) {
        this.subscriptionTypeRepository = subscriptionTypeRepository;
    }


    @Override
    public List<SubscriptionType> findAll() {
        return subscriptionTypeRepository.findAll();
    }

    @Override
    public SubscriptionType findById(Long id) {
        return getSubscriptionType(id);
    }

    @Override
    public SubscriptionType create(SubscriptionTypeDto dto) {
        if (Objects.nonNull(dto.getId())) {
            throw new BadRequestException("Id deve ser nulo");
        }

        SubscriptionType subscriptionType = new SubscriptionType(
                dto.getId(),
                dto.getName(),
                dto.getAccessMonth(),
                dto.getPrice(),
                dto.getProductKey()
        );
        return subscriptionTypeRepository.save(subscriptionType);
    }

    @Override
    public SubscriptionType update(Long id, SubscriptionTypeDto dto) {
        SubscriptionType subscriptionType = getSubscriptionType(id);
        subscriptionType.setName(dto.getName());
        subscriptionType.setAccessMonth(dto.getAccessMonth());
        subscriptionType.setPrice(dto.getPrice());
        return subscriptionTypeRepository.save(subscriptionType);
    }

    @Override
    public void delete(Long id) {

    }

    private SubscriptionType getSubscriptionType(Long id) {
        Optional<SubscriptionType> subscriptionType = subscriptionTypeRepository.findById(id);
        if (subscriptionType.isEmpty()) {
            throw new NotFoundException("SubscriptionType not found");
        }
        return subscriptionType.get();
    }
}
