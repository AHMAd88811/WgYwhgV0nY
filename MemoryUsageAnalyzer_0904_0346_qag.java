// 代码生成时间: 2025-09-04 03:46:48
package com.example.play;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http;
import play.Logger;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

/**
 * Controller to handle memory usage analysis.
 */
public class MemoryUsageAnalyzer extends Controller {
    
    /**
     * GET action to analyze memory usage.
     * @return A JSON result with memory usage details.
     */
    public Result analyzeMemoryUsage() {
        try {
            // Retrieve the memory MXBean
            MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
            
            // Get the memory usage
            MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
            MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
            
            // Create a response object
            MemoryUsageResult result = new MemoryUsageResult(
                heapMemoryUsage.getUsed(),
                heapMemoryUsage.getMax(),
                nonHeapMemoryUsage.getUsed(),
                nonHeapMemoryUsage.getMax()
            );
            
            // Return the result as JSON
            return ok(play.libs.Json.toJson(result));
        } catch (Exception e) {
            Logger.error("Error analyzing memory usage: " + e.getMessage());
            return internalServerError("Error analyzing memory usage.");
        }
    }
    
    /**
     * Data class to hold memory usage details.
     */
    public static class MemoryUsageResult {
        
        private final long heapUsed;
        private final long heapMax;
        private final long nonHeapUsed;
        private final long nonHeapMax;
        
        /**
         * Constructor for MemoryUsageResult.
         * @param heapUsed Used heap memory in bytes.
         * @param heapMax Maximum heap memory in bytes.
         * @param nonHeapUsed Used non-heap memory in bytes.
         * @param nonHeapMax Maximum non-heap memory in bytes.
         */
        public MemoryUsageResult(long heapUsed, long heapMax, long nonHeapUsed, long nonHeapMax) {
            this.heapUsed = heapUsed;
            this.heapMax = heapMax;
            this.nonHeapUsed = nonHeapUsed;
            this.nonHeapMax = nonHeapMax;
        }
        
        // Getters for the fields
        public long getHeapUsed() { return heapUsed; }
        public long getHeapMax() { return heapMax; }
        public long getNonHeapUsed() { return nonHeapUsed; }
        public long getNonHeapMax() { return nonHeapMax; }
    }
}
