package com.graduate.mobilekiosk.web.order;

import com.graduate.mobilekiosk.domain.Item;
import com.graduate.mobilekiosk.domain.Order;
import com.graduate.mobilekiosk.domain.OrderItem;
import com.graduate.mobilekiosk.web.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;

    public void createOrderItem(Order order, Long menuId) {
        Item item = itemRepository.findById(menuId).get();
        OrderItem orderItem = OrderItem.builder()
                .item(item)
                .orderItemPrice(item.getPrice())
                .order(order)
                .build();

        orderItemRepository.save(orderItem);
    }

    public void deleteOrderItem(Long orderItemId) {
        orderItemRepository.deleteById(orderItemId);
    }
}
