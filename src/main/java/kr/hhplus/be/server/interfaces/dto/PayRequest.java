package kr.hhplus.be.server.interfaces.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "결제 요청 바디")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PayRequest {

    @Schema(description = "주문 ID", example = "1")
    private Long orderId;

    @Schema(description = "총 정가", example = "10000")
    private Long totalRegularPrice;

    @Schema(description = "총 할인가", example = "3000")
    private Long totalDiscountPrice;

    @Schema(description = "총 판매가", example = "7000")
    private Long totalSalePrice;
}
