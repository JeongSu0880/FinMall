package com.hanaro.finmall.user;

import com.hanaro.finmall.user.dto.UpdateUserRequestDTO;
import com.hanaro.finmall.user.dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    public UserResponseDTO getUser(Long id) {
        return userMapper.toResponse(userRepository.findById(id).orElseThrow());
    }

    @Transactional
    public void updateUser(Long id, UpdateUserRequestDTO request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (request.getUsername() != null) {
            user.changeUsername(request.getUsername());
        }

        if (request.getBirthDate() != null) {
            user.changeBirthDate(request.getBirthDate());
        }
    }
}