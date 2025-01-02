package kr.hhplus.be.server.api.external;

import kr.hhplus.be.server.api.external.dto.OrderRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/external")
public class ExternalController {

    @PostMapping("/send")
    public boolean fakeApi(@RequestBody OrderRequest orderRequest) {
        return true;
    }
}
