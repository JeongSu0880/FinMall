package com.hanaro.finmall.account;

import com.hanaro.finmall.account.dto.AccountSearchDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByUserId(Long userId);

    @Query("""
                SELECT a FROM Account a
                JOIN a.product p
                WHERE a.user.id = :userId
                AND (:#{#search.productId} IS NULL OR p.id = :#{#search.productId})
                AND (:#{#search.productName} IS NULL OR p.name LIKE %:#{#search.productName}%)
                AND (:#{#search.accountNumber} IS NULL OR a.accountNumber = :#{#search.accountNumber})
                AND (:#{#search.status} IS NULL OR a.status = :#{#search.status})
            """)
    List<Account> search(@Param("userId") Long userId,
                         @Param("search") AccountSearchDTO search);

    boolean existsByAccountNumber(String accountNumber);
}