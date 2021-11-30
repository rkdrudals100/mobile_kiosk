package com.graduate.mobilekiosk.web.item.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.List;

@Data
public class MenuSaveDto {

    @NotBlank(message = "메뉴 이름은 반드시 존재해야합니다.")
    private String name;

    @Length(min = 0, max = 30)
    private String shortDescription;

    private String description;

    private MultipartFile image;

    private boolean visible = true;

    @NotNull
    private int price;

    @NotBlank
    private String categoryName;

}
