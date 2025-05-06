package models;

import core.*;
import interfaces.*;

public class Category implements Imanageable {
    private String name;
    private String description;
    private String type;

    public Category(String name, String description, String type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }

    // CRUD Implementation
    @Override
    public void create() {
        Database.categoriesDB.add(this);
        System.out.println("category created successfully");
    }

    @Override
    public void read() {
        System.out.println("\ncategory details:");
        System.out.println("name: " + name);
        System.out.println("description: " + description);
        System.out.println("type: " + type);
    }

    public static void readAll() {
        System.out.println("\nall categories:");
        for (Category category : Database.categoriesDB) {
            category.read();
            System.out.println("...................................");

        }
    }

    @Override
    public void update() {
        for (Category category : Database.categoriesDB) {
            if (category.name.equals(this.name)) {
                category.description = this.description;
                category.type = this.type;
                System.out.println("category updated successfully");
                return;
            }
        }
        System.out.println("category not found ");
    }

    @Override
    public void delete() {
        for (int i = 0; i < Database.categoriesDB.size(); i++) {
            if (Database.categoriesDB.get(i).name.equals(this.name)) {
                Database.categoriesDB.remove(i);
                System.out.println("category deleted successfully");
                return;
            }
        }
        System.out.println("category not found");
    }

    // Getters and setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
