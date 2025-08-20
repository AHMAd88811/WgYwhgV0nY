// 代码生成时间: 2025-08-20 14:46:21
package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http;
import play.mvc.Session;
import views.html.index;

/**
 * Controller to handle theme switching.
 */
public class ThemeController extends Controller {

    private static final String THEME_SESSION_KEY = "theme";
    private static final String DEFAULT_THEME = "default";
    private static final String LIGHT_THEME = "light";
    private static final String DARK_THEME = "dark";
    private static final String[] AVAILABLE_THEMES = {LIGHT_THEME, DARK_THEME};

    /**
     * Changes the theme based on the request parameter.
     * @param request The HTTP request
     * @return 302 redirect to the application's index page
     */
    public static Result switchTheme(Http.Request request) {
        String theme = request.getQueryString("theme");
        try {
            // Validate if the theme is one of the available themes
            if (isValidTheme(theme)) {
                Session currentSession = request.session();
                currentSession.put(THEME_SESSION_KEY, theme);
            }
        } catch (IllegalArgumentException e) {
            // Log error and set default theme if invalid theme is passed
            switchTheme(DEFAULT_THEME, request);
        }
        return redirect(routes.Application.index());
    }

    /**
     * Checks if the provided theme is valid.
     * @param theme The theme to check
     * @return true if the theme is valid, false otherwise.
     */
    private static boolean isValidTheme(String theme) {
        if (theme == null) {
            return false;
        }
        for (String availableTheme : AVAILABLE_THEMES) {
            if (availableTheme.equals(theme)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Switches to a default theme.
     * @param theme The theme to switch to
     * @param request The HTTP request
     * @return 302 redirect to the application's index page
     */
    public static Result switchTheme(String theme, Http.Request request) {
        request.session().put(THEME_SESSION_KEY, theme);
        return redirect(routes.Application.index());
    }
}
