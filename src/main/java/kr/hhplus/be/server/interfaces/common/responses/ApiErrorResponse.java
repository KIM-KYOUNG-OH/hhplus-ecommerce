package kr.hhplus.be.server.interfaces.common.responses;

public record ApiErrorResponse(
        int code,
        String message
) {
}
