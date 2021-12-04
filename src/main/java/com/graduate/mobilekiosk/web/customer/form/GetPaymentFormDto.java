package com.graduate.mobilekiosk.web.customer.form;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GetPaymentFormDto {
    private String requirements;
    private List<String> whichPayment = new ArrayList();
}
