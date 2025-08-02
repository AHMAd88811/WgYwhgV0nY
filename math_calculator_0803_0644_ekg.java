// 代码生成时间: 2025-08-03 06:44:55
package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.BodyParser;
import play.libs.Json;
import java.util.HashMap;
import java.util.Map;
import static play.libs.Json.toJson;

/**
 * This class provides a set of mathematical operations.
 */
public class MathCalculator extends Controller {

    /**
     * Handles the request to perform addition.
     *
     * @return A JSON result of the addition.
     */
    @BodyParser.Of(BodyParser.Json.class)
    public Result add() {
        Map<String, Double> jsonBody = request().body().asJson().toMap();
        try {
            double result = jsonBody.get("a") + jsonBody.get("b");
            return ok(Json.newObject()
                    .put("result", result)
                    .put("operation", "add"));
        } catch (Exception e) {
            return badRequest("Invalid input for addition");
        }
    }

    /**
     * Handles the request to perform subtraction.
     *
     * @return A JSON result of the subtraction.
     */
    @BodyParser.Of(BodyParser.Json.class)
    public Result subtract() {
        Map<String, Double> jsonBody = request().body().asJson().toMap();
        try {
            double result = jsonBody.get("a") - jsonBody.get("b");
            return ok(Json.newObject()
                    .put("result", result)
                    .put("operation", "subtract"));
        } catch (Exception e) {
            return badRequest("Invalid input for subtraction");
        }
    }

    /**
     * Handles the request to perform multiplication.
     *
     * @return A JSON result of the multiplication.
     */
    @BodyParser.Of(BodyParser.Json.class)
    public Result multiply() {
        Map<String, Double> jsonBody = request().body().asJson().toMap();
        try {
            double result = jsonBody.get("a") * jsonBody.get("b");
            return ok(Json.newObject()
                    .put("result", result)
                    .put("operation", "multiply"));
        } catch (Exception e) {
            return badRequest("Invalid input for multiplication");
        }
    }

    /**
     * Handles the request to perform division.
     *
     * @return A JSON result of the division.
     */
    @BodyParser.Of(BodyParser.Json.class)
    public Result divide() {
        Map<String, Object> jsonBody = request().body().asJson().toMap();
        try {
            double a = (double) jsonBody.get("a");
            double b = (double) jsonBody.get("b");
            if (b == 0) {
                return badRequest("Cannot divide by zero");
            }

            double result = a / b;
            return ok(Json.newObject()
                    .put("result", result)
                    .put("operation", "divide"));
        } catch (Exception e) {
            return badRequest("Invalid input for division");
        }
    }
}
