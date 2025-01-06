package kr.hhplus.be.server.interfaces.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.interfaces.dto.ChargeBalanceRequest;
import kr.hhplus.be.server.interfaces.common.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "balance", description = "잔액 API")
@RestController
@RequestMapping("/api")
public class BalanceController {

    @Operation(
            summary = "회원 잔액 조회",
            description = "현재 회원의 잔액을 조회합니다."
    )
    @GetMapping("/members/balance")
    public ApiResponse<Map<String, Long>> getBalance(@Parameter(description = "회원 ID", required = true)
                                                     @RequestHeader("x-hhplus-memberId") Long memberId) {
        return ApiResponse.ok(Map.of("balance", 50000L));
    }

    @Operation(
            summary = "회원 잔액 충전",
            description = "현재 회원의 잔액을 충전합니다."
    )
    @PatchMapping("/members/balance")
    public ApiResponse<Void> chargeBalance(@Parameter(description = "회원 ID", required = true)
                                           @RequestHeader("x-hhplus-memberId") Long memberId,
                                           @Parameter(description = "잔액충전요청바디", required = true)
                                           @RequestBody ChargeBalanceRequest request) {
        return ApiResponse.empty();
    }
}
