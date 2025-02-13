package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.controller.SubscriptionTypeController;
import com.client.ws.rasmooplus.dto.SubscriptionTypeDto;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.mapper.SubscriptionTypeMapper;
import com.client.ws.rasmooplus.model.mysql.SubscriptionType;
import com.client.ws.rasmooplus.repository.mysql.SubscriptionTypeRepository;
import com.client.ws.rasmooplus.service.SubscriptionTypeService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SubscriptionTypeServiceImpl implements SubscriptionTypeService {

    private static final String UPDATE = "update";
    private static final String DELETE = "delete";

    private final SubscriptionTypeRepository subscriptionTypeRepository;

    public SubscriptionTypeServiceImpl(SubscriptionTypeRepository subscriptionTypeRepository) {
        this.subscriptionTypeRepository = subscriptionTypeRepository;
    }

    @Cacheable(value = "subscriptionType")
    @Override
    public List<SubscriptionType> findAll() {
        return subscriptionTypeRepository.findAll();
    }

    @Cacheable(value = "subscriptionType", key = "#id")
    @Override
    public SubscriptionType findById(Long id) {
        return getSubscriptionType(id).add(WebMvcLinkBuilder.linkTo(
             WebMvcLinkBuilder.methodOn(SubscriptionTypeController.class).findById(id))
                .withSelfRel()
        ).add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(SubscriptionTypeController.class).update(id, new SubscriptionTypeDto()))
                .withRel(UPDATE)
        ).add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(SubscriptionTypeController.class).delete(id))
                .withRel(DELETE)
        );
    }

    @CacheEvict(value = "subscriptionType", allEntries = true)
    @Override
    public SubscriptionType create(SubscriptionTypeDto dto) {
        if (Objects.nonNull(dto.getId())) {
            throw new BadRequestException("Id deve ser nulo");
        }

        SubscriptionType subscriptionType = SubscriptionTypeMapper.fromDtoToEntity(dto);
        return subscriptionTypeRepository.save(subscriptionType);
    }

    @CacheEvict(value = "subscriptionType", allEntries = true)
    @Override
    public SubscriptionType update(Long id, SubscriptionTypeDto dto) {
        SubscriptionType subscriptionType = getSubscriptionType(id);
        subscriptionType.setName(dto.getName());
        subscriptionType.setAccessMonths(dto.getAccessMonths());
        subscriptionType.setPrice(dto.getPrice());
        return subscriptionTypeRepository.save(subscriptionType);
    }

    @CacheEvict(value = "subscriptionType", allEntries = true)
    @Override
    public void delete(Long id) {
        getSubscriptionType(id);
        subscriptionTypeRepository.deleteById(id);
    }

    private SubscriptionType getSubscriptionType(Long id) {
        Optional<SubscriptionType> subscriptionType = subscriptionTypeRepository.findById(id);
        if (subscriptionType.isEmpty()) {
            throw new NotFoundException("SubscriptionType not found");
        }
        return subscriptionType.get();
    }
}
