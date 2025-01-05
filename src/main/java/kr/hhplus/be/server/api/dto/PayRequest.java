package kr.hhplus.be.server.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PayRequest {

    private Long orderId;
    private Long totalRegularPrice;
    private Long totalDiscountPrice;
    private Long totalSalePrice;
}
