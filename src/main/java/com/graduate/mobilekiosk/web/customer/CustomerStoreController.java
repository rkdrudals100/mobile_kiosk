package com.graduate.mobilekiosk.web.customer;

import com.graduate.mobilekiosk.domain.PurchaseType;
import com.graduate.mobilekiosk.domain.Order;
import com.graduate.mobilekiosk.domain.OrderItem;
import com.graduate.mobilekiosk.web.order.OrderItemService;
import com.graduate.mobilekiosk.web.order.OrderRepository;
import com.graduate.mobilekiosk.web.order.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/customer/shopping-basket")
@RequiredArgsConstructor
public class CustomerStoreController {

    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final OrderService orderService;



    @GetMapping("")
    public String shoppingBasket(HttpServletRequest request, Model model) {
        String user = request.getSession().getId();

        Order order = orderRepository.findWithOrderItemByUser(user);

        model.addAttribute("order", order);
        model.addAttribute("purchaseTypes", PurchaseType.values());

        return "customer/customer-store";
    }



    @PostMapping("")
    public String moveToPayment(HttpServletRequest request){
        String user = request.getSession().getId();
        Order order = orderRepository.findWithOrderItemByUser(user);

        // 주문의 수량 업데이트
        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem orderItem: orderItems) {
            String nameOfOrderItem = "orderItemQuantity" + orderItem.getId();
            orderItem.setItemCount(Integer.parseInt(request.getParameter(nameOfOrderItem)));
            orderService.updateOrderItem(user, orderItem);
        }

        // 주문의 총액 업데이트
        orderService.updateTotalPrice(user);

        // 주문의 매장식사, 포장 여부 업데이트
        PurchaseType purchaseType = orderService.convertTypeOfOrderType(request.getParameter("purchaseTypeCheck"));
        orderService.selectPurchaseType(user,purchaseType);

        return "redirect:/customer/payment";

    }

    @DeleteMapping("/{orderItemId}")
    public String shoppingBasket(HttpServletRequest request, Model model, @PathVariable Long orderItemId) {
        orderItemService.deleteOrderItem(orderItemId);

        return "redirect:";
    }
}
