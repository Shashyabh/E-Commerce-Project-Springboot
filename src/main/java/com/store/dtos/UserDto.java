package com.store.dtos;

import com.store.entities.Role;
import com.store.validation.ImageNameValid;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String userId;

    @Size(min = 3,max = 15,message = "Invalid Name")
    //Swagger ApiModelProperty
    @ApiModelProperty(value = "User_name",name = "username",required = true,notes = "Username of new user")
    private String name;

    @Pattern(regexp = "^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$",message = "User email invalid")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @Size(min=4,max = 6,message = "Invalid gender")
    private String gender;

    @NotBlank(message = "About should not be blanked")
    private String about;

    @ImageNameValid
    private String imageName;

    private Set<RoleDto> roles=new HashSet<>();
}
