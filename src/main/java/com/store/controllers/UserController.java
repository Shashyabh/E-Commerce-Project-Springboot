package com.store.controllers;

import com.store.dtos.ApiResponse;
import com.store.dtos.ImageResponse;
import com.store.dtos.PageableResponse;
import com.store.dtos.UserDto;
import com.store.services.FileService;
import com.store.services.UserServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
@Api(value = "User Controller" ,description = "RestAPIs related to perform User Operation")//Swagger
public class UserController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;
    //Create User
    @ApiOperation(value = "This Api use for creating User",response = ResponseEntity.class)
    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto userDto1 = this.userServices.createUser(userDto);
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }

    //Get All users

    @ApiOperation(value = "This Api use for Updating User",response = ResponseEntity.class)
    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDto> updateUser( @Valid @PathVariable String userId,@RequestBody UserDto userDto){
        UserDto userDto1 = this.userServices.updateUser(userDto, userId);
        return new ResponseEntity<>(userDto1,HttpStatus.OK);
    }

    //Delete user and images'
    @ApiOperation(value = "This Api use for Deleting User",response = ResponseEntity.class)
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId){
        this.userServices.deleteUser(userId);
        ApiResponse message = ApiResponse
                .builder()
                .message("User deleted successfully")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

//    @GetMapping("/getUsers")
//    public ResponseEntity<List<UserDto>> getAllUsers
//            (@RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
//             @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
//             @RequestParam(value = "sortBy",defaultValue = "name",required = false) String sortBy,
//             @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir){
//        return new ResponseEntity<>(userServices.getAllUser(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
//    }

    @ApiOperation(value = "This Api use for get all User",response = ResponseEntity.class)
    @GetMapping("/getUsers")
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers
            (@RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
             @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
             @RequestParam(value = "sortBy",defaultValue = "name",required = false) String sortBy,
             @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir){
        return new ResponseEntity<>(userServices.getAllUser(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
    }

    @ApiOperation(value = "This Api use for getting Single User",response = ResponseEntity.class)
    @GetMapping("/userId/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId){
        return new ResponseEntity<>(userServices.getUserById(userId),HttpStatus.OK);
    }

    //Get user by email

    @ApiOperation(value = "This Api use for getting User by email",response = ResponseEntity.class)
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByemail(@PathVariable String email){
        return new ResponseEntity<>(userServices.getUserByEmail(email),HttpStatus.OK);
    }

    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> getUserBySearch(@PathVariable String keywords){
        return new ResponseEntity<>(userServices.searchUser(keywords),HttpStatus.OK);
    }

    //Upload user image

    @ApiOperation(value = "This Api use for uploading a imgae",response = ResponseEntity.class)
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@PathVariable String userId,@RequestParam("userImage")MultipartFile image) throws IOException {
        String imageName = fileService.uploadImage(image, imageUploadPath);
        UserDto user= userServices.getUserById(userId);
        user.setImageName(imageName);
        UserDto userDto = userServices.updateUser(user, userId);
        ImageResponse imageResponse=ImageResponse.builder().imageName(imageName).success(true).status(HttpStatus.CREATED).message("Created").build();
        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }

    //Serve user image
    @ApiOperation(value = "This Api use for serving a imgae",response = ResponseEntity.class)
    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable String userId, HttpServletResponse httpServletResponse) throws IOException {
        UserDto user = userServices.getUserById(userId);
        InputStream resource = fileService.getResource(imageUploadPath, user.getImageName());
        httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,httpServletResponse.getOutputStream());
    }
}
