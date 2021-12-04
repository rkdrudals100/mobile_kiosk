package com.graduate.mobilekiosk.domain;

import lombok.*;
import lombok.experimental.Accessors;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@ToString(callSuper = true, exclude = {})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder @Accessors(chain = true)
public class Customer extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "customer_id")
    private Long id;
    private String uuid;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();
}
