package com.ll.exam.final__2022_10_08.app.cart.service;

import com.ll.exam.final__2022_10_08.app.base.exception.NotFoundException;
import com.ll.exam.final__2022_10_08.app.cart.entity.CartItem;
import com.ll.exam.final__2022_10_08.app.cart.repository.CartRepository;
import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import com.ll.exam.final__2022_10_08.app.product.entity.Product;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    public CartItem addCart(Member member, Product product) {
        CartItem cartItem = new CartItem(member, product);
        return cartRepository.save(cartItem);
    }

    public List<CartItem> findAllByMemberId(long id) {
        return cartRepository.findAllByMemberId(id);
    }

    public void deleteCartItem(Long uniqueId) {
        CartItem cartItem = cartRepository.findByIndexUnique(uniqueId).orElse(null);

        if (cartItem == null) {
            throw new NotFoundException();
        }
        cartRepository.delete(cartItem);
    }
}