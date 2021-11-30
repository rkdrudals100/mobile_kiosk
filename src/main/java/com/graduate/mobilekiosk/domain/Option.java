package com.graduate.mobilekiosk.domain;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity @Table(name = "options")
@Data
@ToString(callSuper = true, exclude = {})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder @Accessors(chain = true)
public class Option extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "option_id")
    private Long id;

    private String name;
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "optionGroup_id")
    private OptionGroup optionGroup;

}
