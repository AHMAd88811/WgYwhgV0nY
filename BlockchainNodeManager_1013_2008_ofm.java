// 代码生成时间: 2025-10-13 20:08:40
package com.example.blockchain;

import play.mvc.Controller;
import play.mvc.Result;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * BlockchainNodeManager handles the management of blockchain nodes.
 * It provides functionality to add, remove, and retrieve nodes from the blockchain network.
 */
public class BlockchainNodeManager extends Controller {

    // A thread-safe map to store blockchain nodes
    private static final Map<String, String> nodes = new ConcurrentHashMap<>();

    /**
     * Adds a new node to the blockchain network.
     * @param nodeName The name of the node to be added.
     * @return A Result object indicating the outcome of the operation.
     */
    public static Result addNode(String nodeName) {
        try {
            if (nodeName == null || nodeName.isEmpty()) {
                return badRequest("Node name cannot be null or empty.");
            }
            if (nodes.containsKey(nodeName)) {
                return badRequest("Node already exists.");
            }
            nodes.put(nodeName, nodeName);
            return ok("Node added successfully.");
        } catch (Exception e) {
            return internalServerError("Error adding node: " + e.getMessage());
        }
    }

    /**
     * Removes a node from the blockchain network.
     * @param nodeName The name of the node to be removed.
     * @return A Result object indicating the outcome of the operation.
     */
    public static Result removeNode(String nodeName) {
        try {
            if (nodeName == null || nodeName.isEmpty()) {
                return badRequest("Node name cannot be null or empty.");
            }
            if (!nodes.containsKey(nodeName)) {
                return badRequest("Node does not exist.");
            }
            nodes.remove(nodeName);
            return ok("Node removed successfully.");
        } catch (Exception e) {
            return internalServerError("Error removing node: " + e.getMessage());
        }
    }

    /**
     * Retrieves all nodes in the blockchain network.
     * @return A Result object containing a JSON array of node names.
     */
    public static Result getNodes() {
        try {
            Map<String, String> nodeMap = new HashMap<>(nodes);
            return ok(Json.toJson(nodeMap));
        } catch (Exception e) {
            return internalServerError("Error retrieving nodes: " + e.getMessage());
        }
    }
}
