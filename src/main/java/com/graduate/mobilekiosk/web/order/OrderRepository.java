package com.graduate.mobilekiosk.web.order;

import com.graduate.mobilekiosk.domain.Member;
import com.graduate.mobilekiosk.domain.Order;
import com.graduate.mobilekiosk.domain.OrderStatus;
import com.graduate.mobilekiosk.domain.PurchaseType;
import net.bytebuddy.TypeCache;
import org.aspectj.weaver.ast.Or;
import org.hibernate.annotations.Where;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = {"orders"})
    Optional<Order> findById(Long id);

    @EntityGraph(attributePaths = {"orderItems", "orderItems.item"}, type = EntityGraph.EntityGraphType.LOAD)
    Order findWithOrderItemByUser(String user);

    Order findByUser(String user);

    List<Order> findByMemberAndPurchase(Member id, String purchase);

    @Query("select o from Order o where o.purchase = 'purchase' and o.orderStatus in ('ACCEPT', 'ORDER') and o.member = :member")
    List<Order> findByMemberAndEffectiveOrders(@Param("member") Member member);

    @Query("select o from Order o where o.purchase = 'purchase' and o.orderStatus in ('ACCEPT', 'ORDER') and o.member = :member")
    List<Order> findByMemberAndEffectiveOrders(@Param("member") Member member, Sort sort);
}
