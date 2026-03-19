package com.hanaro.finmall.user;

import com.hanaro.finmall.common.security.UserAuthDTO;
import com.hanaro.finmall.user.dto.UpdateUserRequestDTO;
import com.hanaro.finmall.user.dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserResponseDTO getMe(@AuthenticationPrincipal UserAuthDTO user) {
        return userService.getUser(user.getId());
    }

    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @GetMapping("/{id}")
    public UserResponseDTO getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PatchMapping("/users/me")
    public ResponseEntity<Void> updateMyInfo(
            @AuthenticationPrincipal UserAuthDTO user,
            @RequestBody UpdateUserRequestDTO request
    ) {
        userService.updateUser(user.getId(), request);
        return ResponseEntity.noContent().build();
    }
}