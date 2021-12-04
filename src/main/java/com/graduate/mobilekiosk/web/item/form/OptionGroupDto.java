package com.graduate.mobilekiosk.web.item.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class OptionGroupDto {

    @NotNull
    private Long itemId;

    private String optionGroupName;

    private boolean essential;

    private boolean multiple;
}
