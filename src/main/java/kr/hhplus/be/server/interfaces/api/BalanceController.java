package kr.hhplus.be.server.interfaces.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.application.BalanceFacade;
import kr.hhplus.be.server.interfaces.dto.ChargeBalanceRequest;
import kr.hhplus.be.server.interfaces.common.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "balance", description = "잔액 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceFacade balanceFacade;

    @Operation(
            summary = "회원 잔액 조회",
            description = "현재 회원의 잔액을 조회합니다."
    )
    @GetMapping("/members/balance")
    public ApiResponse<Map<String, Long>> getBalance(@Parameter(description = "회원 ID", required = true, example = "1")
                                                     @RequestHeader("x-hhplus-memberId") Long memberId) {
        Long balance = balanceFacade.getBalance(memberId);
        return ApiResponse.ok(Map.of("balance", balance));
    }

    @Operation(
            summary = "회원 잔액 충전",
            description = "현재 회원의 잔액을 충전합니다."
    )
    @PatchMapping("/members/balance")
    public ApiResponse<String> chargeBalance(@Parameter(description = "회원 ID", required = true, example = "1")
                                           @RequestHeader("x-hhplus-memberId") Long memberId,
                                           @Parameter(description = "잔액충전요청바디", required = true)
                                           @RequestBody ChargeBalanceRequest request) {
        balanceFacade.chargeBalance(memberId, request.getChargeAmount());
        return ApiResponse.empty();
    }
}
