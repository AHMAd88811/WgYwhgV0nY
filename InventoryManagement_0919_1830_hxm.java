// 代码生成时间: 2025-09-19 18:30:30
package com.example.inventory;

import play.db.jpa.JPAApi;
import play.mvc.Controller;
import play.mvc.Result;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

// InventoryItem class to represent an item in inventory
public class InventoryItem {
    private Long id;
    private String name;
    private int quantity;

    // Constructors, getters and setters
    public InventoryItem() {}
    public InventoryItem(Long id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}

// InventoryService class to handle business logic
public class InventoryService {
    private JPAApi jpaApi;

    @Inject
    public InventoryService(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public List<InventoryItem> getAllItems() {
        return jpaApi.withTransaction(() -> {
            EntityManager em = jpaApi.em();
            return em.createQuery("SELECT i FROM InventoryItem i", InventoryItem.class).getResultList();
        });
    }

    public InventoryItem getItemById(Long id) {
        return jpaApi.withTransaction(() -> {
            EntityManager em = jpaApi.em();
            return em.find(InventoryItem.class, id);
        });
    }

    public InventoryItem addItem(InventoryItem item) {
        return jpaApi.withTransaction(() -> {
            EntityManager em = jpaApi.em();
            em.persist(item);
            return item;
        });
    }

    public InventoryItem updateItem(Long id, InventoryItem item) {
        return jpaApi.withTransaction(() -> {
            EntityManager em = jpaApi.em();
            InventoryItem existingItem = getItemById(id);
            if (existingItem != null) {
                existingItem.setName(item.getName());
                existingItem.setQuantity(item.getQuantity());
                return existingItem;
            } else {
                throw new RuntimeException("Item not found");
            }
        });
    }

    public void deleteItem(Long id) {
        jpaApi.withTransaction(() -> {
            EntityManager em = jpaApi.em();
            InventoryItem item = getItemById(id);
            if (item != null) {
                em.remove(item);
            } else {
                throw new RuntimeException("Item not found");
            }
            return null;
        });
    }
}

// InventoryController class to handle HTTP requests
public class InventoryController extends Controller {
    private InventoryService inventoryService;

    @Inject
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public Result getAllItems() {
        List<InventoryItem> items = inventoryService.getAllItems();
        return ok(Json.toJson(items));
    }

    public Result getItem(Long id) {
        InventoryItem item = inventoryService.getItemById(id);
        if (item == null) {
            return notFound("Item not found");
        }
        return ok(Json.toJson(item));
    }

    public Result addItem() {
        InventoryItem item = Json.fromJson(request().body().asJson(), InventoryItem.class);
        InventoryItem createdItem = inventoryService.addItem(item);
        return createdItem != null ?
                created(createdItem) :
                badRequest("Error while adding item");
    }

    public Result updateItem(Long id) {
        InventoryItem item = Json.fromJson(request().body().asJson(), InventoryItem.class);
        InventoryItem updatedItem = inventoryService.updateItem(id, item);
        return updatedItem != null ?
                updated(updatedItem) :
                badRequest("Error while updating item");
    }

    public Result deleteItem(Long id) {
        inventoryService.deleteItem(id);
        return ok("Item deleted");
    }
}
