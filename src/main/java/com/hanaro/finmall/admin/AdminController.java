package com.hanaro.finmall.admin;

import com.hanaro.finmall.account.AccountService;
import com.hanaro.finmall.account.dto.AccountResponse;
import com.hanaro.finmall.product.ProductService;
import com.hanaro.finmall.product.dto.ProductResponse;
import com.hanaro.finmall.user.UserService;
import com.hanaro.finmall.user.dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ProductService productService;
    private final AccountService accountService;
    private final UserService userService;


    @GetMapping("/products")
    public List<ProductResponse> getProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/accounts")
    public List<AccountResponse> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/users")
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }


}