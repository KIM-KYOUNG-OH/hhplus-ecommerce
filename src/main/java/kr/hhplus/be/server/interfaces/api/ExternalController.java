package kr.hhplus.be.server.interfaces.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.hhplus.be.server.interfaces.dto.OrderExternalRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Schema(name = "외부 API", hidden = true)
@RestController
@RequestMapping("/api/external")
public class ExternalController {

    @Operation(summary = "외부 API", hidden = true)
    @PostMapping("/send")
    public boolean fakeApi(@RequestBody OrderExternalRequest request) {
        return true;
    }
}
