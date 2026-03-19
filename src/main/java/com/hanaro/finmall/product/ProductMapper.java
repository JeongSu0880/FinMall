package com.hanaro.finmall.product;

import com.hanaro.finmall.image.Image;
import com.hanaro.finmall.product.dto.ProductCreateRequestDTO;
import com.hanaro.finmall.product.dto.ProductResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "images", ignore = true)
    Product toEntity(ProductCreateRequestDTO dto);

    //    @Mapping(target = "imageUrl", expression = "java(getThumbnailUrl(product))")
    ProductResponseDTO toResponse(Product product);

    default String getThumbnailUrl(Product product) {
        return product.getImages().stream()
                .filter(Image::isThumbnail)
                .findFirst()
                .map(Image::getImageUrl)
                .orElse(null);
    }
}