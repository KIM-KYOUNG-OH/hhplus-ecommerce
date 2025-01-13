package kr.hhplus.be.server.domain.coupon.exception;

public class AlreadyUsedCouponException extends RuntimeException {

    public AlreadyUsedCouponException(String message) {
        super(message);
    }
}
