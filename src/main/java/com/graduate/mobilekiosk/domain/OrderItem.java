package com.graduate.mobilekiosk.domain;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"order", "item"})
@AllArgsConstructor
@Builder @Accessors(chain = true)
public class OrderItem extends BaseEntity {


    @Id
    @GeneratedValue
    @Column(name = "orderItem_id")
    private Long id;

    private int orderItemPrice;

    private String options;

    private int itemCount;

    private String description;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne()
    @JoinColumn(name = "item_id")
    private Item item;

    public void changeOrderItem(OrderItem orderItem){
        this.orderItemPrice = orderItem.orderItemPrice;
        this.itemCount = orderItem.itemCount;
        this.description = orderItem.description;
    }

    public String toString() {
        return  item.getName() + ": " + itemCount + "개(" + options.trim() +")\n";
    }
}
