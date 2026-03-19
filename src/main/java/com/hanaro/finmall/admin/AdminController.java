package com.hanaro.finmall.admin;

import com.hanaro.finmall.account.AccountService;
import com.hanaro.finmall.account.dto.AccountResponseDTO;
import com.hanaro.finmall.product.ProductService;
import com.hanaro.finmall.product.dto.ProductCreateRequestDTO;
import com.hanaro.finmall.product.dto.ProductResponseDTO;
import com.hanaro.finmall.product.dto.ProductUpdateRequestDTO;
import com.hanaro.finmall.user.UserService;
import com.hanaro.finmall.user.dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ProductService productService;
    private final AccountService accountService;
    private final UserService userService;


    @GetMapping("/products")
    public List<ProductResponseDTO> getProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/accounts")
    public List<AccountResponseDTO> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/users")
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Long create(@RequestBody ProductCreateRequestDTO req) {
        return productService.create(req);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Long update(@PathVariable Long id,
                       @RequestBody ProductUpdateRequestDTO req) {
        return productService.update(id, req);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Integer delete(@PathVariable Long id) {
        return productService.delete(id);
    }

}