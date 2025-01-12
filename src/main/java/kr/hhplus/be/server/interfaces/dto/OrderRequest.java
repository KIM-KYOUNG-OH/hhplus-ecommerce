package kr.hhplus.be.server.interfaces.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Schema(description = "주문 요청 바디")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @Schema(description = "주문 아이템 목록")
    private List<OrderItem> orderItems;

    @Schema(description = "적용된 쿠폰 ID", example = "1")
    private Long appliedCouponId;

    @Getter
    @Setter
    public static class OrderItem {

        @Schema(description = "상품 ID", example = "1")
        private Long productId;

        @Schema(description = "상품 옵션 ID", example = "1")
        private Long productOptionId;

        @Schema(description = "주문 갯수", example = "10")
        private Long orderCount;
    }
}
