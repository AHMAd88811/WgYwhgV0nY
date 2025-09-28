// 代码生成时间: 2025-09-29 00:00:55
import play.mvc.*;
import play.libs.Json;
import play.mvc.Http;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CompletableFuture;
import com.google.cloud.translate.v3.TranslationServiceClient;
import com.google.cloud.translate.v3.TranslationServiceSettings;
import com.google.cloud.translate.v3.Translation;
import com.google.protobuf.Value;

public class MachineTranslationController extends Controller {

    // 定义翻译服务客户端
    private static final TranslationServiceClient translationClient = TranslationServiceClient.create();

    public static Result translate(String text, String targetLanguage) {
        try {
            // 构建翻译请求
            TranslationServiceSettings.TranslationServiceClientSettings settings =
                TranslationServiceSettings.newBuilder()
                    .setListLocationsSettings(TranslationServiceSettings.ListLocationsSettings.newBuilder().build())
                    .build();

            translationClient.setListLocationsSettings(settings);

            // 翻译文本
            Translation response = translationClient.translateText(
                TranslationServiceSettings.messageWithText(text),
                TranslationServiceSettings.targetLanguage(targetLanguage)
            ).getTranslation();

            // 构建响应结果
            return ok(Json.toJson(response));
        } catch (Exception e) {
            // 错误处理
            return internalServerError(Json.toJson("errors": [e.getMessage()]));
        }
    }
}
