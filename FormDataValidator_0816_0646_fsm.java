// 代码生成时间: 2025-08-16 06:46:39
package controllers;

import play.data.validation.Constraints;
import play.mvc.With;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * Custom annotation to apply form validation.
 */
@With(FormDataValidatorAction.class)
public @interface FormDataValid {
}

/**
 * Action to validate form data.
 */
public class FormDataValidatorAction extends play.mvc.Action.Simple {
    @Override
    public play.mvc.Result call(play.mvc.Context ctx) throws Throwable {
        // Get the validator instance
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // Get the class type of the controller method parameter
        Class<?>[] parameterTypes = ctx.method().firstParam().getClass();

        // Get the argument array
        Object[] arguments = ctx.args();

        // Validate each argument if it is a form data class
        for (int i = 0; i < parameterTypes.length; i++) {
            if (parameterTypes[i].isAnnotationPresent(FormDataValid.class)) {
                Set<ConstraintViolation<Object>> violations = validator.validate(arguments[i]);

                // If there are constraint violations, return a bad request with an error message
                if (!violations.isEmpty()) {
                    StringBuilder errorMessage = new StringBuilder();
                    for (ConstraintViolation<Object> violation : violations) {
                        errorMessage.append(violation.getMessage()).append("
");
                    }
                    return badRequest(errorMessage.toString().trim());
                }
            }
        }

        // If validation passes, call the next action
        return delegate.call(ctx);
    }
}

/**
 * Example of a form data class.
 */
public class LoginForm {
    @Constraints.Required(message = "Username is required.")
    private String username;

    @Constraints.Required(message = "Password is required.")
    private String password;

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

/**
 * Example of a controller using the FormDataValidatorAction.
 */
public class FormController extends play.mvc.Controller {
    
    /**
     * Submit form action.
     *
     * @param loginForm The form data to validate.
     * @return The result of the form submission.
     */
    public static Result submit(@FormDataValid LoginForm loginForm) {
        // Form validation is handled by FormDataValidatorAction
        // Process the form submission
        return ok("Form submitted successfully!");
    }
}