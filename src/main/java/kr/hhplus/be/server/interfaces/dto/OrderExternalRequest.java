package kr.hhplus.be.server.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderExternalRequest {

    private Long totalRegularPrice;
    private Long totalDiscountPrice;
    private Long totalSalePrice;
    private List<OrderItem> orderItems;

    @Getter
    @Setter
    public static class OrderItem {
        private Long productId;
        private Long productOptionId;
        private Long orderCount;
        private Long regularPrice;
        private Long discountPrice;
        private Long salePrice;
        private Long appliedCouponId;
    }
}
