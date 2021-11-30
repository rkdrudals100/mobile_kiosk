package com.graduate.mobilekiosk.web.item;

import com.graduate.mobilekiosk.domain.Item;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface ItemRepository extends JpaRepository<Item, Long> {

    @EntityGraph(attributePaths = {"item"})
    Item findWithItemById(Long menuId);
}
