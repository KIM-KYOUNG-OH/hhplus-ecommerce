package kr.hhplus.be.server.interfaces.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.application.CouponFacade;
import kr.hhplus.be.server.interfaces.common.responses.ApiResponse;
import kr.hhplus.be.server.interfaces.dto.MyCouponDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "coupons", description = "쿠폰 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CouponController {

    private final CouponFacade couponFacade;

    @Operation(
            summary = "보유 쿠폰 목록 조회",
            description = "현재 회원이 보유한 쿠폰 목록을 조회합니다."
    )
    @GetMapping("/coupons")
    public ApiResponse<List<MyCouponDto>> getCoupons(@Parameter(description = "회원 ID", required = true, example = "1")
                                                     @RequestHeader("x-hhplus-memberId")
                                                     Long memberId,
                                                     @Parameter(description = "현재 페이지", required = true, example = "1")
                                                     @RequestParam(value = "pageNumber", defaultValue = "1")
                                                     int pageNumber,
                                                     @Parameter(description = "페이지 크기", required = true, example = "10")
                                                     @RequestParam(value = "size", defaultValue = "10")
                                                     int size) {
        Pageable page = PageRequest.of(pageNumber - 1, size);
        Page<MyCouponDto> response = couponFacade.getCoupons(memberId, page).map(MyCouponDto::from);
        return ApiResponse.ok(response.getContent());
    }

    @Operation(
            summary = "쿠폰 발급",
            description = "현재 회원에게 쿠폰을 발급합니다."
    )
    @PostMapping("/coupons/{couponId}")
    public ApiResponse<String> issueCoupon(@Parameter(description = "회원 ID", required = true, example = "1")
                                           @RequestHeader("x-hhplus-memberId")
                                           Long memberId,
                                           @Parameter(description = "발급할 쿠폰 ID", required = true, example = "1")
                                           @PathVariable(name = "couponId")
                                           Long couponId) {
        couponFacade.issueCoupon(memberId, couponId);
        return ApiResponse.empty();
    }
}
