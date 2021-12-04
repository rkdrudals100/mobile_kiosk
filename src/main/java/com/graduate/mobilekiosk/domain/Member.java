package com.graduate.mobilekiosk.domain;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "members")
@Data @ToString(callSuper = true, exclude = {})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder @Accessors(chain = true)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String userId;

    private String password;

    @Column(unique = true)
    private String storeName;

    private String role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> categories = new ArrayList<>();

    // 연관관계 편의 메소드
    public void addOrder(Order order) {
        order.setMember(this);
        this.getOrders().add(order);
    }

    public void addCategories(Category category) {
        category.setMember(this);
        this.getCategories().add(category);
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }
}
