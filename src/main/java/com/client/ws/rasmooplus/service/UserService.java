package com.client.ws.rasmooplus.service;

import com.client.ws.rasmooplus.dto.UserDto;
import com.client.ws.rasmooplus.model.mysql.User;

public interface UserService {

    User create(UserDto dto);
}
