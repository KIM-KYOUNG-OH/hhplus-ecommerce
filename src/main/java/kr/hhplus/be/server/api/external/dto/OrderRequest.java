package kr.hhplus.be.server.api.external.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    private long totalRegularPrice;
    private long totalDiscountPrice;
    private long totalSalePrice;
    private List<OrderItem> orderItems;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItem {
        private long productId;
        private long productOptionId;
        private long orderCount;
        private long regularPrice;
        private long discountPrice;
        private long salePrice;
        private long appliedCouponId;
    }
}
