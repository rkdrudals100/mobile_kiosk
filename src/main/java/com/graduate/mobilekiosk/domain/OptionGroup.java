package com.graduate.mobilekiosk.domain;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@ToString(callSuper = true, exclude = {})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder @Accessors(chain = true)
public class OptionGroup {

    @Id
    @GeneratedValue
    @Column(name = "optionGroup_id")
    private Long id;
    private String name;
    private boolean essential;
    private boolean multiple;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @OneToMany(mappedBy = "optionGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options = new ArrayList<>();

    // 연관관계 편의 메소드
    public void addOption(Option option) {
        option.setOptionGroup(this);
        this.getOptions().add(option);
    }
}
