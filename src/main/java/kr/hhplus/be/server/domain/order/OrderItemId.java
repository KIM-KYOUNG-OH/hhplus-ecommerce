package kr.hhplus.be.server.domain.order;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemId implements Serializable {

    private Long orderId;
    private Long productId;
    private Long productOptionId;
}
