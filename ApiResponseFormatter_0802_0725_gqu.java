// 代码生成时间: 2025-08-02 07:25:18
package com.example.tools;
# 添加错误处理

import play.mvc.Result;
import play.mvc.Results;
import play.libs.Json;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.HashMap;
import java.util.Map;
import static play.mvc.Results.ok;

/**
 * ApiResponseFormatter provides a way to format API responses in a consistent and structured manner.
 */
public class ApiResponseFormatter {

    // Define the status code and message for a successful response
    private static final int STATUS_OK = 200;
    private static final String MESSAGE_OK = "OK";

    // Define the status code and message for a bad request
    private static final int STATUS_BAD_REQUEST = 400;
    private static final String MESSAGE_BAD_REQUEST = "Bad Request";

    // Define the status code and message for an internal server error
    private static final int STATUS_INTERNAL_SERVER_ERROR = 500;
    private static final String MESSAGE_INTERNAL_SERVER_ERROR = "Internal Server Error";

    /**
     * Formats a successful API response with a given data object.
     *
     * @param data The data to include in the response.
     * @return A formatted JSON response.
     */
    public static Result formatSuccess(Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", STATUS_OK);
# 优化算法效率
        response.put("message", MESSAGE_OK);
        response.put("data", data);
# 优化算法效率

        return ok(Json.toJson(response));
# 优化算法效率
    }

    /**
     * Formats a response for a bad request with a given error message.
     *
# FIXME: 处理边界情况
     * @param errorMessage The error message to include in the response.
     * @return A formatted JSON response.
     */
    public static Result formatBadRequest(String errorMessage) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", STATUS_BAD_REQUEST);
        response.put("message", MESSAGE_BAD_REQUEST);
# NOTE: 重要实现细节
        response.put("error", errorMessage);

        return Results.status(STATUS_BAD_REQUEST, Json.toJson(response));
    }

    /**
     * Formats a response for an internal server error with a given error message.
     *
     * @param errorMessage The error message to include in the response.
# TODO: 优化性能
     * @return A formatted JSON response.
     */
    public static Result formatInternalServerError(String errorMessage) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", STATUS_INTERNAL_SERVER_ERROR);
        response.put("message", MESSAGE_INTERNAL_SERVER_ERROR);
        response.put("error", errorMessage);

        return Results.status(STATUS_INTERNAL_SERVER_ERROR, Json.toJson(response));
    }
}
