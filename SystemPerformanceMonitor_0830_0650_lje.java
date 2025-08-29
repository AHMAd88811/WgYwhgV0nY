// 代码生成时间: 2025-08-30 06:50:21
package com.example.monitor;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http;
import play.libs.Json;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.HashMap;
import java.util.Map;

public class SystemPerformanceMonitor extends Controller {

    /**
     * Retrieves the current system performance metrics as JSON.
     *
     * @return A Result object containing the system performance metrics.
     */
    public Result getSystemPerformance() {
        Map<String, Object> performanceData = new HashMap<>();
        try {
            // Get the OperatingSystemMXBean to access OS-level metrics
            OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();

            // Collect system performance metrics
            performanceData.put("SystemLoadAverage", osBean.getSystemLoadAverage());
            performanceData.put("TotalPhysicalMemorySize", osBean.getTotalPhysicalMemorySize());
            performanceData.put("FreePhysicalMemorySize", osBean.getFreePhysicalMemorySize());
            performanceData.put("AvailableProcessors", osBean.getAvailableProcessors());

            // Return the performance metrics as JSON
            return ok(Json.toJson(performanceData));
        } catch (Exception e) {
            // Handle any exceptions that occur and return an error response
            return internalServerError("Failed to retrieve system performance metrics: " + e.getMessage());
        }
    }
}
