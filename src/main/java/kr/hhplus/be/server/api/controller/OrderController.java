package kr.hhplus.be.server.api.controller;

import kr.hhplus.be.server.api.common.responses.ApiResponse;
import kr.hhplus.be.server.api.dto.OrderRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderController {

    @PostMapping("/order")
    public ApiResponse<Long> placeOrder(@RequestHeader("x-hhplus-memberId") Long memberId, @RequestBody OrderRequest body) {
        Long orderId = 1L;
        return ApiResponse.ok(orderId);
    }
}
