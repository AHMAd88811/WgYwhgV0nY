// 代码生成时间: 2025-09-11 14:09:17
import play.libs.Crypto;
import java.util.Base64;
import javax.inject.Inject;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * PasswordEncryptionDecryption controller for handling password encryption and decryption.
 */
public class PasswordEncryptionDecryption extends Controller {

    @Inject
    private Crypto crypto;

    /**
     * Encrypts the given password.
     *
     * @param password The password to encrypt.
     * @return A Base64 encoded encrypted password.
     */
    public Result encryptPassword(String password) {
        if (password == null || password.isEmpty()) {
            return badRequest("Password cannot be null or empty.");
        }
        try {
            String encryptedPassword = Base64.getEncoder().encodeToString(
                crypto.encryptAES("key".getBytes(), password.getBytes()));
            return ok("Encrypted password: " + encryptedPassword);
        } catch (Exception e) {
            return internalServerError("Error encrypting password: " + e.getMessage());
        }
    }

    /**
     * Decrypts the given password.
     *
     * @param encryptedPassword The encrypted password to decrypt.
     * @return The decrypted password.
     */
    public Result decryptPassword(String encryptedPassword) {
        if (encryptedPassword == null || encryptedPassword.isEmpty()) {
            return badRequest("Encrypted password cannot be null or empty.");
        }
        try {
            String decryptedPassword = new String(
                crypto.decryptAES("key".getBytes(), Base64.getDecoder().decode(encryptedPassword)));
            return ok("Decrypted password: " + decryptedPassword);
        } catch (Exception e) {
            return internalServerError("Error decrypting password: " + e.getMessage());
        }
    }
}
