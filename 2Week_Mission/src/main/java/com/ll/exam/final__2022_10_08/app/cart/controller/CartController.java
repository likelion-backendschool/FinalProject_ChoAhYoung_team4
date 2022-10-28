package com.ll.exam.final__2022_10_08.app.cart.controller;

import com.ll.exam.final__2022_10_08.app.base.exception.NotFoundException;
import com.ll.exam.final__2022_10_08.app.base.rq.Rq;
import com.ll.exam.final__2022_10_08.app.cart.entity.CartItem;
import com.ll.exam.final__2022_10_08.app.cart.service.CartService;
import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import com.ll.exam.final__2022_10_08.app.member.service.MemberService;
import com.ll.exam.final__2022_10_08.app.product.entity.Product;
import com.ll.exam.final__2022_10_08.app.product.service.ProductService;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final Rq rq;
    private final CartService cartService;
    private final MemberService memberService;
    private final ProductService productService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/add/{productId}")
    public String addCart (@PathVariable Long productId, Principal principal) {
        Member member = memberService.findByUsername(principal.getName()).orElse(null);
        if (member == null) {
            throw new NotFoundException();
        }

        Product product = productService.findById(productId).orElse(null);
        if (product == null) {
            throw  new NotFoundException();
        }
        cartService.addCart(member, product);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/list")
    public String showCartItemList(Model model) {
        List<CartItem> cartItemList = cartService.findAllByMemberId(rq.getId());
        model.addAttribute("cartItemList", cartItemList);
        return "cart/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{uniqueId}")
    public String deleteCartItem(@PathVariable Long uniqueId) {
        cartService.deleteCartItem(uniqueId);
        return "redirect:/cart/list";
    }
}
