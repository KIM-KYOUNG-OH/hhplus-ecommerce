package kr.hhplus.be.server.api.controller;

import kr.hhplus.be.server.api.dto.ChargeBalanceRequest;
import kr.hhplus.be.server.api.common.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class BalanceController {

    @GetMapping("/members/balance")
    public ApiResponse<Map<String, Long>> getBalance(@RequestHeader("x-hhplus-memberId") Long memberId) {
        return ApiResponse.ok(Map.of("balance", 50000L));
    }

    @PatchMapping("/members/balance")
    public ApiResponse<Void> chargeBalance(@RequestHeader("x-hhplus-memberId") Long memberId,  @RequestBody ChargeBalanceRequest body) {
        return ApiResponse.empty();
    }
}
