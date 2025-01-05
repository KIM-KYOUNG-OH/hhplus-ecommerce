package kr.hhplus.be.server.api.controller;

import kr.hhplus.be.server.api.common.responses.ApiResponse;
import kr.hhplus.be.server.api.dto.IssueCouponRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CouponController {

    @GetMapping("/coupons")
    public ApiResponse<Map<String, Object>> getCoupons(@RequestHeader("x-hhplus-memberId") Long memberId, @RequestParam Integer page, @RequestParam Integer size) {
        return ApiResponse.ok(Map.of(
                "currentPage", page,
                "pageSize", size,
                "coupons", List.of(
                        Map.of("couponId", 1L, "couponName", "10000원 할인 쿠폰", "discountType", "FIXED", "discountAmount", 10000L, "quantity", 10L),
                        Map.of("couponId", 2L, "couponName", "10프로 할인 쿠폰", "discountType", "PERCENT", "discountAmount", 10L, "quantity", 15L)
                )
        ));
    }

    @PostMapping("/coupons")
    public ApiResponse<Void> issueCoupon(@RequestHeader("x-hhplus-memberId") Long memberId, @RequestBody IssueCouponRequest body) {
        return ApiResponse.empty();
    }
}
