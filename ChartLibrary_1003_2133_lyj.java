// 代码生成时间: 2025-10-03 21:33:01
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http;
import play.libs.Json;
import play.twirl.api.Html;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;

import static play.mvc.Results.ok;
import static play.libs.Json.toJson;

/**
 * A Controller to handle chart library functionality.
 */
public class ChartLibrary extends Controller {

    private final ChartService chartService;

    @Inject
    public ChartLibrary(ChartService chartService) {
        this.chartService = chartService;
    }

    /**
     * Displays the chart library page.
     * @return The HTML page as a Result object.
     */
    public Result index() {
        try {
            Html chartLibraryPage = views.html.chartLibrary.render();
            return ok(chartLibraryPage);
        } catch (Exception e) {
            return internalServerError("An error occurred: " + e.getMessage());
        }
    }

    /**
     * Fetches chart data for visualization.
     * @param chartType The type of chart to generate.
     * @param params Additional parameters required for the chart.
     * @return A JSON object containing chart data.
     */
    public Result fetchChartData(String chartType, Http.Request request) {
        try {
            Map<String, Object> params = parseParams(request);
            Map<String, Object> chartData = chartService.generateChartData(chartType, params);
            return ok(Json.toJson(chartData));
        } catch (IllegalArgumentException e) {
            return badRequest("Invalid parameters: " + e.getMessage());
        } catch (Exception e) {
            return internalServerError("An error occurred: " + e.getMessage());
        }
    }

    /**
     * Helper method to parse additional parameters from the request.
     * @param request The HTTP request containing additional parameters.
     * @return A map of additional parameters.
     */
    private Map<String, Object> parseParams(Http.Request request) {
        Map<String, String[]> formParams = request.body().asFormUrlEncoded();
        Map<String, Object> params = new HashMap<>();
        for (Map.Entry<String, String[]> entry : formParams.entrySet()) {
            String key = entry.getKey();
            String[] values = entry.getValue();
            if (values.length > 1) {
                params.put(key, values);
            } else if (values.length == 1) {
                params.put(key, values[0]);
            }
        }
        return params;
    }
}
