package org.example;

public class Location {
    String name;
    String created_at;
    String UUID;

    public Location(String UUID, String name, String created_at) {
        this.UUID = UUID;
        this.name = name;
        this.created_at = created_at;
    }

    public String getUUID() { return UUID; }
    public String getName() { return name; }
    public String getDate() { return created_at; }
}
