package com.graduate.mobilekiosk.web.customer;

import com.graduate.mobilekiosk.domain.Category;
import com.graduate.mobilekiosk.domain.Item;
import com.graduate.mobilekiosk.domain.Order;
import com.graduate.mobilekiosk.domain.OrderItem;
import com.graduate.mobilekiosk.web.item.ItemRepository;
import com.graduate.mobilekiosk.web.order.OrderItemService;
import com.graduate.mobilekiosk.web.order.OrderRepository;
import com.graduate.mobilekiosk.web.order.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerItemController {

    private final ItemRepository itemrepository;
    private final OrderItemService orderItemService;
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @GetMapping("items/{itemId}")
    public String moveItem(@PathVariable Long itemId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();

        Item item = itemrepository.findById(itemId).get();
        model.addAttribute("item", item);

        return"customer/customer-item.html";
    }

    @PostMapping("items/{itemId}")
    public String customerAdd(@PathVariable Long itemId, Model model, HttpServletRequest request, @RequestParam String url, @RequestParam String options) {
        String user = request.getSession().getId();

        Order order = orderService.createOrder(user, url);

        //이미 처리되었던 주문인지 검사
        if (order.getOrderStatus() != null) {
            log.warn("이미 완료된 주문입니다.");

            return "redirect:/customer/customer-alert";
        }

        List<Long> ops = new ArrayList<>();

        if (!options.equals("")) {
            String[] option = options.split(" ");
            Arrays.stream(option).forEach(op -> {
                ops.add(Long.parseLong(op));
            });
        }

        // Order안에 아이템 ID, 옵션까지 같은 OrderItem이 있는지 있는지 검사
        if (order.getOrderItems() != null) {
            for (OrderItem each : order.getOrderItems()) {
                log.warn("아이디: {}", each.getItem().getId());
                if (each.getItem().getId() == itemId) {
                    log.warn("같은 아이디의 아이템 있음");
                    if (each.getOptions().equals(orderItemService.makeStringOption(ops))) {
                        log.warn("같은 옵션 있음");
                        return "redirect:/customer/" + url;
                    }
                }
            }
        }

        orderItemService.createOrderItem(order, itemId, ops);

        return "redirect:/customer/" + url;
    }
}