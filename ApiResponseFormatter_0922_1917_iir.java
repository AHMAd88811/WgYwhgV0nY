// 代码生成时间: 2025-09-22 19:17:40
package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import play.libs.Json;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// ApiResponseFormatter is a helper class to format API responses in a consistent way.
public class ApiResponseFormatter extends Controller {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Formats a successful API response with a given data object.
    public static Result formatSuccess(Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", data);

        try {
            return Results.ok(Json.toJson(response));
        } catch (JsonProcessingException e) {
            // Log the error and return an internal server error response.
            return Results.internalServerError("Error processing JSON");
        }
    }

    // Formats an API response with a given message and optional data object.
    public static Result formatError(String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", message);
        if (data != null) {
            response.put("data", data);
        }

        try {
            return Results.badRequest(Json.toJson(response));
        } catch (JsonProcessingException e) {
            // Log the error and return an internal server error response.
            return Results.internalServerError("Error processing JSON");
        }
    }

    // Convenience method to create an error response without additional data.
    public static Result formatError(String message) {
        return formatError(message, null);
    }
}
