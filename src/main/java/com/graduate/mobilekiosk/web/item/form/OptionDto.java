package com.graduate.mobilekiosk.web.item.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class OptionDto {

    @NotNull
    private Long optionGroup;

    @NotNull
    private Long itemId;

    @NotBlank
    private String optionName;

    @NotNull
    private int optionPrice;
}
