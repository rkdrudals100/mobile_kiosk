package com.graduate.mobilekiosk.web.customer;

import com.graduate.mobilekiosk.domain.Order;
import com.graduate.mobilekiosk.domain.OrderStatus;
import com.graduate.mobilekiosk.domain.WhichPayment;
import com.graduate.mobilekiosk.web.customer.form.GetPaymentFormDto;
import com.graduate.mobilekiosk.web.order.OrderRepository;
import com.graduate.mobilekiosk.web.order.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping("/customer/payment")
@RequiredArgsConstructor
public class CustomerPaymentController {

    private final OrderRepository orderRepository;
    private final OrderService orderService;



    @GetMapping("")
    public String paymentHome(HttpServletRequest request, Model model) {
        String user = request.getSession().getId();
        Order order = orderRepository.findWithOrderItemByUser(user);

        model.addAttribute("GetPaymentFormDto", new GetPaymentFormDto());
        model.addAttribute("order", order);

        return "customer/customer-payment";
    }



    @PostMapping("")
    public String payment(HttpServletRequest request, GetPaymentFormDto getPaymentFormDto) {
        String user = request.getSession().getId();

        // 구매자 구매 확정 상태로 업데이트
        orderService.purchase(user);

        // 고객 요구사항 업데이트
        orderService.changeRequirements(user, getPaymentFormDto.getRequirements());

        // 결제수단 업데이트
        WhichPayment whichPayment = orderService.convertTypeOfWhichPayment(getPaymentFormDto.getWhichPayment().get(0));
        orderService.changeWhichPayment(user, whichPayment);

        // 판매자 수락 상태 ORDER으로 업데이트
        orderService.changeOrderStatus(user, OrderStatus.ORDER);

        return "redirect:/customer/customer-alert";
    }
}
