// 代码生成时间: 2025-08-15 02:59:34
package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.BodyParser;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.HashMap;
import java.util.Map;

// MathCalculator 类提供了一个简单的数学计算工具集
public class MathCalculator extends Controller {

    // 计算两个数字的加法
    public Result add() {
        JsonNode requestJson = request().body().asJson();
        if (requestJson == null || !requestJson.has("a") || !requestJson.has("b")) {
            return badRequest("Missing parameters");
        }
        double a = requestJson.get("a").asDouble();
        double b = requestJson.get("b").asDouble();
        double sum = a + b;
        return ok(sum);
    }

    // 计算两个数字的减法
    public Result subtract() {
        JsonNode requestJson = request().body().asJson();
        if (requestJson == null || !requestJson.has("a") || !requestJson.has("b")) {
            return badRequest("Missing parameters");
        }
        double a = requestJson.get("a").asDouble();
        double b = requestJson.get("b").asDouble();
        double difference = a - b;
        return ok(difference);
    }

    // 计算两个数字的乘法
    public Result multiply() {
        JsonNode requestJson = request().body().asJson();
        if (requestJson == null || !requestJson.has("a") || !requestJson.has("b")) {
            return badRequest("Missing parameters");
        }
        double a = requestJson.get("a").asDouble();
        double b = requestJson.get("b").asDouble();
        double product = a * b;
        return ok(product);
    }

    // 计算两个数字的除法
    public Result divide() {
        JsonNode requestJson = request().body().asJson();
        if (requestJson == null || !requestJson.has("a") || !requestJson.has("b") || requestJson.get("b").asDouble() == 0) {
            return badRequest("Missing parameters or division by zero");
        }
        double a = requestJson.get("a").asDouble();
        double b = requestJson.get("b").asDouble();
        double quotient = a / b;
        return ok(quotient);
    }

}