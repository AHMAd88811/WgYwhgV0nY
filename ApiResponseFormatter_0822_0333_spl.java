// 代码生成时间: 2025-08-22 03:33:16
package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import play.libs.Json;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

/**
 * ApiResponseFormatter provides a utility for formatting API responses in a consistent manner.
 */
public class ApiResponseFormatter extends Controller {

    /**
     * Formats the API response as a JSON object.
     *
     * @param data The data to be included in the response body.
     * @param <T>  The type of the data.
     * @return A Result object wrapping the formatted JSON response.
     */
    public static <T> Result formatResponse(T data) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.valueToTree(data);
            return Results.ok(Json.toJson(jsonNode));
        } catch (IOException e) {
            // Log the error and return a 500 Internal Server Error response.
            return internalServerError("Error formatting response: " + e.getMessage());
        }
    }

    /**
     * Formats an API response with a specific status code.
     *
     * @param data    The data to be included in the response body.
     * @param status  The HTTP status code for the response.
     * @param <T>     The type of the data.
     * @return A Result object wrapping the formatted JSON response.
     */
    public static <T> Result formatResponse(T data, int status) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.valueToTree(data);
            return status(status, Json.toJson(jsonNode));
        } catch (IOException e) {
            // Log the error and return a 500 Internal Server Error response.
            return internalServerError("Error formatting response: " + e.getMessage());
        }
    }

    /**
     * Formats an API response with an error message.
     *
     * @param message The error message to include in the response body.
     * @return A Result object wrapping the error message.
     */
    public static Result formatErrorResponse(String message) {
        return status(Results.INTERNAL_SERVER_ERROR, Json.toJson(message));
    }
}
