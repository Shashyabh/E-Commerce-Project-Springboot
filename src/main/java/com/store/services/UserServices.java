package com.store.services;

import com.store.dtos.PageableResponse;
import com.store.dtos.UserDto;
import com.store.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserServices {
    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, String userId);

    void deleteUser(String userId);

    PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);

    UserDto getUserById(String userId);

    UserDto getUserByEmail(String email);

    List<UserDto> searchUser(String keyword);

    //For google Api
    Optional<User> findUserByEmailOptional(String email);
}
