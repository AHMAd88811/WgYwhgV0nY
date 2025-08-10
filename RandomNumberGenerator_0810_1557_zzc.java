// 代码生成时间: 2025-08-10 15:57:45
import play.libs.F.*;
import play.mvc.*;
import java.util.concurrent.ThreadLocalRandom;

// RandomNumberGenerator 是一个控制器，用于生成随机数
public class RandomNumberGenerator extends Controller {

    // ACTION: 生成一个随机整数并返回给请求者
    // @param min 最小值（包含）
    // @param max 最大值（不包含）
    public Result getRandomNumber(int min, int max) {
        // 检查边界条件
        if (min >= max) {
            return badRequest("Minimum value must be less than maximum value");
        }

        try {
            // 使用 ThreadLocalRandom 生成一个随机数
            int randomNumber = ThreadLocalRandom.current().nextInt(min, max);
            return ok(Json.toJson(randomNumber));
        } catch (Exception e) {
            // 错误处理
            return internalServerError("Error generating random number: " + e.getMessage());
        }
    }

    // ACTION: 生成一个随机整数并返回给请求者，使用默认边界值
    public Result getDefaultRandomNumber() {
        // 默认边界值
        final int DEFAULT_MIN = 1;
        final int DEFAULT_MAX = 100;
        return getRandomNumber(DEFAULT_MIN, DEFAULT_MAX);
    }
}
