// 代码生成时间: 2025-08-16 00:32:11
import play.mvc.*;
    import play.libs.Json;
    import com.fasterxml.jackson.databind.JsonNode;
    import com.fasterxml.jackson.core.JsonProcessingException;
    import java.util.HashMap;
    import java.util.Map;
    
    /**
     * Utility class for formatting API responses in a Play Framework application.
     */
    public class ApiResponseFormatter {
    
        /**
         * Formats a successful API response.
         *
         * @param result The data to be included in the response.
         * @return A formatted JSON response indicating success.
         */
        public static Result formatSuccess(Object result) {
            try {
                JsonNode jsonNode = Json.toJson(result);
                Map<String, Object> response = new HashMap<>();
                response.put("status", "success");
                response.put("data", jsonNode);
                return ok(Json.toJson(response));
            } catch (JsonProcessingException e) {
                return handleException(e);
            }
        }
    
        /**
         * Formats an error API response.
         *
         * @param errorCode The error code identifying the type of error.
         * @param message The error message to be displayed.
         * @return A formatted JSON response indicating an error.
         */
        public static Result formatError(String errorCode, String message) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("errorCode", errorCode);
            response.put("message", message);
            return status(BAD_REQUEST, Json.toJson(response));
        }
    
        /**
         * Handles any exceptions that occur during the formatting process.
         *
         * @param e The exception to handle.
         * @return A formatted JSON response indicating an internal server error.
         */
        public static Result handleException(Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("errorCode", "SERVER_ERROR");
            response.put("message", e.getMessage());
            return status(INTERNAL_SERVER_ERROR, Json.toJson(response));
        }
    
        /**
         * Helper method to create a JSON response with a specific status code.
         *
         * @param statusCode The HTTP status code to return.
         * @param json The JSON content of the response.
         * @return A Result object containing the JSON response with the specified status code.
         */
        private static Result status(int statusCode, JsonNode json) {
            return new DefaultResult(new StatusHeader(statusCode, ""), json, new Header[]{}, new HttpEntity.Streamed(new ByteArrayInputStream(json.toString().getBytes()), HttpEntity.NO_FLUSH), new java.util.HashMap<>());
        }
    
        /**
         * Helper method to return an OK response with JSON content.
         *
         * @param json The JSON content of the response.
         * @return A Result object containing the JSON response with a 200 OK status code.
         */
        private static Result ok(JsonNode json) {
            return status(OK, json);
        }
    }