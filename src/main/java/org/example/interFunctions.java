package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class interFunctions {
    final private static DatabaseManager databaseManager = ContactSystem.databaseManager;
    private final static Scanner scanner = new Scanner(System.in);
    private final static String Prefix = Ergonomics.Color.Prefix;
    enum mainMenuList {ITEMS,LOCATION,STOCKIN,STOCKOUT,ADJUST,TRANSFER,VIEWSTOCK,REPORTS,HISTORY,QUIT}
    enum managementList {ADD,DELETE,VIEW,EDIT,QUIT};
    enum viewLocationList {ALL,DATE,QUIT};


    //--------------- MAIN SCREEN ---------------


    public static void mainScreenMenu(){
        Ergonomics.clearScreen();
        System.out.println(Ergonomics.Color.CYAN + "---INVENTORY MANAGEMENT SYSTEM---" + Ergonomics.Color.RESET);
        boolean isMainScreen = true;
        while (isMainScreen){
            mainMenuList SELECTED = mainMenuSelector();
            Ergonomics.clearLines(11);
            switch (SELECTED){
                case ITEMS -> itemManagementFunc();
                case LOCATION -> locationManagementFunc();
                case STOCKIN -> stockinDataCollector();
                case STOCKOUT -> stockoutDataCollector();
                case VIEWSTOCK -> viewLocationFunc();
                case ADJUST -> adjustStockValue();
                case HISTORY -> itemHistoryDataCollector();
                case QUIT -> isMainScreen = false;
            }
        }
    }
    public static mainMenuList mainMenuSelector(){
        System.out.println("1. Manage Items \n 2.Manage location \n 3. Stock In \n 4. Stock Out \n 5. Adjust Stock \n 6. Transfer Stock \n 7. View Stock Levels \n 8. Low Stock Report \n 9. Item History \n 0. Exit");
        int Option = scanner.nextInt();
        switch (Option){
            case 1-> {
                return mainMenuList.ITEMS;
            }
            case 2->{
                return mainMenuList.LOCATION;
            }
            case 3-> {
                return mainMenuList.STOCKIN;
            }
            case 4-> {
                return mainMenuList.STOCKOUT;
            }
            case 5->{
                return mainMenuList.ADJUST;
            }
            case 6-> {
                return mainMenuList.TRANSFER;
            }
            case 7-> {
                return mainMenuList.VIEWSTOCK;
            }
            case 8->{
                return mainMenuList.REPORTS;
            }
            case 9-> {
                return mainMenuList.HISTORY;
            }
            case 0->{
                return mainMenuList.QUIT;
            }
            default -> {
                return  mainMenuList.QUIT;
            }
        }
    }


    //--------------- MANAGE ITEMS ---------------


    public static void itemManagementFunc(){
        managementList SELECTED = itemManagementSelector();
        Ergonomics.clearLines(7);
        switch (SELECTED){
            case ADD -> addItemDataCollector();
            case DELETE -> deleteItemDataCollector();
            case VIEW -> viewItemDataCollector();
            case EDIT -> EditItemDataCollector();
            case QUIT -> {
            }
        }
    }
    public static managementList itemManagementSelector(){
        System.out.println("1. Add Item \n2. Delete Item \n3. View Item \n4. Edit Item \n0. Exit menu");
        int option = scanner.nextInt();
        switch (option){
            case 1->{
                return managementList.ADD;
            }
            case 2->{
                return managementList.DELETE;
            }
            case 3->{
                return managementList.VIEW;
            }
            case 4->{
                return managementList.EDIT;
            }
            case 5->{
                return managementList.QUIT;
            }
            default -> {
                System.out.println("Invalid choice!");
                return managementList.QUIT;
            }
        }
    }
    //subfunctions
    public static void addItemDataCollector(){
        System.out.print(Prefix + "Enter SKU (or press enter to auto-generate): ");
        scanner.nextLine();
        String sku = scanner.nextLine().trim();
        Ergonomics.clearLines(1);

        // Name (required)
        String name;
        do {
            System.out.print("Enter item name: ");
            name = scanner.nextLine().trim();
            Ergonomics.clearLines(1);
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty.");
            }
        } while (name.isEmpty());

        // Unit (required)
        System.out.print(Prefix + "Enter unit of measure (each/kg/box/etc.): ");
        String unit = scanner.nextLine().trim();
        Ergonomics.clearLines(1);

        // Category (optional)
        System.out.print(Prefix + "Enter category (optional, press enter to skip): ");
        String category = scanner.nextLine().trim();
        Ergonomics.clearLines(1);
        if (category.isEmpty()) {
            category = null;
        }

        // Reorder point (required, must be a non-negative integer)
        int reorderPoint = -1;
        while (reorderPoint < 0) {
            System.out.print("Enter reorder point: ");
            String input = scanner.nextLine().trim();
            Ergonomics.clearLines(1);
            try {
                reorderPoint = Integer.parseInt(input);
                if (reorderPoint < 0) {                  
                    System.out.println(Prefix + "Reorder point cannot be negative.");
                }
            } catch (NumberFormatException e) {
                System.out.println(Prefix + "Please enter a valid number.");
            }
        }

        // Date (defaults to today if left blank)
        LocalDate createdAt = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (createdAt == null) {
            System.out.print(Prefix + "Enter date (yyyy-MM-dd, press enter for today): ");
            String dateInput = scanner.nextLine().trim();
            Ergonomics.clearLines(1);
            if (dateInput.isEmpty()) {
                createdAt = LocalDate.now();
            } else {
                try {
                    createdAt = LocalDate.parse(dateInput, formatter);
                } catch (Exception e) {
                    System.out.println(Prefix + "Invalid date format. Use yyyy-MM-dd.");
                }
            }
            databaseManager.addItem(sku,name,unit,category,reorderPoint,createdAt);
        }
    }
    public static void deleteItemDataCollector(){
        System.out.print("Enter Items name (leave it blank to cancel): ");
        String name = scanner.nextLine();
        Ergonomics.clearLines(1);
        while(name.isBlank()){
            name = scanner.nextLine();
            Ergonomics.clearLines(1);
        }
        DatabaseManager.deleteItem(name);
    }
    public static void viewItemDataCollector(){
        System.out.print("Enter Items name (leave it blank to cancel): ");
        String name = scanner.nextLine();
        Ergonomics.clearLines(1);
        while(name.isBlank()){
            name = scanner.nextLine();
            Ergonomics.clearLines(1);
        }
        List<Item> itemData = DatabaseManager.viewItem(name);
        if (itemData.isEmpty()) {
            System.out.println("Aucun article trouvé dans la base de données.");
        } else {
            for (Item item : itemData) {
                System.out.println(item.getUuid() + "|" + item.getSku() + " | " + item.getName() + " | " + item.getUnit() +
                        " | " + item.getCategory() + " | " + item.getReorderPoint() + " | " + item.getCreatedAt());
            }
        }

    }
    public static void EditItemDataCollector(){
        System.out.print("Enter item's name: ");
        scanner.nextLine();
        String name = scanner.nextLine();
        Ergonomics.clearLines(1);
        if(name.isEmpty()){
            System.out.println("this item doesn't exist in the database");
            return;
        }
        List<Item> itemProperties = DatabaseManager.viewItem(name);
        while(itemProperties.isEmpty()){
            System.out.println("invalid name");
            name = scanner.nextLine();
            itemProperties = DatabaseManager.viewItem(name);
        }

            Item propertyList = itemProperties.getFirst();

            String sku = propertyList.getSku();
            String newName = propertyList.getName();
            String unit = propertyList.getUnit();
            String category = propertyList.getCategory();
            int reorderPoint = propertyList.getReorderPoint();
            String createdAt = propertyList.getCreatedAt();

            System.out.println("\n--- UPDATE ITEM (Press ENTER to keep current value) ---");
            System.out.print("New name [" + sku + "]: ");
            String inputSku = scanner.nextLine().trim();
            if (!inputSku.isEmpty()) {
                sku = inputSku;
            }

            System.out.print("New name [" + newName + "]: ");
            String inputName = scanner.nextLine().trim();
            if (!inputName.isEmpty()) {
                newName = inputName;
            }

            System.out.print("New unit [" + unit + "]: ");
            String inputUnit = scanner.nextLine().trim();
            if (!inputUnit.isEmpty()) {
                unit = inputUnit;
            }

            System.out.print("New category [" + category + "]: ");
            String inputCategory = scanner.nextLine().trim();
            if (!inputCategory.isEmpty()) {
                category = inputCategory;
            }
            System.out.print("New reorder point [" + reorderPoint + "]: ");
            String inputReorder = scanner.nextLine();
            if (!inputReorder.isEmpty()) {
                try {
                    reorderPoint = Integer.parseInt(inputReorder);
                } catch (NumberFormatException e) {
                    System.out.println("invalid input (Not a number)");
                    return;
                }
            }
            System.out.println("\nItem properties updated in memory:");
            System.out.println(sku + " | " + newName + " | " + unit + " | " + category + " | " + reorderPoint + " | " + createdAt);
            DatabaseManager.editItem(name,sku,newName,unit,category,reorderPoint,createdAt);
        }


    //--------------- MANAGE LOCATIONS ---------------


    public static void locationManagementFunc(){
        managementList SELECTED = locationManagementSelector();
        Ergonomics.clearLines(7);
        switch (SELECTED){
            case ADD -> addLocationDataCollector();
            case DELETE -> deleteLocationDataCollector();
            case VIEW -> viewLocationFunc();

            case EDIT -> editLocationDataCollector();
            case QUIT -> {
                return;
            }
            default -> {
                System.out.println("invalid input");
                return;
            }
        }
    }
    public static managementList locationManagementSelector(){
        System.out.println("1. Add location \n2. Delete location \n3. View location \n4. Edit location \n0. Exit menu");
        int option = scanner.nextInt();
        switch (option){
            case 1->{
                return managementList.ADD;
            }
            case 2->{
                return managementList.DELETE;
            }
            case 3->{
                return managementList.VIEW;
            }
            case 4->{
                return managementList.EDIT;
            }
            case 5->{
                return managementList.QUIT;
            }
            default -> {
                System.out.println("Invalid choice!");
                return managementList.QUIT;
            }
        }
    }
    //subfunctions
    public static void addLocationDataCollector(){
        System.out.print("enter location's name: ");
        scanner.nextLine();
        String location = scanner.nextLine();
        Ergonomics.clearLines(1);
        if (location.isBlank()){
            System.out.println("invalide input");
            return;
        }
        LocalDate createdAt = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (createdAt == null) {
            System.out.print("Enter date (yyyy-MM-dd, press enter for today): ");
            String dateInput = scanner.nextLine().trim();
            Ergonomics.clearLines(1);
            if (dateInput.isEmpty()) {
                createdAt = LocalDate.now();
            } else {
                try {
                    createdAt = LocalDate.parse(dateInput, formatter);
                } catch (Exception e) {
                    System.out.println("Invalid date format. Use yyyy-MM-dd.");
                }
            }
        }
        DatabaseManager.addLocation(location,createdAt);
    }
    public static void viewLocationFunc(){
        viewLocationList option = viewLocationSelector();
        switch(option){
            case ALL->viewAllLocationDataCollector();
            case DATE->{return;}
            case QUIT->{return;}
        }
    }
    public static viewLocationList viewLocationSelector() {
        System.out.println(" 1.View all locations \n 2.View location by date period \n 0.Exit");
        int option = scanner.nextInt();
        switch (option) {
            case 1 -> {
                return viewLocationList.ALL;
            }
            case 2 -> {
                return viewLocationList.DATE;
            }
            default -> {
                return viewLocationList.QUIT;
            }
        }
    }
    public static void viewAllLocationDataCollector(){
        List<Location> locationList = DatabaseManager.viewAllLocations();
        if (locationList.isEmpty()) {
            System.out.println("There are no locations in the database!");
        } else {
            for (Location location : locationList) {
                System.out.println(location.getUUID() + "|" + location.getName() + " | " + location.getDate());
            }
        }
    }
    public static void editLocationDataCollector() {
        System.out.print("Enter location's name: ");
        scanner.nextLine();
        String name = scanner.nextLine();
        Ergonomics.clearLines(1);
        if (name.isEmpty()) {
            System.out.println("this location doesn't exist in the database");
            return;
        }
        List<Location> locationProperties = DatabaseManager.viewLocationByName(name);
        while (locationProperties.isEmpty()) {
            System.out.println("invalid name");
            name = scanner.nextLine();
            locationProperties = DatabaseManager.viewLocationByName(name);
        }

        Location propertyList = locationProperties.getFirst();

        String newName = propertyList.getName();
        String date = propertyList.getDate();

        System.out.println("\n--- UPDATE LOCATION (Press ENTER to keep current value) ---");
        System.out.print("New name [" + newName + "]: ");
        String inputName = scanner.nextLine().trim();
        if (!inputName.isEmpty()) {
            newName = inputName;
        }

        System.out.print("New date [" + date + "]: ");
        String inputDate = scanner.nextLine().trim();
        if (!inputDate.isEmpty()) {
            date = inputDate;
        }

        System.out.println("\nLocation properties updated in memory:");
        System.out.println(newName + " | " + date);
        DatabaseManager.editLocation(name, newName, date);
    }
    public static void deleteLocationDataCollector(){
        System.out.print("Enter Location's name (leave it blank to cancel): ");
        String name = scanner.nextLine();
        Ergonomics.clearLines(1);
        while(name.isBlank()){
            name = scanner.nextLine();
            Ergonomics.clearLines(1);
        }
        DatabaseManager.deleteLocation(name);
    }


    //--------------- STOCK IN ---------------


    public static void stockinDataCollector(){
        System.out.print("Enter item's name: ");
        scanner.nextLine();
        String Name = scanner.nextLine();
        Ergonomics.clearLines(1);
        List<Item> itemsPropertie = DatabaseManager.viewItem(Name);
        if(itemsPropertie.isEmpty()){
            System.out.println("This item does not exist in the database!");
            return;
        }
        System.out.print("Enter location's name: ");
        String Location = scanner.nextLine();
        Ergonomics.clearLines(1);
        List<Location> locationList = DatabaseManager.viewLocationByName(Location);
        if(locationList.isEmpty()){
            System.out.println("This location does not exist in the database!");
            return;
        }
        System.out.print("Enter the amount of units: ");
        String stock = scanner.nextLine();
        Ergonomics.clearLines(1);
        int stockCount;
        try{
            stockCount = Integer.parseInt(stock);
            if(stockCount <=0){
                System.out.println("Invalide input");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
            return;
        }
        System.out.print("note: ");
        String Note = scanner.nextLine();
        Ergonomics.clearLines(1);
        LocalDate createdAt = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (createdAt == null) {
            System.out.print("Enter date (yyyy-MM-dd, press enter for today): ");
            String dateInput = scanner.nextLine().trim();
            Ergonomics.clearLines(1);
            if (dateInput.isEmpty()) {
                createdAt = LocalDate.now();
            } else {
                try {
                    createdAt = LocalDate.parse(dateInput, formatter);
                } catch (Exception e) {
                    System.out.println("Invalid date format. Use yyyy-MM-dd.");
                }
            }
        }
        DatabaseManager.stockIn(itemsPropertie.getFirst().getUuid(),locationList.getFirst().getUUID(),stockCount,Note, String.valueOf(createdAt));

    }
    public static void stockoutDataCollector(){
        System.out.print("Enter item's name: ");
        scanner.nextLine();
        String Name = scanner.nextLine();
        Ergonomics.clearLines(1);
        List<Item> itemsPropertie = DatabaseManager.viewItem(Name);
        if(itemsPropertie.isEmpty()){
            System.out.println("This item does not exist in the database!");
            return;
        }
        System.out.print("Enter location's name: ");
        String Location = scanner.nextLine();
        Ergonomics.clearLines(1);
        List<Location> locationList = DatabaseManager.viewLocationByName(Location);
        if(locationList.isEmpty()){
            System.out.println("This location does not exist in the database!");
            return;
        }
        System.out.print("Enter the amount of units: ");
        String stock = scanner.nextLine();
        Ergonomics.clearLines(1);
        int stockCount;
        try{
            stockCount = Integer.parseInt(stock);
            if(stockCount <=0){
                System.out.println("Invalide input");
                return;
            }
            int stockLevels = DatabaseManager.viewStockLevels(itemsPropertie.getFirst().getUuid());
            if(stockCount>stockLevels){
                System.out.println("Not enough units to do this action, current available units: " + stockLevels);
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
            return;
        }
        System.out.print("note: ");
        String Note = scanner.nextLine();
        Ergonomics.clearLines(1);
        LocalDate createdAt = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (createdAt == null) {
            System.out.print("Enter date (yyyy-MM-dd, press enter for today): ");
            String dateInput = scanner.nextLine().trim();
            Ergonomics.clearLines(1);
            if (dateInput.isEmpty()) {
                createdAt = LocalDate.now();
            } else {
                try {
                    createdAt = LocalDate.parse(dateInput, formatter);
                } catch (Exception e) {
                    System.out.println("Invalid date format. Use yyyy-MM-dd.");
                }
            }
        }
        DatabaseManager.stockOut(itemsPropertie.getFirst().getUuid(),locationList.getFirst().getUUID(),stockCount,Note, String.valueOf(createdAt));

    }
    public static void adjustStockValue(){
        System.out.print("Enter item's name: ");
        scanner.nextLine();
        String Name = scanner.nextLine();
        Ergonomics.clearLines(1);
        List<Item> itemsPropertie = DatabaseManager.viewItem(Name);
        if(itemsPropertie.isEmpty()){
            System.out.println("This item does not exist in the database!");
            return;
        }
        System.out.print("Enter location's name: ");
        String Location = scanner.nextLine();
        Ergonomics.clearLines(1);
        List<Location> locationList = DatabaseManager.viewLocationByName(Location);
        if(locationList.isEmpty()){
            System.out.println("This location does not exist in the database!");
            return;
        }
        System.out.print("Enter the amount of units: ");
        String stock = scanner.nextLine();
        Ergonomics.clearLines(1);
        int stockCount;
        try{
            stockCount = Integer.parseInt(stock);
            int stockLevels = DatabaseManager.viewStockLevels(itemsPropertie.getFirst().getUuid());
            if(stockCount+stockLevels<0){
                System.out.println("Not enough units to do this action, current available units: " + stockLevels);
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
            return;
        }
        System.out.print("note: ");
        String Note = scanner.nextLine();
        Ergonomics.clearLines(1);
        LocalDate createdAt = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (createdAt == null) {
            System.out.print("Enter date (yyyy-MM-dd, press enter for today): ");
            String dateInput = scanner.nextLine().trim();
            Ergonomics.clearLines(1);
            if (dateInput.isEmpty()) {
                createdAt = LocalDate.now();
            } else {
                try {
                    createdAt = LocalDate.parse(dateInput, formatter);
                } catch (Exception e) {
                    System.out.println("Invalid date format. Use yyyy-MM-dd.");
                }
            }
        }
        DatabaseManager.adjustStock(itemsPropertie.getFirst().getUuid(),locationList.getFirst().getUUID(),stockCount,Note, String.valueOf(createdAt));
    }
    public static void viewStockLevelsDataCollector(){
        System.out.print("Enter item's name: ");
        scanner.nextLine();
        String Name = scanner.nextLine();
        Ergonomics.clearLines(1);
        List<Item> itemsPropertie = DatabaseManager.viewItem(Name);
        if(itemsPropertie.isEmpty()){
            System.out.println("This item does not exist in the database!");
            return;
        }
        int stockLevels = DatabaseManager.viewStockLevels(itemsPropertie.getFirst().getUuid());
        System.out.println(Name+" stocks are: "+stockLevels);
    }
    /*public static void transferStockDataCollector(){
        System.out.println("Enter item's name:");
        scanner.nextLine();
        String Name = scanner.nextLine();
        List<Item> itemsPropertie = DatabaseManager.viewItem(Name);
        if(itemsPropertie.isEmpty()){
            System.out.println("This item does not exist in the database!");
            return;
        }
        System.out.println("transfer from:");
        String firstLocation = scanner.nextLine();
        List<Location> firstLocationList = DatabaseManager.viewLocation(firstLocation);
        if(firstLocationList.isEmpty()){
            System.out.println("This location does not exist in the database!");
            return;
        }
        System.out.println("transfer to:");
        String lastLocation = scanner.nextLine();
        List<Location> lastLocationList = DatabaseManager.viewLocation(lastLocation);
        if(lastLocationList.isEmpty()){
            System.out.println("This location does not exist in the database!");
            return;
        }
        System.out.println("Enter amount of units");
        String stock = scanner.nextLine();
        int stockCount;
        try{
            stockCount = Integer.parseInt(stock);
            if(stockCount <=0){
                System.out.println("Invalide input");
                return;
            }
            int stockLevels = DatabaseManager.viewStockLevels(itemsPropertie.getFirst().getUuid());
            if(stockCount>stockLevels){
                System.out.println("Not enough units to do this action, current available units: " + stockLevels);
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
            return;
        }
    }*/
    public static void itemHistoryDataCollector(){
        System.out.print("Enter item's name: ");
        scanner.nextLine();
        String Name = scanner.nextLine();
        Ergonomics.clearLines(1);
        List<Item> itemsPropertie = DatabaseManager.viewItem(Name);
        if(itemsPropertie.isEmpty()){
            System.out.println("This item does not exist in the database!");
            return;
        }
        List<StockMovement> stockMovements = DatabaseManager.getItemHistory(itemsPropertie.getFirst().getUuid());
        if (stockMovements.isEmpty()) {
            System.out.println("No movement history found for this item.");
        } else {
            System.out.println("==== History " + Name + " ====");
            System.out.println("    DATE     | type | amount | location | note");
            for (StockMovement movement : stockMovements) {
                System.out.println(movement);
            }
        }
    }
    }