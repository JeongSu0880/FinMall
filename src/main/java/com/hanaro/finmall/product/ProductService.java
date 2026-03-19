package com.hanaro.finmall.product;

import com.hanaro.finmall.product.dto.ProductCreateRequestDTO;
import com.hanaro.finmall.product.dto.ProductResponseDTO;
import com.hanaro.finmall.product.dto.ProductUpdateRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    public Long create(ProductCreateRequestDTO req) {
        Product product = productMapper.toEntity(req);

        productRepository.save(product);

        return product.getId();
    }

    public Long update(Long id, ProductUpdateRequestDTO req) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("상품이 존재하지 않습니다."));

        updateIfNotNull(req.getName(), product::changeName);
        updateIfNotNull(req.getMinPaymentAmount(), product::changeMinPaymentAmount);
        updateIfNotNull(req.getMaxPaymentAmount(), product::changeMaxPaymentAmount);
        updateIfNotNull(req.getBaseRate(), product::changeBaseRate);
        updateIfNotNull(req.getEarlyWithrawalRate(), product::changeEarlyWithdrawalRate);
        updateIfNotNull(req.getMinAge(), product::changeMinAge);
        updateIfNotNull(req.getMaxAge(), product::changeMaxAge);
        updateIfNotNull(req.getDepositProtectionLimit(), product::changeDepositProtectionLimit);

        return product.getId();
    }

    public Integer delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("상품 존재 하지 않아요."));

        productRepository.delete(product);
        return 1;
    }

    public ProductResponseDTO get(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("상품이 존재하지 않습니다."));

        return productMapper.toResponse(product);
    }

    private <T> void updateIfNotNull(T value, java.util.function.Consumer<T> updater) {
        if (value != null) {
            updater.accept(value);
        }
    }
}