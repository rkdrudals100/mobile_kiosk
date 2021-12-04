package com.graduate.mobilekiosk.web.order;

import com.graduate.mobilekiosk.domain.Item;
import com.graduate.mobilekiosk.domain.Option;
import com.graduate.mobilekiosk.domain.Order;
import com.graduate.mobilekiosk.domain.OrderItem;
import com.graduate.mobilekiosk.web.item.ItemRepository;
import com.graduate.mobilekiosk.web.option.OptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OptionRepository optionRepository;
    private final ItemRepository itemRepository;

    public void createOrderItem(Order order, Long menuId, List<Long> option) {

        String stringOption = "";
        int totalPrice = 0;
        if (!option.isEmpty()) {
            List<Option> checkOptions = optionRepository.findWithCheckOptionsById(option);
            totalPrice = checkOptions.stream().mapToInt(Option::getPrice).sum();
            for (Option op : checkOptions) {
                stringOption += op.getName() +  "  ";
            }
        }


        Item item = itemRepository.findById(menuId).get();
        OrderItem orderItem = OrderItem.builder()
                .item(item)
                .options(stringOption)
                .orderItemPrice(item.getPrice() + totalPrice)
                .order(order)
                .build();

        orderItemRepository.save(orderItem);
    }


    public String makeStringOption(List<Long> option) {

        String stringOption = "";
        if (!option.isEmpty()) {
            List<Option> checkOptions = optionRepository.findWithCheckOptionsById(option);
            for (Option op : checkOptions) {
                stringOption += op.getName() + "  ";
            }
        }
        return stringOption;
    }


    public void deleteOrderItem(Long orderItemId) {
        orderItemRepository.deleteById(orderItemId);
    }
}
