package kr.hhplus.be.server.interfaces.common.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private T data;

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(data);
    }

    public static <T> ApiResponse<T> empty() {
        return new ApiResponse<>(null);
    }
}
