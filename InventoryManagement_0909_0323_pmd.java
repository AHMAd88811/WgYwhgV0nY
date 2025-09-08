// 代码生成时间: 2025-09-09 03:23:44
package com.example.inventory;

import play.db.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import java.util.concurrent.CompletionStage;
import play.mvc.Controller;
import play.mvc.Result;
import play.libs.concurrent.HttpExecutionContext;

@Entity
public class InventoryItem extends Model {
    @Id
    public Long id;
    public String name;
    public int quantity;

    public InventoryItem() {}

    public InventoryItem(Long id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }
}

public class InventoryManagement extends Controller {
    private final HttpExecutionContext ec;

    public InventoryManagement(HttpExecutionContext ec) {
        this.ec = ec;
    }

    /**
     * GET /inventory - Display all inventory items
     */
    public CompletionStage<Result> listAll() {
        return inventoryItems.findList().toCompletableFuture()
            .thenApplyAsync(list -> ok(views.html.inventory.render(list)), ec.current());
    }

    /**
     * GET /inventory/item/:id - Display inventory item by id
     */
    public CompletionStage<Result> displayItem(Long id) {
        return inventoryItems.findById(id).toCompletableFuture()
            .thenApplyAsync(item -> item != null ? ok(views.html.item.render(item)) : notFound(), ec.current());
    }

    /**
     * POST /inventory/item - Add a new inventory item
     */
    public CompletionStage<Result> addItem() {
        return request().body().asJson()
            .thenApplyAsync(json -> {
                InventoryItem newItem = json.as(InventoryItem.class);
                newItem.save();
                return redirect(routes.InventoryManagement.listAll());
            }, ec.current());
    }

    /**
     * PUT /inventory/item/:id - Update an inventory item
     */
    public CompletionStage<Result> updateItem(Long id) {
        return inventoryItems.findById(id).toCompletableFuture()
            .thenComposeAsync(item -> {
                if (item != null) {
                    return request().body().asJson()
                        .thenApplyAsync(json -> {
                            item.name = json.as(InventoryItem.class).name;
                            item.quantity = json.as(InventoryItem.class).quantity;
                            item.update();
                            return redirect(routes.InventoryManagement.listAll());
                        }, ec.current());
                } else {
                    return CompletableFuture.completedFuture(notFound());
                }
            }, ec.current());
    }

    /**
     * DELETE /inventory/item/:id - Delete an inventory item
     */
    public CompletionStage<Result> deleteItem(Long id) {
        return inventoryItems.findById(id).toCompletableFuture()
            .thenApplyAsync(item -> {
                if (item != null) {
                    item.delete();
                    return redirect(routes.InventoryManagement.listAll());
                } else {
                    return notFound();
                }
            }, ec.current());
    }
}
