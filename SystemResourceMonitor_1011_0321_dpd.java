// 代码生成时间: 2025-10-11 03:21:24
 * It provides a simple interface to retrieve CPU, Memory, and Disk usage information.
 *
 * @author Your Name
 * @version 1.0
 */

package com.example.monitor;

import play.mvc.Controller;
import play.mvc.Result;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;

public class SystemResourceMonitor extends Controller {
    
    /*
     * Retrieves system resource information and returns it as JSON.
     *
     * @return A JSON object containing CPU, Memory, and Disk usage information.
     */
    public Result getSystemResourceInfo() {
        try {
            // Get the operating system MX bean
            OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
            // Get the runtime MX bean
            RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
            // Get the thread MX bean
            ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
            
            // Create a JSON object to hold the resource information
            SystemResourceInfo resourceInfo = new SystemResourceInfo();
            
            // CPU usage
            resourceInfo.setCpuUsage(osBean.getSystemCpuLoad());
            // Memory usage
            runtimeBean.getUptime(); // Warm-up the JVM
            long memoryUsedBefore = runtimeBean.getUsedHeapMemory();
            Thread.sleep(100); // Simulate some workload
            long memoryUsedAfter = runtimeBean.getUsedHeapMemory();
            long memoryUsage = memoryUsedAfter - memoryUsedBefore;
            resourceInfo.setMemoryUsage(memoryUsage);
            // Disk usage
            // This is a placeholder. In a real scenario, you would use a library to get disk usage.
            resourceInfo.setDiskUsage(0); // Placeholder value
            
            // Return the JSON object
            return ok(Json.toJson(resourceInfo));
        } catch (Exception e) {
            // Handle any exceptions that occur
            return internalServerError("An error occurred while retrieving system resource information: " + e.getMessage());
        }
    }
    
    /*
     * A simple DTO to hold system resource information.
     */
    public static class SystemResourceInfo {
        private double cpuUsage;
        private long memoryUsage;
        private long diskUsage;
        
        // Getters and setters
        public double getCpuUsage() { return cpuUsage; }
        public void setCpuUsage(double cpuUsage) { this.cpuUsage = cpuUsage; }
        public long getMemoryUsage() { return memoryUsage; }
        public void setMemoryUsage(long memoryUsage) { this.memoryUsage = memoryUsage; }
        public long getDiskUsage() { return diskUsage; }
        public void setDiskUsage(long diskUsage) { this.diskUsage = diskUsage; }
    }
}