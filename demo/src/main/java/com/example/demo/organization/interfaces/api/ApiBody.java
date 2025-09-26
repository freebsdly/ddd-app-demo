package com.example.demo.organization.interfaces.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "API Body")
@Data
@AllArgsConstructor
public class ApiBody<T>
{
    @Schema(description = "错误码")
    private int code;
    @Schema(description = "错误信息")
    private String message;
    @Schema(description = "数据")
    private T data;

    public static <T> ApiBody<T> success(T data)
    {
        return new ApiBody<>(0, "success", data);
    }

    public static <T> ApiBody<T> failure(int code, String message)
    {
        return new ApiBody<>(code, message, null);
    }
}
