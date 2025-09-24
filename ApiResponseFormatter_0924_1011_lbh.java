// 代码生成时间: 2025-09-24 10:11:00
import play.mvc.Result;
import play.mvc.Results;
import play.libs.Json;
import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.Controller;

import java.util.HashMap;
import java.util.Map;

// ApiResponseFormatter类，用于格式化API响应
public class ApiResponseFormatter extends Controller {

    // 格式化成功的API响应
    public Result formatSuccessResponse(String message, int status) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", message);
        return status(status).json(response);
    }

    // 格式化失败的API响应
    public Result formatErrorResponse(String message, int status) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "error");
        response.put("message", message);
        return status(status).json(response);
    }

    // 通用方法，处理API响应并格式化为JSON
    public Result handleApiResult(JsonNode jsonNode, int status) {
        try {
            // 将JsonNode转换为Map
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.putAll(jsonNode.fields());
            return status(status).json(responseMap);
        } catch (Exception e) {
            // 错误处理，返回内部服务器错误
            return formatErrorResponse("Internal server error", 500);
        }
    }

    // 测试方法，用于演示ApiResponseFormatter的使用
    public Result testApiResponseFormatter() {
        // 模拟成功响应
        return formatSuccessResponse("Operation successful", 200);
    }

    // 测试方法，用于演示ApiResponseFormatter的使用
    public Result testErrorResponse() {
        // 模拟错误响应
        return formatErrorResponse("Operation failed", 400);
    }
}
