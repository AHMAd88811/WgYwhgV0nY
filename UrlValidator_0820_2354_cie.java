// 代码生成时间: 2025-08-20 23:54:45
package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CompletableFuture;
import static play.libs.ws.ahc.AhcWSClientConfigBuilder.trustAllHosts;

public class UrlValidator extends Controller {

    private final WSClient ws;

    public UrlValidator(WSClient ws) {
        this.ws = ws;
    }

    public CompletionStage<Result> validateUrl(String url) {
        CompletableFuture<Result> future = new CompletableFuture<>();
        // Validate the URL format
        try {
            URI uri = new URI(url);
            WSRequest request = ws.url(url).setFollowRedirects(true);
            request.get().thenAccept(response -> {
                if(response.getStatus() == 200) {
                    future.complete(ok("URL is valid: " + url));
                } else {
                    future.complete(badRequest("Invalid URL or resource not found: " + url));
                }
            }).exceptionally(throwable -> {
                future.complete(internalServerError("Error validating URL: " + url));
                return null;
            });
        } catch (URISyntaxException e) {
            future.complete(badRequest("Invalid URL format: " + url));
        } catch (Exception e) {
            future.complete(internalServerError("An error occurred: " + e.getMessage()));
        }
        return future;
    }
}
