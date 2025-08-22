// 代码生成时间: 2025-08-23 00:53:06
package controllers;

import play.mvc.*;
import play.libs.Json;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import models.*;
import views.html.*;
import javax.inject.*;
import play.db.jpa.*;

// RESTful API controller using Play Framework
@Singleton
public class RestfulApiController extends Controller {
    // JPA repository
    private final UserRepository userRepository;

    // Constructor injection for UserRepository
    @Inject
    public RestfulApiController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Endpoint to get all users
    public CompletionStage<Result> getAllUsers() {
        return userRepository.findAll()
            .thenApplyAsync(users -> 
                ok(Json.toJson(users)),
                ec.current());
    }

    // Endpoint to get a user by ID
    public CompletionStage<Result> getUser(Long id) {
        return userRepository.findById(id)
            .thenApplyAsync(optionalUser ->
                optionalUser
                    .map(user -> ok(Json.toJson(user)))
                    .orElseGet(() -> notFound("User not found"))),
            ec.current());
    }

    // Endpoint to create a new user
    public CompletionStage<Result> createUser() {
        return request().body().asJson()
            .thenApplyAsync(json -> {
                User newUser = Json.fromJson(json, User.class);
                return userRepository.save(newUser)
                    .thenApplyAsync(savedUser -> ok(Json.toJson(savedUser)), ec.current());
            }, ec.current());
    }

    // Endpoint to update a user by ID
    public CompletionStage<Result> updateUser(Long id) {
        return request().body().asJson()
            .thenApplyAsync(json -> {
                User updatedUser = Json.fromJson(json, User.class);
                return userRepository.update(id, updatedUser)
                    .thenApplyAsync(updated -> ok(Json.toJson(updated)), ec.current());
            }, ec.current());
    }

    // Endpoint to delete a user by ID
    public CompletionStage<Result> deleteUser(Long id) {
        return userRepository.delete(id)
            .thenApplyAsync(deleted -> ok("User deleted"), ec.current());
    }
}

// JPA repository for User entity
public interface UserRepository extends JpaRepository<User, Long> {
    // Custom repository methods can be added here
}

// User entity class
@Entity
public class User extends Model {
    @Id
    private Long id;

    private String name;
    private String email;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}