package com.hanaro.finmall.image.entity;

import com.hanaro.finmall.common.BaseEntity;
import com.hanaro.finmall.product.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Image")
public class Image extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private Product product;

    @Column(name = "thumbnail_path", nullable = false, length = 255)
    private String thumbnailPath;

    @Column(name = "image_path", nullable = false, length = 255)
    private String imagePath;

    @Builder
    public Image(Product product, String thumbnailPath, String imagePath) {
        this.product = product;
        this.thumbnailPath = thumbnailPath;
        this.imagePath = imagePath;
    }
}