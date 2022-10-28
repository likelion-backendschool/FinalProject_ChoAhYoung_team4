package com.ll.exam.final__2022_10_08.app.order.entity;

import com.ll.exam.final__2022_10_08.app.cart.entity.CartItem;
import com.ll.exam.final__2022_10_08.app.cart.service.CartService;
import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartService cartService;
    private final OrderRepository orderRepository;
    /*
    * 장바구니에 있는 상품 목록을 가지고온다.
    * */
    public Order createFromCart (Member member) {
        List<CartItem> cartItems = cartService.findAllByMemberId(member.getId());
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            orderItems.add(new OrderItem(cartItem.getProduct()));
            cartService.deleteCartItem(cartItem.getIndexUnique());
        }

        return create(member, orderItems);
    }

    private Order create(Member member, List<OrderItem> orderItems) {
        Order order = Order
            .builder()
            .member(member)
            .build();
        for (OrderItem orderItem: orderItems) {
            order.addOrderItem(orderItem);
        }

        orderRepository.save(order);

        return order;
    }
}
