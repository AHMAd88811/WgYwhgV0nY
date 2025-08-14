// 代码生成时间: 2025-08-14 14:23:06
package com.yourcompany.monitor;

import play.mvc.Controller;
import play.mvc.Result;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.time.Duration;
import java.time.Instant;

public class SystemPerformanceMonitor extends Controller {

    /**
     * Returns system performance metrics as JSON
     * 
     * @return A JSON object containing system performance metrics
     */
    public Result getSystemMetrics() {
        try {
            OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(
                OperatingSystemMXBean.class
            );
            SystemMetrics metrics = new SystemMetrics();
            metrics.setCpuLoad(osBean.getSystemCpuLoad());
            metrics.setMemoryUsage(osBean.getFreePhysicalMemorySize() + osBean.getTotalPhysicalMemorySize());
            metrics.setUptime(Instant.now().getEpochSecond());
            return ok(metrics.toJson());
        } catch (Exception e) {
            return internalServerError("Error retrieving system metrics: " + e.getMessage());
        }
    }

    /**
     * Represents system metrics
     */
    public static class SystemMetrics {
        private double cpuLoad;
        private long memoryUsage;
        private long uptime;

        // Getters and setters
        public double getCpuLoad() { return cpuLoad; }
        public void setCpuLoad(double cpuLoad) { this.cpuLoad = cpuLoad; }
        public long getMemoryUsage() { return memoryUsage; }
        public void setMemoryUsage(long memoryUsage) { this.memoryUsage = memoryUsage; }
        public long getUptime() { return uptime; }
        public void setUptime(long uptime) { this.uptime = uptime; }

        /**
         * Converts system metrics to JSON format
         * 
         * @return A JSON string representing system metrics
         */
        public String toJson() {
            return String.format(
                "{"cpuLoad": %.2f, "memoryUsage": %d, "uptime": %d}",
                cpuLoad, memoryUsage, uptime
            );
        }
    }
}
