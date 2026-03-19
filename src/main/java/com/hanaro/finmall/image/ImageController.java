package com.hanaro.finmall.image;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(
            @RequestParam("file") MultipartFile file) {
        String savedPath = imageService.upload(file);
        return ResponseEntity.ok(savedPath);
    }

    @PostMapping("/upload/multiple")
    public ResponseEntity<List<String>> uploadMultiple(
            @RequestParam("files") List<MultipartFile> files) {
        List<String> savedPaths = files.stream()
                .map(imageService::upload)
                .toList();
        return ResponseEntity.ok(savedPaths);
    }
}