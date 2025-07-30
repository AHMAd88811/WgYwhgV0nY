// 代码生成时间: 2025-07-31 00:43:31
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * NetworkConnectionChecker is a PlayFramework controller class used to check the network connection status.
 * This class provides a REST endpoint to check if a given URL is reachable and returns the status.
 */
public class NetworkConnectionChecker extends Controller {

    /**
     * Checks the network connection status for a given URL.
     *
     * @param url The URL to check for connectivity.
     * @return A Result object containing HTTP status code and message indicating the connection status.
     */
    public static Result checkConnection(String url) {
        try {
            URL urlObj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("HEAD"); // Use HEAD method to check connectivity without downloading the resource
            conn.setConnectTimeout(5000); // Set a 5-second timeout

            // Get the HTTP response code
            int responseCode = conn.getResponseCode();

            // Check if the response code indicates a successful connection (2xx)
            if (responseCode >= 200 && responseCode < 300) {
                return ok("The URL is reachable and the network connection is active.");
            } else {
                return status(Http.Status.SERVICE_UNAVAILABLE, "The URL is not reachable or the network connection is inactive.");
            }
        } catch (IOException e) {
            // Handle any IO exceptions, such as a network error, by returning a service unavailable status
            return status(Http.Status.SERVICE_UNAVAILABLE, "An IO exception occurred: " + e.getMessage());
        }
    }
}
