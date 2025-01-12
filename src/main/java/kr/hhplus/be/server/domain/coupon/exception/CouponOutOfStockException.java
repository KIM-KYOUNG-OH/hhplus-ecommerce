package kr.hhplus.be.server.domain.coupon.exception;

public class CouponOutOfStockException extends RuntimeException {

    public CouponOutOfStockException(String message) {
        super(message);
    }
}
