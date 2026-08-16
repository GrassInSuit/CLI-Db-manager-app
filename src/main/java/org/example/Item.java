package org.example;

public class Item {
    private String uuid;
    private String sku;
    private String name;
    private String unit;
    private String category;
    private int reorderPoint;
    private String createdAt;

    public Item(String uuid, String sku, String name, String unit, String category, int reorderPoint, String createdAt) {
        this.uuid = uuid;
        this.sku = sku;
        this.name = name;
        this.unit = unit;
        this.category = category;
        this.reorderPoint = reorderPoint;
        this.createdAt = createdAt;
    }

    public String getUuid() { return uuid; }
    public String getSku() { return sku; }
    public String getName() { return name; }
    public String getUnit() { return unit; }
    public String getCategory() { return category; }
    public int getReorderPoint() { return reorderPoint; }
    public String getCreatedAt() { return createdAt; }
}
