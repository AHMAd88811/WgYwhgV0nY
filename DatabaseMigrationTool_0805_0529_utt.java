// 代码生成时间: 2025-08-05 05:29:06
package com.example.tools;

import play.db.Database;
import play.db.evolutions.Evolution;
import play.db.evolutions.EvolutionsReader;
import play.db.evolutions.DefaultEvolutionsReader;
import play.mvc.Controller;

import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CompletableFuture;

public class DatabaseMigrationTool extends Controller {

    // Database instance
    private final Database database;

    // Constructor
    public DatabaseMigrationTool(Database database) {
        this.database = database;
    }

    /*
     * Perform database migrations.
     *
     * @return A CompletionStage indicating the completion of the migration.
     */
    public CompletionStage<Result> migrateDatabase() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Load evolution scripts
                List<Evolution> evolutions = EvolutionsReader.getEvolutions(
                        new DefaultEvolutionsReader(database));
                // Apply evolutions
                for (Evolution evolution : evolutions) {
                    evolution.apply(database);
                }
                // Return success message
                return ok("Database migration completed successfully.");
            } catch (Exception e) {
                // Handle errors
                return internalServerError("Error during database migration: " + e.getMessage());
            }
        }).thenApply(result -> result);
    }

    /*
     * Route to trigger database migration.
     */
    public Result triggerMigration() {
        return asyncResultOf(migrateDatabase());
    }

    /*
     * Helper method to wrap CompletableFuture as a Result.
     *
     * @param future The future to wrap.
     * @return A Result wrapped in a CompletableFuture.
     */
    private Result asyncResultOf(CompletionStage<Result> future) {
        return future.toCompletableFuture().join();
    }
}
