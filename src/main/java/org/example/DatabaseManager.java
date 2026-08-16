package org.example;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class DatabaseManager {
    private static Connection connection;
    final String Prefix = Ergonomics.Color.Prefix;
    final String Arrow = Ergonomics.Color.Arrow;
    public DatabaseManager(Connection conn){
        this.connection = conn;
    }

    public static void addItem(String SKU, String Name, String Unit, String Category , int Reorder_point , LocalDate date){
        String command = "INSERT INTO items(sku,name,unit,category,reorder_point) VALUES(?,?,?,?,?)";
        try(PreparedStatement statement = connection.prepareStatement(command)){
            statement.setString(1,SKU);
            statement.setString(2,Name);
            statement.setString(3,Unit);
            statement.setString(4,Category);
            statement.setInt(5,Reorder_point);
            int excuted = statement.executeUpdate();
        }catch (SQLException e){
            System.err.println("Womp Womp");
            e.printStackTrace();
        }
    }
    public static void deleteItem(String name){
        String command = "UPDATE items SET active=false WHERE name = ?";
        try(PreparedStatement statement = connection.prepareStatement(command)){
            statement.setString(1,name);
            int excution = statement.executeUpdate();
            System.out.println("Deletion successful" + excution);
        } catch (SQLException e) {
            System.err.println("Deletion error");
            e.printStackTrace();
        }

    }
    public static List<Item> viewItem(String Name){
        List<Item> itemsList = new ArrayList<>();
        String command = "SELECT id,sku,name,unit,category,reorder_point,created_at FROM items WHERE name=?";
        try(PreparedStatement statement = connection.prepareStatement(command)){
            statement.setString(1,Name);
            try(ResultSet excution = statement.executeQuery()){
                while (excution.next()) {
                    Item item = new Item(
                            excution.getString("id"),
                            excution.getString("sku"),
                            excution.getString("name"),
                            excution.getString("unit"),
                            excution.getString("category"),
                            excution.getInt("reorder_point"),
                            excution.getString("created_at")
                    );
                    itemsList.add(item);
                }
            }
        }catch (Exception e){
            System.err.println("view error");
            e.printStackTrace();
            return null;
        }
        return itemsList;
    }
    public static void editItem(String Name,String SKU,String newName, String Unit,String Category, int Reorder_point, String date) {
        String command = "UPDATE items SET sku=? , name=? , unit=? , category=? , reorder_point=? WHERE name=?";
        try(PreparedStatement statement = connection.prepareStatement(command)){
            statement.setString(1,SKU);
            statement.setString(2,newName);
            statement.setString(3,Unit);
            statement.setString(4,Category);
            statement.setInt(5,Reorder_point);
            statement.setString(6,Name);
            int excution = statement.executeUpdate();
        }catch (SQLException e){
            System.err.println("Editing error");
            e.printStackTrace();
        }
    };

    public static void addLocation(String Name,LocalDate date){
        String command = "INSERT INTO locations(name,created_at)  VALUES(?,?::timestamp)";
        try (PreparedStatement statement = connection.prepareStatement(command)){
            statement.setString(1,Name);
            statement.setString(2,date.toString());
            int execution = statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void deleteLocation(String Name){
        String command = "DELETE FROM locations WHERE name=?;";
        try (PreparedStatement statement = connection.prepareStatement(command)){
            statement.setString(1,Name);
            int execution = statement.executeUpdate();
            System.out.println(execution + " Deleted");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static List<Location> viewLocationByName(String name){
        String command = "SELECT id,name,created_at FROM locations WHERE name=?";
        List<Location> locationList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(command)){
            statement.setString(1,name);
            try (ResultSet execution = statement.executeQuery() ){
                while(execution.next()){
                    Location location = new Location(
                    execution.getString("id"),
                    execution.getString("name"),
                    execution.getString("created_at")
                    );
                    locationList.add(location);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
        return locationList;
    }
    public static List<Location> viewAllLocations(){
        String command = "SELECT id,name,created_at FROM locations";
        List<Location> locationList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(command)){
            try (ResultSet execution = statement.executeQuery() ){
                while(execution.next()){
                    Location location = new Location(
                            execution.getString("id"),
                            execution.getString("name"),
                            execution.getString("created_at")
                    );
                    locationList.add(location);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
        return locationList;
    }
    public static void editLocation(String Name,String newName , String date){
        String command = "UPDATE locations SET name=?,created_at=?::timestamp WHERE name=?";
        try(PreparedStatement statement = connection.prepareStatement(command)){
            statement.setString(1,newName);
            statement.setString(2,date);
            statement.setString(3,Name);
            int excution = statement.executeUpdate();
        }catch (SQLException e){
            System.err.println("Editing error");
            e.printStackTrace();
        }
    }

    public static void stockIn(String item_id, String location_id ,int quantity_delta, String note , String  created_at){
        String command = "INSERT INTO stock_movements(item_id,location_id,quantity_delta,movement_type,note,created_at) VALUES(?::pg_catalog.uuid,?::pg_catalog.uuid,?,?,?,?::pg_catalog.timestamp)";
        try(PreparedStatement statement = connection.prepareStatement(command)){
            statement.setString(1,item_id);
            statement.setString(2,location_id);
            statement.setInt(3,quantity_delta);
            statement.setString(4,"IN");
            statement.setString(5,note);
            statement.setString(6,created_at);
            int execution = statement.executeUpdate();
            System.out.println("Stock Is updated!");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void stockOut(String item_id, String location_id ,int quantity_delta, String note , String  created_at){
        String command = "INSERT INTO stock_movements(item_id,location_id,quantity_delta,movement_type,note,created_at) VALUES(?::pg_catalog.uuid,?::pg_catalog.uuid,?,?,?,?::pg_catalog.timestamp)";
        try(PreparedStatement statement = connection.prepareStatement(command)){
            statement.setString(1,item_id);
            statement.setString(2,location_id);
            statement.setInt(3,-quantity_delta);
            statement.setString(4,"OUT");
            statement.setString(5,note);
            statement.setString(6,created_at);
            int execution = statement.executeUpdate();
            System.out.println("Stock Is updated!");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void adjustStock(String item_id , String location_id,int quantity_delta ,String note,String created_at){
        String command = "INSERT INTO stock_movements (location_id, quantity_delta, movement_type, note, created_at, item_id) " +
                "VALUES (?::pg_catalog.uuid, ?, ?, ?, ?::pg_catalog.timestamp, ?::pg_catalog.uuid)";

        try (PreparedStatement statement = connection.prepareStatement(command)) {
            statement.setString(1, location_id);
            statement.setInt(2, quantity_delta);
            statement.setString(3, "ADJUST");
            statement.setString(4, note);
            statement.setString(5, created_at);
            statement.setString(6, item_id);

            int execution = statement.executeUpdate();
            System.out.println("Stock is updated!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static int viewStockLevels(String uuid){
        String command = "SELECT SUM(quantity_delta) FROM stock_movements WHERE item_id=?::pg_catalog.uuid";
        try(PreparedStatement statement = connection.prepareStatement(command)){
            statement.setString(1,uuid);
            try (ResultSet sum = statement.executeQuery()){
                if(sum.next()){
                   return sum.getInt(1);
                }
            }
            System.out.println("Stock Is updated!");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }
    public static List<StockMovement> getItemHistory(String itemId) {
        List<StockMovement> history = new ArrayList<>();

        String sql = "SELECT sm.created_at, sm.movement_type, sm.quantity_delta, l.name AS location, sm.note " +
                "FROM stock_movements sm " +
                "JOIN locations l ON l.id = sm.location_id " +
                "WHERE sm.item_id = ?::pg_catalog.uuid " +
                "ORDER BY sm.created_at DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, itemId);

            try (ResultSet execution = stmt.executeQuery()) {
                while (execution.next()) {
                    StockMovement movement = new StockMovement(
                            execution.getTimestamp("created_at").toLocalDateTime(),
                            execution.getString("movement_type"),
                            execution.getInt("quantity_delta"),
                            execution.getString("location"),
                            execution.getString("note")
                    );
                    history.add(movement);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch item history", e);
        }

        return history;
    }
}

