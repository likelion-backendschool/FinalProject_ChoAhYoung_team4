package com.ll.exam.final__2022_10_08.service;

import com.ll.exam.final__2022_10_08.app.cart.entity.CartItem;
import com.ll.exam.final__2022_10_08.app.cart.service.CartService;
import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import com.ll.exam.final__2022_10_08.app.member.service.MemberService;
import com.ll.exam.final__2022_10_08.app.product.entity.Product;
import com.ll.exam.final__2022_10_08.app.product.service.ProductService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class CartServiceTests {
    @Autowired
    private CartService cartService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("상품 추가")
    void t1 () {
        // Given
        Member member = memberService.findByUsername("user1").get();
        Product product = productService.findById(3L).get();
        // When
        CartItem cartItem = cartService.addCart(member, product);
        // Then
        assertThat(cartItem.getId()).isEqualTo(3L);
        assertThat(cartItem.getMember()).isEqualTo(member);
        assertThat(cartItem.getProduct()).isEqualTo(product);
    }

    @Test
    @DisplayName("상품 목록 확인")
    void t2() {
        // When
        List<CartItem> cartItemList = cartService.findAllByMemberId(2L);
        // Then
        assertThat(cartItemList.size()).isEqualTo(1);
        assertThat(cartItemList.get(0).getIndexUnique()).isEqualTo(23);
    }


    @Test
    @DisplayName("상품 삭제 확인")
    void t3() {
        // When
        cartService.deleteCartItem(23L);
        List<CartItem> cartItemList = cartService.findAllByMemberId(2L);
        // Then
        assertThat(cartItemList.size()).isEqualTo(0);
    }
}
