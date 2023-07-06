package com.store.services.impl;

import com.store.dtos.PageableResponse;
import com.store.dtos.UserDto;
import com.store.entities.Role;
import com.store.entities.User;
import com.store.exceptions.ResourceNotFoundException;
import com.store.helper.Helper;
import com.store.repositories.RoleRepo;
import com.store.repositories.UserRepo;
import com.store.services.UserServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserServices {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${user.profile.image.path}")
    private String imagePath;

    @Value("${normal.role.id}")
    private String normalRoleId;

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public UserDto createUser(UserDto userDto) {
        String userId=UUID.randomUUID().toString();
        userDto.setUserId(userId);
        //Encoding password
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        User user=modelMapper.map(userDto,User.class);
        //Fetch the role of normal and set it to user
        Role role = roleRepo.findById(normalRoleId).get();

        user.getRoles().add(role);

        User savedUser = this.userRepo.save(user);
        UserDto userDto1=modelMapper.map(savedUser,UserDto.class);
        return userDto1;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setEmail(userDto.getEmail());
        user.setImageName(userDto.getImageName());
        User savedUser = this.userRepo.save(user);

        UserDto userDto1=modelMapper.map(savedUser,UserDto.class);
        return userDto1;
    }

    @Override
    public void deleteUser(String userId) {

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        //Delete image profile path
        //imagePath=====>>image/user/
        //fullPath=====>>>image/user/abc.png
        String fullPath = imagePath + user.getImageName();

        try{
            Path path= Paths.get(fullPath);
            Files.delete(path);
        }catch (NoSuchFileException ex){
            ex.printStackTrace();
        }catch (IOException ex){
            ex.printStackTrace();
        }

        this.userRepo.delete(user);
    }

//    @Override
//    public List<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {
//        Sort sort = sortDir.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
//
//
//        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
//        Page<User> page=this.userRepo.findAll(pageable);
//        List<User> users=page.getContent();
//
//        List<UserDto> userDtoStream = users.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
//
//        return userDtoStream;
//    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();


        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<User> page=this.userRepo.findAll(pageable);
//        List<User> users=page.getContent();
//
//        List<UserDto> userDtoStream = users.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
//
//        PageableResponse<UserDto> response=new PageableResponse<>();
//        response.setContent(userDtoStream);
//        response.setPageNumber(page.getNumber());
//        response.setPageSize(page.getSize());
//        response.setTotalElements(page.getTotalElements());
//        response.setTotalPages(page.getTotalPages());
//        response.setLastPage(page.isLast());
        PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);
        return response;
    }

    @Override
    public UserDto getUserById(String userId) {

        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with Id"));
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with this email"));
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<User> byNameContaining = userRepo.findByNameContaining(keyword);
        List<UserDto> userDtoStream = byNameContaining.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        return userDtoStream;
    }

    @Override
    public Optional<User> findUserByEmailOptional(String email) {
        return userRepo.findByEmail(email);
    }
}
