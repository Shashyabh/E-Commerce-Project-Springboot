package com.store.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private String categoryId;

    @NotBlank
    private String title;

    @NotBlank(message = "Description required")
    private String description;

    @NotBlank(message = "Cover Image should not be blank")
    private String coverImage;
}
