// 代码生成时间: 2025-09-10 09:02:00
package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.libs.Json;
import java.util.HashMap;
import java.util.Map;
import static play.mvc.Results.ok;

/**
 * Controller for handling math utility operations.
 */
public class MathUtilityController extends Controller {

    /**
     * Handles the addition operation request.
     *
     * @param a The first operand.
     * @param b The second operand.
     * @return A JSON object containing the result of the addition.
     */
    public Result add(String a, String b) {
        try {
            double result = Double.parseDouble(a) + Double.parseDouble(b);
            return ok(Json.newObject().put("result", result));
        } catch (NumberFormatException e) {
            return ok(Json.newObject().put("error", "Invalid input for addition operation"));
        }
    }

    /**
     * Handles the subtraction operation request.
     *
     * @param a The first operand.
     * @param b The second operand.
     * @return A JSON object containing the result of the subtraction.
     */
    public Result subtract(String a, String b) {
        try {
            double result = Double.parseDouble(a) - Double.parseDouble(b);
            return ok(Json.newObject().put("result", result));
        } catch (NumberFormatException e) {
            return ok(Json.newObject().put("error", "Invalid input for subtraction operation"));
        }
    }

    /**
     * Handles the multiplication operation request.
     *
     * @param a The first operand.
     * @param b The second operand.
     * @return A JSON object containing the result of the multiplication.
     */
    public Result multiply(String a, String b) {
        try {
            double result = Double.parseDouble(a) * Double.parseDouble(b);
            return ok(Json.newObject().put("result", result));
        } catch (NumberFormatException e) {
            return ok(Json.newObject().put("error", "Invalid input for multiplication operation"));
        }
    }

    /**
     * Handles the division operation request.
     *
     * @param a The first operand.
     * @param b The second operand.
     * @return A JSON object containing the result of the division or an error message if division by zero occurs.
     */
    public Result divide(String a, String b) {
        try {
            double result = Double.parseDouble(a) / Double.parseDouble(b);
            return ok(Json.newObject().put("result", result));
        } catch (NumberFormatException e) {
            return ok(Json.newObject().put("error", "Invalid input for division operation"));
        } catch (ArithmeticException e) {
            return ok(Json.newObject().put("error", "Division by zero is not allowed"));
        }
    }

    // Additional math operations can be added here following the same pattern
}