package kr.hhplus.be.server.interfaces.common;

import kr.hhplus.be.server.domain.coupon.exception.AlreadyUsedCouponException;
import kr.hhplus.be.server.domain.product.exception.ProductOptionOutOfStockException;
import kr.hhplus.be.server.domain.wallet.exception.InsufficientBalanceException;
import kr.hhplus.be.server.interfaces.common.exception.InvalidNumberParamException;
import kr.hhplus.be.server.interfaces.common.exception.NotFoundException;
import kr.hhplus.be.server.interfaces.common.responses.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseEntity<ApiErrorResponse> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(InvalidNumberParamException.class)
    @ResponseBody
    public ResponseEntity<ApiErrorResponse> handleInvalidNumberParamException(InvalidNumberParamException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    @ResponseBody
    public ResponseEntity<ApiErrorResponse> handleInsufficientBalanceException(InsufficientBalanceException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(AlreadyUsedCouponException.class)
    @ResponseBody
    public ResponseEntity<ApiErrorResponse> handleAlreadyUsedCouponException(AlreadyUsedCouponException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(ProductOptionOutOfStockException.class)
    @ResponseBody
    public ResponseEntity<ApiErrorResponse> handleProductOptionOutOfStockException(ProductOptionOutOfStockException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }
}
