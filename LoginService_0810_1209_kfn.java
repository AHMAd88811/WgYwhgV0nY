// 代码生成时间: 2025-08-10 12:09:21
package com.example.auth;

import play.db.jpa.JPAApi;
import play.mvc.Result;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import play.mvc.Controller;
import play.mvc.Http;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.CompletableFuture;

public class LoginService extends Controller {

    private final JPAApi jpaApi;

    @Inject
    public LoginService(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public CompletionStage<Result> login(Http.Request request) {
        return CompletableFuture.supplyAsync(() -> {
            String username = request.formUrlEncoded().get("username")[0];
            String password = request.formUrlEncoded().get("password")[0];
            try {
                User user = jpaApi.em().createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                        .setParameter("username", username).getSingleResult();
                if (user != null && user.getPassword().equals(password)) {
                    session().clear(); // Clear any existing session data
                    session("username", user.getUsername()); // Set the user's username in the session
                    return redirect(routes.HomeController.index());
                } else {
                    return forbidden("Invalid username or password");
                }
            } catch (NoResultException e) {
                // No user found with the given username
                return forbidden("Invalid username or password");
            }
        }, jpaApi.executionContext());
    }
}

// User entity
@Entity
public class User extends Controller {
    @Id
    private Long id;
    private String username;
    private String password;
    // Getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
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