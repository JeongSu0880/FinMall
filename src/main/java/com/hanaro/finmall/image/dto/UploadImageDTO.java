package com.hanaro.finmall.image.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class UploadImageDTO {
    @NotEmpty
    List<MultipartFile> files;

    @Size(min = 0, max = 30)
    String nickname;
}