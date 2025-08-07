// 代码生成时间: 2025-08-08 04:42:37
package com.example.monitor;

import play.mvc.Controller;
import play.mvc.Result;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.util.List;
import java.util.Optional;

import static play.libs.Json.toJson;

public class SystemPerformanceMonitor extends Controller {

    // 方法：获取系统性能信息
    public Result getSystemPerformance() {
        try {
            // 获取内存和CPU信息
            OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
            RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();

            // 系统性能信息对象
            SystemPerformanceInfo performanceInfo = new SystemPerformanceInfo();
            performanceInfo.setCpuLoad(osBean.getSystemCpuLoad());
            performanceInfo.setFreeMemory(runtimeBean.getFreeMemory() / (1024.0 * 1024)); // 转换为MB
            performanceInfo.setTotalMemory(runtimeBean.getTotalMemory() / (1024.0 * 1024)); // 转换为MB
            performanceInfo.setUsedMemory(performanceInfo.getTotalMemory() - performanceInfo.getFreeMemory());

            // 返回JSON格式的系统性能信息
            return ok(toJson(performanceInfo));
        } catch (Exception e) {
            // 错误处理
            return internalServerError("Error retrieving system performance data: " + e.getMessage());
        }
    }

    // 系统性能信息类
    public static class SystemPerformanceInfo {
        private double cpuLoad;
        private double freeMemory;
        private double totalMemory;
        private double usedMemory;

        public double getCpuLoad() {
            return cpuLoad;
        }

        public void setCpuLoad(double cpuLoad) {
            this.cpuLoad = cpuLoad;
        }

        public double getFreeMemory() {
            return freeMemory;
        }

        public void setFreeMemory(double freeMemory) {
            this.freeMemory = freeMemory;
        }

        public double getTotalMemory() {
            return totalMemory;
        }

        public void setTotalMemory(double totalMemory) {
            this.totalMemory = totalMemory;
        }

        public double getUsedMemory() {
            return usedMemory;
        }

        public void setUsedMemory(double usedMemory) {
            this.usedMemory = usedMemory;
        }
    }
}