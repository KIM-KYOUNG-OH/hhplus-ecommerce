package kr.hhplus.be.server.interfaces.dto;

import kr.hhplus.be.server.domain.coupon.MyCoupon;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyCouponDto {

    private Long couponIssuedId;
    private CouponDto coupon;
    private boolean isUsed;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    private static ModelMapper modelMapper = new ModelMapper();

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CouponDto {

        private Long couponId;
        private String couponName;
        private String discountType;
        private Long discountAmount;
        private Long quantity;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    public static MyCouponDto from(MyCoupon myCoupon) {
        return modelMapper.map(myCoupon, MyCouponDto.class);
    }
}
