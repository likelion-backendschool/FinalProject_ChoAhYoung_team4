package com.ll.exam.final__2022_10_08.app.cart.entity;

import static lombok.AccessLevel.PROTECTED;

import com.ll.exam.final__2022_10_08.app.base.entity.BaseEntity;
import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import com.ll.exam.final__2022_10_08.app.product.entity.Product;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@ToString(callSuper = true)
@NoArgsConstructor(access = PROTECTED)
public class CartItem extends BaseEntity {
    @ManyToOne
    private Member member;
    @ManyToOne
    private Product product;

    private Long indexUnique;

    public CartItem (Member member, Product product) {
        this.member = member;
        this.product = product;
        this.indexUnique = Long.parseLong("%d%d".formatted(member.getId(), product.getId()));
    }
}
