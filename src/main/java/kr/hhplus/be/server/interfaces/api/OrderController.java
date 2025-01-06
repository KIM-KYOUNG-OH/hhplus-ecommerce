package kr.hhplus.be.server.interfaces.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.interfaces.common.responses.ApiResponse;
import kr.hhplus.be.server.interfaces.dto.OrderRequest;
import org.springframework.web.bind.annotation.*;

@Tag(name = "order", description = "주문 API")
@RestController
@RequestMapping("/api")
public class OrderController {

    @Operation(
            summary = "주문",
            description = "현재 회원으로 상품을 주문하고 주문 ID를 반환합니다."
    )
    @PostMapping("/order")
    public ApiResponse<Long> placeOrder(@Parameter(description = "회원 ID", required = true)
                                        @RequestHeader("x-hhplus-memberId") Long memberId,
                                        @Parameter(description = "주문 요청 바디", required = true)
                                        @RequestBody OrderRequest request) {
        Long orderId = 1L;
        return ApiResponse.ok(orderId);
    }
}
