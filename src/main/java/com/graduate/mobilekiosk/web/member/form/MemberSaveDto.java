package com.graduate.mobilekiosk.web.member.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter @Setter
public class MemberSaveDto {
    @NotBlank
    @Pattern(regexp="[a-z|A-Z|0-9]{4,20}", message = "영어나 숫자로 4~20자리 이내로 입력해주세요.")
    private String username;

    @NotBlank
    @Size(min = 4, max = 20, message = "비밀번호는 4~20 자리 사이로 입력해주세요.")
    private String password;

    @NotBlank
    private String checkPassword;

    @NotBlank
    @Pattern(regexp="[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9| ]{1,10}", message = "특수문자는 사용할 수 없습니다.")
    private String storeName;
}
