package models;

import core.*;

import java.util.ArrayList;

public class Category {
    private String name;
    private String description;

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    // CRUD Implementation
    public static void create(Category category) {
        Database.categoriesDB.add(category);
    }

    public void read() {
    }

    public static void update(String s, Category c) {
        for (Category category : Database.categoriesDB) {
            if (category.getName().equals(s)) {
                category.setName(c.getName());
                category.setDescription(c.getDescription());
                return; // Exit once updated
            }
        }
    }

    public static void delete(String s) {
        Database.categoriesDB.removeIf(category -> category.getName().equals(s));
    }

    public static boolean isNameUnique(String s) {
        return Database.categoriesDB.stream().anyMatch(category -> category.getName().equals(s));
    }

    // Getters and setters
    public static ArrayList<String> getCategoryNames() {
        if(Database.categoriesDB.isEmpty()) {
            return new ArrayList<>();
        }

        ArrayList<String> names = new ArrayList<>();

        for(Category category : Database.categoriesDB) {
            names.add(category.getName());
        }

        return names;
    }
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
}
