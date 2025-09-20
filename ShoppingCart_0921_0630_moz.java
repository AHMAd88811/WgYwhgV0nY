// 代码生成时间: 2025-09-21 06:30:57
package com.example.ecommerce;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http;
import play.mvc.BodyParser;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * ShoppingCart class handles the shopping cart functionality.
 * It uses a Table to map user sessions to their cart items.
 */
public class ShoppingCart extends Controller {

    private static final Table<String, String, Integer> cartTable = HashBasedTable.create();
    private static final Map<String, String> sessionUserMap = new HashMap<>();

    /**
     * Adds a product to the current user's shopping cart.
     *
     * @param request The HTTP request containing the session ID and product details.
     * @return A JSON result indicating success or failure.
     */
    @BodyParser.Of(BodyParser.Json.class)
    public Result addToCart(Http.Request request) {
        JsonNode json = request.body().asJson();
        String productId = json.get("productId").asText();
        int quantity = json.get("quantity").asInt();
        String sessionId = session(request).id();

        if (!sessionUserMap.containsKey(sessionId)) {
            sessionUserMap.put(sessionId, UUID.randomUUID().toString());
        }

        String userId = sessionUserMap.get(sessionId);
        cartTable.put(userId, productId, quantity);
        return ok("Product added to cart.");
    }

    /**
     * Retrieves the current user's shopping cart.
     *
     * @param request The HTTP request containing the session ID.
     * @return A JSON result containing the cart items.
     */
    public Result getCart(Http.Request request) {
        String sessionId = session(request).id();
        if (!sessionUserMap.containsKey(sessionId)) {
            return ok("No cart found.");
        }

        String userId = sessionUserMap.get(sessionId);
        return ok(Json.toJson(cartTable.row(userId)));
    }

    /**
     * Removes a product from the current user's shopping cart.
     *
     * @param request The HTTP request containing the session ID and product ID.
     * @return A JSON result indicating success or failure.
     */
    public Result removeFromCart(Http.Request request) {
        String productId = request.getQueryString("productId");
        String sessionId = session(request).id();

        if (!sessionUserMap.containsKey(sessionId)) {
            return ok("No cart found.");
        }

        String userId = sessionUserMap.get(sessionId);
        cartTable.remove(userId, productId);
        return ok("Product removed from cart.");
    }

    /**
     * Clears the current user's shopping cart.
     *
     * @param request The HTTP request containing the session ID.
     * @return A JSON result indicating success or failure.
     */
    public Result clearCart(Http.Request request) {
        String sessionId = session(request).id();
        if (sessionUserMap.containsKey(sessionId)) {
            String userId = sessionUserMap.get(sessionId);
            cartTable.row(userId).clear();
        }
        return ok("Cart cleared.");
    }
}
