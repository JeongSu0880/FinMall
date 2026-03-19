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
    @JoinColumn(name = "productId", nullable = false, columnDefinition = "INT UNSIGNED")
    private Product product;

    @Column(nullable = false, length = 500)
    private String imageUrl;

    @Column(nullable = false)
    private boolean isThumbnail;

    @Builder
    public Image(Product product, String imageUrl, boolean isThumbnail) {
        this.product = product;
        this.imageUrl = imageUrl;
        this.isThumbnail = isThumbnail;
    }
}