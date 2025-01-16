package kr.hhplus.be.server.infrastructure.product;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.domain.product.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT po
        FROM ProductOption po
        JOIN FETCH Product p on p.productId = po.product.productId
        WHERE po.productOptionId = :productOptionId
    """)
    Optional<ProductOption> findByIdWithLock(@Param("productOptionId") Long productOptionId);
}
