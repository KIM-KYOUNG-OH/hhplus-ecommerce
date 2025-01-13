package kr.hhplus.be.server.domain.product.exception;

public class ProductOptionOutOfStockException extends RuntimeException {

    public ProductOptionOutOfStockException(String message) {
        super(message);
    }
}
