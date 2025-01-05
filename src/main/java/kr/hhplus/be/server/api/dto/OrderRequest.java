package kr.hhplus.be.server.api.dto;

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

    private List<OrderItem> orderItems;
    private Long appliedCouponId;

    @Getter
    @Setter
    public static class OrderItem {

        private Long productId;
        private Long productOptionId;
        private Long orderCount;
    }
}
