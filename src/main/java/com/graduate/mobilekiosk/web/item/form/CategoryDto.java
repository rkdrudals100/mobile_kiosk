package com.graduate.mobilekiosk.web.item.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CategoryDto {

    @NotBlank
    private String categoryName;
}
