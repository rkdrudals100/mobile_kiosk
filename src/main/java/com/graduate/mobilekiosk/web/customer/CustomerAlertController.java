package com.graduate.mobilekiosk.web.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
@RequestMapping("/customer/customer-alert")
@RequiredArgsConstructor
public class CustomerAlertController {

    @GetMapping("")
    public String Alert(HttpServletRequest request, Model model) {
        String user = request.getSession().getId();

        return "customer/customer-alert";
    }
}
