package com.graduate.mobilekiosk.domain;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@ToString(callSuper = true, exclude = {"orderItems"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder @Accessors(chain = true)
public class Item extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private String shortDescription;

    private String description;

    private String image;

    private boolean visible;

    private int price;
    private int sort;

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OptionGroup> optionGroups = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    // 연관관계 편의 메소드
    public void addOptionGroup(OptionGroup optionGroup) {
        optionGroup.setItem(this);
        this.getOptionGroups().add(optionGroup);
    }
}
