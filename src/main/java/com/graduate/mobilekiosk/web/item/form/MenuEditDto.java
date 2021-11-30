package com.graduate.mobilekiosk.web.item.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MenuEditDto {

    @NotNull(message = "아이디는 반드시 존재해야합니다..")
    private Long id;

    @NotBlank(message = "메뉴 이름은 반드시 존재해야합니다.")
    private String name;

    @Length(min = 0, max = 30)
    private String shortDescription;

    private String description;

    private MultipartFile image;

    private boolean visible;

    @NotNull
    private int price;
}
