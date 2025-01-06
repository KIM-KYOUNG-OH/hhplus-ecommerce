package kr.hhplus.be.server.interfaces.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.interfaces.common.responses.ApiResponse;
import kr.hhplus.be.server.interfaces.dto.PayRequest;
import org.springframework.web.bind.annotation.*;

@Tag(name = "pay", description = "결제 API")
@RestController
@RequestMapping("/api")
public class PayController {

    @Operation(
            summary = "결제",
            description = "주문 API 호출후 발급된 주문 ID를 이용하여 결제를 진행합니다."
    )
    @PostMapping("/pay")
    public ApiResponse<Void> placeOrder(@Parameter(description = "회원 ID", required = true)
                                        @RequestHeader("x-hhplus-memberId") Long memberId,
                                        @Parameter(description = "결제 요청 바디", required = true)
                                        @RequestBody PayRequest request) {
        return ApiResponse.empty();
    }
}
