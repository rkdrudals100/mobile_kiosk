package com.graduate.mobilekiosk.web.item;

import com.graduate.mobilekiosk.domain.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CategoryRepository extends JpaRepository<Category, Long> {

    @EntityGraph(attributePaths = {"items"})
    List<Category> findByUserName(String name);

    @Query("select distinct c from Category c join fetch c.items i where c.userName = :name and i.visible = true")
    List<Category> findVisibleByUserName(@Param("name") String name);

    Category findByNameAndUserName(String categoryName, String userName);
}
