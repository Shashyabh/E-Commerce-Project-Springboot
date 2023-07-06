package com.store.helper;

import com.store.dtos.PageableResponse;
import com.store.dtos.UserDto;
import com.store.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {
//    @Autowired
//    private static ModelMapper modelMapper;
    //U->Entity      V->Dtos

    public static <U,V>PageableResponse<V> getPageableResponse(Page<U> page,Class<V>type){
        List<U> users=page.getContent();

        List<V> userDtoStream = users.stream().map(object -> new ModelMapper().map(object, type)).collect(Collectors.toList());
        PageableResponse<V> response=new PageableResponse<>();
        response.setContent(userDtoStream);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());
        return response;
    }
}
