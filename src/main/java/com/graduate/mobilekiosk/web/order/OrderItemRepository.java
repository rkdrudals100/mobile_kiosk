package com.graduate.mobilekiosk.web.order;

import com.graduate.mobilekiosk.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long> { }
