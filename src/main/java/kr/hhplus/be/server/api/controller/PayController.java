package kr.hhplus.be.server.api.controller;

import kr.hhplus.be.server.api.common.responses.ApiResponse;
import kr.hhplus.be.server.api.dto.PayRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PayController {

    @PostMapping("/pay")
    public ApiResponse<Void> placeOrder(@RequestHeader("x-hhplus-memberId") Long memberId, @RequestBody PayRequest body) {
        return ApiResponse.empty();
    }
}
