package kr.hhplus.be.server.domain.external;

import kr.hhplus.be.server.interfaces.dto.OrderExternalRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "externalApi", url = "http://localhost:8080/api/external")
public interface ExternalApiFeignClient {

    @PostMapping("/send")
    boolean sendOrder(@RequestBody OrderExternalRequest request);
}
