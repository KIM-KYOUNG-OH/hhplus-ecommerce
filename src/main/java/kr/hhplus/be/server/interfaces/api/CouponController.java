package kr.hhplus.be.server.interfaces.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.interfaces.common.responses.ApiResponse;
import kr.hhplus.be.server.interfaces.dto.IssueCouponRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "coupons", description = "쿠폰 API")
@RestController
@RequestMapping("/api")
public class CouponController {

    @Operation(
            summary = "보유 쿠폰 목록 조회",
            description = "현재 회원이 보유한 쿠폰 목록을 조회합니다."
    )
    @GetMapping("/coupons")
    public ApiResponse<Map<String, Object>> getCoupons(@Parameter(description = "회원 ID", required = true)
                                                       @RequestHeader("x-hhplus-memberId") Long memberId,
                                                       @Parameter(description = "현재 페이지", required = true)
                                                       @RequestParam(value = "page") Long page,
                                                       @Parameter(description = "페이지 크기", required = true)
                                                       @RequestParam(value = "size") Long size) {
        return ApiResponse.ok(Map.of(
                "currentPage", page,
                "pageSize", size,
                "coupons", List.of(
                        Map.of("couponId", 1L, "couponName", "10000원 할인 쿠폰", "discountType", "FIXED", "discountAmount", 10000L, "quantity", 10L),
                        Map.of("couponId", 2L, "couponName", "10프로 할인 쿠폰", "discountType", "PERCENT", "discountAmount", 10L, "quantity", 15L)
                )
        ));
    }

    @Operation(
            summary = "쿠폰 발급",
            description = "현재 회원에게 쿠폰을 발급합니다."
    )
    @PostMapping("/coupons")
    public ApiResponse<String> issueCoupon(@Parameter(description = "회원 ID", required = true)
                                         @RequestHeader("x-hhplus-memberId") Long memberId,
                                         @Parameter(description = "쿠폰 발급 요청 바디", required = true)
                                         @RequestBody IssueCouponRequest request) {
        return ApiResponse.empty();
    }
}
