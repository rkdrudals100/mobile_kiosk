package com.graduate.mobilekiosk.web.order;

import com.graduate.mobilekiosk.domain.Member;
import com.graduate.mobilekiosk.domain.Order;
import com.graduate.mobilekiosk.domain.OrderStatus;
import com.graduate.mobilekiosk.web.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/order-management")
public class OrderController {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final OrderService orderService;

    @GetMapping
    public String moveOrder(Model model, Principal principal) {

        Member member = memberRepository.findByUserId(principal.getName());

        List<Order> orders = orderRepository.findByMemberAndEffectiveOrders(member);

        model.addAttribute("orders", orders);

        return "seller/order-management";
    }
    /****자세히 버튼 누를 시 세부 내용 출력****/


    @GetMapping("/agree")
    public String orderAgree(@RequestParam Long orderId) {

        Order changedOrder = orderRepository.getById(orderId);
        orderService.changeOrderStatus(changedOrder, OrderStatus.ACCEPT);

        return "redirect:";
    }

    @GetMapping("/sold")
    public String orderSold(@RequestParam Long orderId) {

        Order changedOrder = orderRepository.getById(orderId);
        orderService.sold(changedOrder);
        return  "redirect:";
    }

    @GetMapping("/refuse")
    public String orderRefuse(HttpServletRequest request, @RequestParam String selbox, @RequestParam String selboxDirect, @RequestParam Long orderId) {

        Order order = orderRepository.getById(orderId);

        orderService.changeReasonOfRefuse(order, selbox, selboxDirect);
        orderService.changeOrderStatus(order, OrderStatus.REFUSE);

        return "redirect:";
    }



    /*****거절일 경우 OrderStatus(거절 사유) 상태 변경 후 DB 전송, 리다이렉트 (사라짐) ******/




}
