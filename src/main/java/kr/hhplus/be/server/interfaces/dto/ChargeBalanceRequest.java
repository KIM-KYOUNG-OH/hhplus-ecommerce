package kr.hhplus.be.server.interfaces.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "잔액 충전 요청 바디")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChargeBalanceRequest {

    @Schema(description = "충전 금액 (자연수만 가능)", example = "1000")
    private Long chargeAmount;
}
