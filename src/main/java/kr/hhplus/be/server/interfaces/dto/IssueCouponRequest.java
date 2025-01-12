package kr.hhplus.be.server.interfaces.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "쿠폰 발급 요청 바디")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IssueCouponRequest {

    @Schema(description = "쿠폰 ID (자연수만 가능)", example = "1")
    private Long couponId;

    @Schema(description = "발급 갯수 (자연수만 가능)", example = "1")
    private Long quantity;
}
