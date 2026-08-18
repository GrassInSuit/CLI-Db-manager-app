package org.example;

import javax.lang.model.util.SimpleTypeVisitor7;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class interFunctions {
    final private static DatabaseManager databaseManager = ContactSystem.databaseManager;
    private final static Scanner scanner = new Scanner(System.in);
    private final static String Prefix = Ergonomics.Prefix;
    private final static String Error = Ergonomics.Error;
    enum mainMenuList {ITEMS,LOCATION,STOCKIN,STOCKOUT,ADJUST,TRANSFER,VIEWSTOCK,REPORTS,HISTORY,QUIT}
    enum managementList {ADD,DELETE,VIEW,EDIT,QUIT};
    enum viewLocationList {ALL,DATE,QUIT};
    enum viewItemList {ALL,NAME,CATEGORY,DATE,QUIT};
    enum viewStockList {NAME,CATEGORY,DATE,LOCATION,QUIT};
    enum reportList {GLOBAL,LOCATION,QUIT};
    private static String YELLOW = Ergonomics.Color.YELLOW;
    private static String GREEN = Ergonomics.Color.GREEN;
    private static String RED = Ergonomics.Color.RED;
    private static String RESET = Ergonomics.Color.RESET;
    private static String CYAN = Ergonomics.Color.CYAN;


    //--------------- MAIN SCREEN ---------------


    public static void mainScreenMenu(){
        Ergonomics.clearScreen();
        System.out.println(CYAN + """
                  ██╗███╗   ███╗███████╗
                  ██║████╗ ████║██╔════╝
                  ██║██╔████╔██║███████╗
                  ██║██║╚██╔╝██║╚════██║
                  ██║██║ ╚═╝ ██║███████║
                  ╚═╝╚═╝     ╚═╝╚══════╝
            """ + RESET +
                YELLOW + "   Inventory Management System" + RESET + "\n");
        boolean isMainScreen = true;
        while (isMainScreen){
            mainMenuList SELECTED = mainMenuSelector();
            Ergonomics.clearLines(11);
            switch (SELECTED){
                case ITEMS -> itemManagementFunc();
                case LOCATION -> locationManagementFunc();
                case STOCKIN -> stockinDataCollector();
                case STOCKOUT -> stockoutDataCollector();
                case VIEWSTOCK -> viewStockFunc();
                case ADJUST -> adjustStockValue();
                case TRANSFER -> transferStockDataCollector();
                case REPORTS -> reportsFunc();
                case HISTORY -> itemHistoryDataCollector();
                case QUIT -> isMainScreen = false;
            }
        }
    }
    public static mainMenuList mainMenuSelector(){
        System.out.printf(
                YELLOW + "%-5s" + CYAN + "%-22s" + RESET + "%s%n" +
                        YELLOW + "%-5s" + CYAN + "%-22s" + RESET + "%s%n" +
                        YELLOW + "%-5s" + CYAN + "%-22s" + RESET + "%s%n" +
                        YELLOW + "%-5s" + CYAN + "%-22s" + RESET + "%s%n" +
                        YELLOW + "%-5s" + CYAN + "%-22s" + RESET + "%s%n" +
                        YELLOW + "%-5s" + CYAN + "%-22s" + RESET + "%s%n" +
                        YELLOW + "%-5s" + CYAN + "%-22s" + RESET + "%s%n" +
                        YELLOW + "%-5s" + CYAN + "%-22s" + RESET + "%s%n" +
                        YELLOW + "%-5s" + CYAN + "%-22s" + RESET + "%s%n" +
                        YELLOW + "%-5s" + CYAN + "%-22s" + RESET + "%s%n",

                "1.", "MANAGE ITEMS", "allows you to add, delete, edit and view items in your database",
                "2.", "MANAGE LOCATIONS", "allows you to add, delete, edit and view locations in your database",
                "3.", "STOCK IN", "used to add stock entering",
                "4.", "STOCK OUT", "used to add stock leaving",
                "5.", "ADJUST STOCK", "used to adjust data of an existing stock",
                "6.", "TRANSFER STOCK", "used to transfer stock from a location to another",
                "7.", "VIEW STOCK LEVELS", "allows you to see stock based on item name, category or date",
                "8.", "LOW STOCK REPORTS", "shows you items that need restocking",
                "9.", "VIEW ITEMS HISTORY", "show activities of items in the database",
                "0.", "EXIT", "leave the app"
        );

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
            case VIEW -> viewItemFunc();
            case EDIT -> EditItemDataCollector();
            case QUIT -> {
            }
        }
    }
    public static managementList itemManagementSelector(){
        System.out.printf(
                YELLOW + "%-4s" + CYAN + "%-15s" + RESET + "%s%n" +
                        YELLOW + "%-4s" + CYAN + "%-15s" + RESET + "%s%n" +
                        YELLOW + "%-4s" + CYAN + "%-15s" + RESET + "%s%n" +
                        YELLOW + "%-4s" + CYAN + "%-15s" + RESET + "%s%n" +
                        YELLOW + "%-4s" + CYAN + "%-15s" + RESET + "%s%n",

                "1.", "Add Item", "adds a new item to the database",
                "2.", "Delete Item", "disactivated item from the database",
                "3.", "View Item", "shows detail of an item in the database",
                "4.", "Edit Item", "edits item's properties in the database",
                "0.", "Exit menu", "go back to the previous menu selection"
        );

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
                System.out.println(Error + "Invalid choice!");
                Ergonomics.waitAnyKey();
                Ergonomics.clearLines(2);
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
            System.out.print(Prefix + "Enter item name: ");
            name = scanner.nextLine().trim();
            Ergonomics.clearLines(1);
            if (name.isEmpty()) {
                System.out.println(Error + "Name cannot be empty.");
                Ergonomics.waitAnyKey();
                Ergonomics.clearLines(2);
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
            System.out.print(Prefix + "Enter reorder point: ");
            String input = scanner.nextLine().trim();
            Ergonomics.clearLines(1);
            try {
                reorderPoint = Integer.parseInt(input);
                if (reorderPoint < 0) {
                    System.out.println(Error + "Reorder point cannot be negative.");
                    Ergonomics.waitAnyKey();
                    Ergonomics.clearLines(2);
                }
            } catch (NumberFormatException e) {
                System.out.println(Error + "Please enter a valid number.");
                Ergonomics.waitAnyKey();
                Ergonomics.clearLines(2);
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
                    System.out.println(Error + "Invalid date format. Use yyyy-MM-dd.");
                    Ergonomics.waitAnyKey();
                    Ergonomics.clearLines(2);
                }
            }
        }

        System.out.println(Prefix + YELLOW + " --- Confirm Item Details ---" + RESET);
        System.out.println(Prefix + CYAN + " SKU: " + RESET + sku + CYAN + " | Name: " + RESET + name + CYAN + " | Unit: " + RESET + unit + CYAN + " | Category: " + RESET + category + CYAN + " | Reorder: " + RESET + reorderPoint + CYAN + " | Date: " + RESET + createdAt);

        boolean confirmed = Ergonomics.comfirmAction(scanner);
        Ergonomics.clearLines(3);

        if (!confirmed) {
            return;
        }

        databaseManager.addItem(sku,name,unit,category,reorderPoint,createdAt);
        System.out.println(Prefix + GREEN + "Item added successfully!" + RESET);
        Ergonomics.waitAnyKey();
        Ergonomics.clearLines(2);
    }
    public static void deleteItemDataCollector(){
        System.out.print(Prefix + "Enter Items name (leave it blank to cancel): ");
        String name = scanner.nextLine();
        Ergonomics.clearLines(1);
        while(name.isBlank()){
            name = scanner.nextLine();
            Ergonomics.clearLines(1);
        }
        List<Item> itemsPropertie = DatabaseManager.viewItem(name);
        if(itemsPropertie.isEmpty()){
            System.out.println(Error + "This item does not exist in the database!");
            Ergonomics.waitAnyKey();
            Ergonomics.clearLines(2);
            return;
        }
        Item item = itemsPropertie.getFirst();

        System.out.println(Prefix + YELLOW + " --- Confirm Item Deletion ---" + RESET);
        System.out.println(Prefix + RED + " UUID: " + RESET + item.getUuid() + RED + " | SKU: " + RESET + item.getSku() + RED + " | Name: " + RESET + item.getName() + RED + " | Unit: " + RESET + item.getUnit() + RED + " | Category: " + RESET + item.getCategory() + RED + " | Reorder: " + RESET + item.getReorderPoint() + RED + " | Date: " + RESET + item.getCreatedAt());

        boolean confirmed = Ergonomics.comfirmAction(scanner);
        Ergonomics.clearLines(3);

        if (!confirmed) {
            return;
        }

        DatabaseManager.deleteItem(name);
        System.out.println(Prefix + GREEN + "Item deleted successfully!" + RESET);
        Ergonomics.waitAnyKey();
        Ergonomics.clearLines(2);
    }
    public static void viewItemFunc(){
        viewItemList option = viewItemSelector();
        switch(option){
            case ALL->viewAllItemDataCollector();
            case NAME->viewItemDataCollector();
            case CATEGORY->viewItemByCategoryDataCollector();
            case DATE->viewItemByDateDataCollector();
            case QUIT->{return;}
        }
    }
    public static viewItemList viewItemSelector() {
        System.out.printf(
                YELLOW + "%-4s" + CYAN + "%-25s" + RESET + "%s%n" +
                        YELLOW + "%-4s" + CYAN + "%-25s" + RESET + "%s%n" +
                        YELLOW + "%-4s" + CYAN + "%-25s" + RESET + "%s%n" +
                        YELLOW + "%-4s" + CYAN + "%-25s" + RESET + "%s%n" +
                        YELLOW + "%-4s" + CYAN + "%-25s" + RESET + "%s%n",

                "1.", "View all items", "displays every single item currently stored in the system",
                "2.", "View item by name", "search and display specific items using their exact name",
                "3.", "View item by category", "filter and group your items by their assigned categories",
                "4.", "View item by date period", "track items added or updated within a specific timeframe",
                "0.", "Exit", "go back to the previous menu selection"
        );

        int option = scanner.nextInt();
        switch (option) {
            case 1 -> {
                return viewItemList.ALL;
            }
            case 2 -> {
                return viewItemList.NAME;
            }
            case 3 -> {
                return viewItemList.CATEGORY;
            }
            case 4 -> {
                return viewItemList.DATE;
            }
            case 0 -> {
                return viewItemList.QUIT;
            }
            default -> {
                return viewItemList.QUIT;
            }
        }
    }
    public static void viewAllItemDataCollector(){
        List<Item> itemData = DatabaseManager.viewAllItems();
        int linesPrinted = 0;
        if (itemData.isEmpty()) {
            System.out.println(Error + "There are no items in the database!");
            linesPrinted++;
        } else {
            for (Item item : itemData) {
                System.out.println(item.getUuid() + "|" + item.getSku() + " | " + item.getName() + " | " + item.getUnit() +
                        " | " + item.getCategory() + " | " + item.getReorderPoint() + " | " + item.getCreatedAt());
                linesPrinted++;
            }
        }
        Ergonomics.waitAnyKey();
        linesPrinted++;
        Ergonomics.clearLines(linesPrinted);
    }
    public static void viewItemDataCollector(){
        scanner.nextLine();
        System.out.print(Prefix + "Enter Items name (leave it blank to cancel): ");
        String name = scanner.nextLine();
        Ergonomics.clearLines(1);
        if (name.isBlank()) {
            return;
        }
        List<Item> itemData = DatabaseManager.viewItem(name);
        int linesPrinted = 0;
        if (itemData.isEmpty()) {
            System.out.println(Error + "This item does not exist in the database!");
            linesPrinted++;
        } else {
            for (Item item : itemData) {
                System.out.println(item.getUuid() + "|" + item.getSku() + " | " + item.getName() + " | " + item.getUnit() +
                        " | " + item.getCategory() + " | " + item.getReorderPoint() + " | " + item.getCreatedAt());
                linesPrinted++;
            }
        }
        Ergonomics.waitAnyKey();
        linesPrinted++;
        Ergonomics.clearLines(linesPrinted);
    }
    public static void viewItemByCategoryDataCollector(){
        scanner.nextLine();
        System.out.print(Prefix + "Enter category: ");
        String category = scanner.nextLine().trim();
        Ergonomics.clearLines(1);
        List<Item> itemData = DatabaseManager.viewItemByCategory(category);
        int linesPrinted = 0;
        if (itemData.isEmpty()) {
            System.out.println(Error + "There are no items in this category!");
            linesPrinted++;
        } else {
            for (Item item : itemData) {
                System.out.println(item.getUuid() + "|" + item.getSku() + " | " + item.getName() + " | " + item.getUnit() +
                        " | " + item.getCategory() + " | " + item.getReorderPoint() + " | " + item.getCreatedAt());
                linesPrinted++;
            }
        }
        Ergonomics.waitAnyKey();
        linesPrinted++;
        Ergonomics.clearLines(linesPrinted);
    }
    public static void viewItemByDateDataCollector(){
        scanner.nextLine();
        LocalDate startDate = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (startDate == null) {
            System.out.print(Prefix + "Enter start date (yyyy-MM-dd, press enter for today): ");
            String dateInput = scanner.nextLine().trim();
            Ergonomics.clearLines(1);
            if (dateInput.isEmpty()) {
                startDate = LocalDate.now();
            } else {
                try {
                    startDate = LocalDate.parse(dateInput, formatter);
                } catch (Exception e) {
                    System.out.println(Error + "Invalid date format. Use yyyy-MM-dd.");
                    Ergonomics.waitAnyKey();
                    Ergonomics.clearLines(2);
                }
            }
        }
        LocalDate endDate = null;
        while (endDate == null) {
            System.out.print(Prefix + "Enter end date (yyyy-MM-dd, press enter for today): ");
            String dateInput = scanner.nextLine().trim();
            Ergonomics.clearLines(1);
            if (dateInput.isEmpty()) {
                endDate = LocalDate.now();
            } else {
                try {
                    endDate = LocalDate.parse(dateInput, formatter);
                } catch (Exception e) {
                    System.out.println(Error + "Invalid date format. Use yyyy-MM-dd.");
                    Ergonomics.waitAnyKey();
                    Ergonomics.clearLines(2);
                }
            }
        }
        List<Item> itemData = DatabaseManager.viewItemByDate(startDate.toString(), endDate.toString());
        int linesPrinted = 0;
        if (itemData.isEmpty()) {
            System.out.println(Error + "There are no items in the database for this date range!");
            linesPrinted++;
        } else {
            for (Item item : itemData) {
                System.out.println(item.getUuid() + "|" + item.getSku() + " | " + item.getName() + " | " + item.getUnit() +
                        " | " + item.getCategory() + " | " + item.getReorderPoint() + " | " + item.getCreatedAt());
                linesPrinted++;
            }
        }
        Ergonomics.waitAnyKey();
        linesPrinted++;
        Ergonomics.clearLines(linesPrinted);
    }
    public static void EditItemDataCollector(){
        System.out.print(Prefix + "Enter item's name: ");
        scanner.nextLine();
        String name = scanner.nextLine();
        Ergonomics.clearLines(1);
        if(name.isEmpty()){
            System.out.println(Error + "this item doesn't exist in the database");
            Ergonomics.waitAnyKey();
            Ergonomics.clearLines(2);
            return;
        }
        List<Item> itemProperties = DatabaseManager.viewItem(name);
        while(itemProperties.isEmpty()){
            System.out.println(Error + "invalid name");
            Ergonomics.waitAnyKey();
            Ergonomics.clearLines(2);
            System.out.print(Prefix + "Enter item's name: ");
            name = scanner.nextLine();
            Ergonomics.clearLines(1);
            itemProperties = DatabaseManager.viewItem(name);
        }

        Item propertyList = itemProperties.getFirst();

        String sku = propertyList.getSku();
        String newName = propertyList.getName();
        String unit = propertyList.getUnit();
        String category = propertyList.getCategory();
        int reorderPoint = propertyList.getReorderPoint();
        String createdAt = propertyList.getCreatedAt();

        System.out.println(Prefix + YELLOW + "\n--- UPDATE ITEM (Press ENTER to keep current value) ---" + RESET);
        System.out.print(Prefix + "New SKU [" + sku + "]: ");
        String inputSku = scanner.nextLine().trim();
        Ergonomics.clearLines(1);
        if (!inputSku.isEmpty()) {
            sku = inputSku;
        }

        System.out.print(Prefix + "New name [" + newName + "]: ");
        String inputName = scanner.nextLine().trim();
        Ergonomics.clearLines(1);
        if (!inputName.isEmpty()) {
            newName = inputName;
        }

        System.out.print(Prefix + "New unit [" + unit + "]: ");
        String inputUnit = scanner.nextLine().trim();
        Ergonomics.clearLines(1);
        if (!inputUnit.isEmpty()) {
            unit = inputUnit;
        }

        System.out.print(Prefix + "New category [" + category + "]: ");
        String inputCategory = scanner.nextLine().trim();
        Ergonomics.clearLines(1);
        if (!inputCategory.isEmpty()) {
            category = inputCategory;
        }
        System.out.print(Prefix + "New reorder point [" + reorderPoint + "]: ");
        String inputReorder = scanner.nextLine();
        Ergonomics.clearLines(1);
        if (!inputReorder.isEmpty()) {
            try {
                reorderPoint = Integer.parseInt(inputReorder);
            } catch (NumberFormatException e) {
                System.out.println(Error + "invalid input (Not a number)");
                Ergonomics.waitAnyKey();
                Ergonomics.clearLines(2 + 2); // 2 lines error + header
                return;
            }
        }

        System.out.println(Prefix + YELLOW + "\n --- Confirm Updated Details ---" + RESET);
        System.out.println(Prefix + CYAN + " SKU: " + RESET + sku + CYAN + " | Name: " + RESET + newName + CYAN + " | Unit: " + RESET + unit + CYAN + " | Category: " + RESET + category + CYAN + " | Reorder: " + RESET + reorderPoint + CYAN + " | Date: " + RESET + createdAt);

        boolean confirmed = Ergonomics.comfirmAction(scanner);
        Ergonomics.clearLines(3 + 2); // clear confirmation block + header lines

        if (!confirmed) {
            return;
        }

        DatabaseManager.editItem(name,sku,newName,unit,category,reorderPoint,createdAt);
        System.out.println(Prefix + GREEN + "Item updated successfully!" + RESET);
        Ergonomics.waitAnyKey();
        Ergonomics.clearLines(2);
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
                System.out.println(Error + "invalid input");
                Ergonomics.waitAnyKey();
                Ergonomics.clearLines(2);
                return;
            }
        }
    }
    public static managementList locationManagementSelector(){
        System.out.printf(
                YELLOW + "%-4s" + CYAN + "%-20s" + RESET + "%s%n" +
                        YELLOW + "%-4s" + CYAN + "%-20s" + RESET + "%s%n" +
                        YELLOW + "%-4s" + CYAN + "%-20s" + RESET + "%s%n" +
                        YELLOW + "%-4s" + CYAN + "%-20s" + RESET + "%s%n" +
                        YELLOW + "%-4s" + CYAN + "%-20s" + RESET + "%s%n",

                "1.", "Add location", "register a new storage location or warehouse site",
                "2.", "Delete location", "remove an existing location from the system database",
                "3.", "View location", "display the details and address of a specific location",
                "4.", "Edit location", "update the name, details, or address of a location",
                "0.", "Exit menu", "go back to the previous menu selection"
        );

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
            case 0->{
                return managementList.QUIT;
            }
            default -> {
                System.out.println(Error + "Invalid choice!");
                Ergonomics.waitAnyKey();
                Ergonomics.clearLines(2);
                return managementList.QUIT;
            }
        }
    }
    //subfunctions
    public static void addLocationDataCollector(){
        System.out.print(Prefix + "Enter location's name: ");
        scanner.nextLine();
        String location = scanner.nextLine();
        Ergonomics.clearLines(1);
        if (location.isBlank()){
            System.out.println(Error + "invalide input");
            Ergonomics.waitAnyKey();
            Ergonomics.clearLines(2);
            return;
        }
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
                    System.out.println(Error + "Invalid date format. Use yyyy-MM-dd.");
                    Ergonomics.waitAnyKey();
                    Ergonomics.clearLines(2);
                }
            }
        }

        System.out.println(Prefix + YELLOW + " --- Confirm Location Details ---" + RESET);
        System.out.println(Prefix + CYAN + " Location: " + RESET + location + CYAN + " | Date: " + RESET + createdAt);

        boolean confirmed = Ergonomics.comfirmAction(scanner);
        Ergonomics.clearLines(3);

        if (!confirmed) {
            return;
        }

        DatabaseManager.addLocation(location,createdAt);
        System.out.println(Prefix + GREEN + "Location added successfully!" + RESET);
        Ergonomics.waitAnyKey();
        Ergonomics.clearLines(2);
    }
    //----------------- VIEW LOCATION --------------------
    public static void viewLocationFunc(){
        viewLocationList option = viewLocationSelector();
        switch(option){
            case ALL->viewAllLocationDataCollector();
            case DATE->viewLocationByDateDataCollector();
            case QUIT->{return;}
        }
    }
    public static viewLocationList viewLocationSelector() {
        System.out.printf(
                YELLOW + "%-4s" + CYAN + "%-28s" + RESET + "%s%n" +
                        YELLOW + "%-4s" + CYAN + "%-28s" + RESET + "%s%n" +
                        YELLOW + "%-4s" + CYAN + "%-28s" + RESET + "%s%n",

                "1.", "View all locations", "Displays every single location currently stored in the system",
                "2.", "View location by date period", "Track locations added or updated within a specific timeframe",
                "0.", "Exit", "Go back to the previous menu selection"
        );

        int option = scanner.nextInt();
        switch (option) {
            case 1 -> {
                return viewLocationList.ALL;
            }
            case 2 -> {
                return viewLocationList.DATE;
            }
            case 0->{
                return viewLocationList.QUIT;
            }
            default -> {
                return viewLocationList.QUIT;
            }
        }
    }
    public static void viewAllLocationDataCollector(){
        List<Location> locationList = DatabaseManager.viewAllLocations();
        int linesPrinted = 0;
        if (locationList.isEmpty()) {
            System.out.println(Error + "There are no locations in the database!");
            linesPrinted++;
        } else {
            for (Location location : locationList) {
                System.out.println(location.getUUID() + "|" + location.getName() + " | " + location.getDate());
                linesPrinted++;
            }
        }
        Ergonomics.waitAnyKey();
        linesPrinted++;
        Ergonomics.clearLines(linesPrinted);
    }
    public static void viewLocationByDateDataCollector(){
        scanner.nextLine();
        LocalDate startDate = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (startDate == null) {
            System.out.print(Prefix + "Enter start date (yyyy-MM-dd, press enter for today): ");
            String dateInput = scanner.nextLine().trim();
            Ergonomics.clearLines(1);
            if (dateInput.isEmpty()) {
                startDate = LocalDate.now();
            } else {
                try {
                    startDate = LocalDate.parse(dateInput, formatter);
                } catch (Exception e) {
                    System.out.println(Error + "Invalid date format. Use yyyy-MM-dd.");
                    Ergonomics.waitAnyKey();
                    Ergonomics.clearLines(2);
                }
            }
        }
        LocalDate endDate = null;
        while (endDate == null) {
            System.out.print(Prefix + "Enter end date (yyyy-MM-dd, press enter for today): ");
            String dateInput = scanner.nextLine().trim();
            Ergonomics.clearLines(1);
            if (dateInput.isEmpty()) {
                endDate = LocalDate.now();
            } else {
                try {
                    endDate = LocalDate.parse(dateInput, formatter);
                } catch (Exception e) {
                    System.out.println(Error + "Invalid date format. Use yyyy-MM-dd.");
                    Ergonomics.waitAnyKey();
                    Ergonomics.clearLines(2);
                }
            }
        }
        List<Location> locationList = DatabaseManager.viewLocationsByDate(startDate.toString(), endDate.toString());
        int linesPrinted = 0;
        if (locationList.isEmpty()) {
            System.out.println(Error + "There are no locations in the database for this date range!");
            linesPrinted++;
        } else {
            for (Location location : locationList) {
                System.out.println(location.getUUID() + "|" + location.getName() + " | " + location.getDate());
                linesPrinted++;
            }
        }
        Ergonomics.waitAnyKey();
        linesPrinted++;
        Ergonomics.clearLines(linesPrinted);
    }
    public static void editLocationDataCollector() {
        System.out.print(Prefix + "Enter location's name: ");
        scanner.nextLine();
        String name = scanner.nextLine();
        Ergonomics.clearLines(1);
        if (name.isEmpty()) {
            System.out.println(Error + "this location doesn't exist in the database");
            Ergonomics.waitAnyKey();
            Ergonomics.clearLines(2);
            return;
        }
        List<Location> locationProperties = DatabaseManager.viewLocationByName(name);
        while (locationProperties.isEmpty()) {
            System.out.println(Error + "invalid name");
            Ergonomics.waitAnyKey();
            Ergonomics.clearLines(2);
            System.out.print(Prefix + "Enter location's name: ");
            name = scanner.nextLine();
            Ergonomics.clearLines(1);
            locationProperties = DatabaseManager.viewLocationByName(name);
        }

        Location propertyList = locationProperties.getFirst();

        String newName = propertyList.getName();
        String date = propertyList.getDate();

        System.out.println(Prefix + YELLOW + "\n--- UPDATE LOCATION (Press ENTER to keep current value) ---" + RESET);
        System.out.print(Prefix + "New name [" + newName + "]: ");
        String inputName = scanner.nextLine().trim();
        Ergonomics.clearLines(1);
        if (!inputName.isEmpty()) {
            newName = inputName;
        }

        System.out.print(Prefix + "New date [" + date + "]: ");
        String inputDate = scanner.nextLine().trim();
        Ergonomics.clearLines(1);
        if (!inputDate.isEmpty()) {
            date = inputDate;
        }

        System.out.println(Prefix + YELLOW + "\n --- Confirm Updated Location ---" + RESET);
        System.out.println(Prefix + CYAN + " Location: " + RESET + newName + CYAN + " | Date: " + RESET + date);

        boolean confirmed = Ergonomics.comfirmAction(scanner);
        Ergonomics.clearLines(3 + 2); // clear confirmation block + header lines

        if (!confirmed) {
            return;
        }

        DatabaseManager.editLocation(name, newName, date);
        System.out.println(Prefix + GREEN + "Location updated successfully!" + RESET);
        Ergonomics.waitAnyKey();
        Ergonomics.clearLines(2);
    }
    public static void deleteLocationDataCollector(){
        System.out.print(Prefix + "Enter Location's name (leave it blank to cancel): ");
        String name = scanner.nextLine();
        Ergonomics.clearLines(1);
        while(name.isBlank()){
            name = scanner.nextLine();
            Ergonomics.clearLines(1);
        }
        List<Location> locationList = DatabaseManager.viewLocationByName(name);
        if(locationList.isEmpty()){
            System.out.println(Error + "This location does not exist in the database!");
            Ergonomics.waitAnyKey();
            Ergonomics.clearLines(2);
            return;
        }
        Location location = locationList.getFirst();

        System.out.println(Prefix + YELLOW + " --- Confirm Deletion ---" + RESET);
        System.out.println(Prefix + RED + " UUID: " + RESET + location.getUUID() + RED + " | Name: " + RESET + location.getName() + RED + " | Date: " + RESET + location.getDate() + RESET);

        boolean confirmed = Ergonomics.comfirmAction(scanner);
        Ergonomics.clearLines(3);

        if (!confirmed) {
            return;
        }

        DatabaseManager.deleteLocation(name);
        System.out.println(Prefix + GREEN + "Location deleted successfully!" + RESET);
        Ergonomics.waitAnyKey();
        Ergonomics.clearLines(2);
    }




    //--------------- STOCK IN ---------------


    public static void stockinDataCollector(){
        System.out.print(Prefix + "Enter item's name: ");
        scanner.nextLine();
        String Name = scanner.nextLine();
        Ergonomics.clearLines(1);
        List<Item> itemsPropertie = DatabaseManager.viewItem(Name);
        if(itemsPropertie.isEmpty()){
            System.out.println(Error + "This item does not exist in the database!");
            Ergonomics.waitAnyKey();
            Ergonomics.clearLines(2);
            return;
        }
        System.out.print(Prefix + "Enter location's name: ");
        String Location = scanner.nextLine();
        Ergonomics.clearLines(1);
        List<Location> locationList = DatabaseManager.viewLocationByName(Location);
        if(locationList.isEmpty()){
            System.out.println(Error + "This location does not exist in the database!");
            Ergonomics.waitAnyKey();
            Ergonomics.clearLines(2);
            return;
        }
        System.out.print(Prefix + "Enter the amount of units: ");
        String stock = scanner.nextLine();
        Ergonomics.clearLines(1);
        int stockCount;
        try{
            stockCount = Integer.parseInt(stock);
            if(stockCount <=0){
                System.out.println(Error + "Invalide input");
                Ergonomics.waitAnyKey();
                Ergonomics.clearLines(2);
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println(Error + "Invalid input!");
            Ergonomics.waitAnyKey();
            Ergonomics.clearLines(2);
            return;
        }
        System.out.print(Prefix + "Note: ");
        String Note = scanner.nextLine();
        Ergonomics.clearLines(1);
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
                    System.out.println(Error + "Invalid date format. Use yyyy-MM-dd.");
                    Ergonomics.waitAnyKey();
                    Ergonomics.clearLines(2);
                }
            }
        }

        System.out.println(Prefix + YELLOW + " --- Confirm Stock In Details ---" + RESET);
        System.out.println(Prefix + CYAN + " Item: " + RESET + Name + CYAN + " | Location: " + RESET + Location + CYAN + " | Amount: " + RESET + stockCount + CYAN + " | Note: " + RESET + Note + CYAN + " | Date: " + RESET + createdAt);

        boolean confirmed = Ergonomics.comfirmAction(scanner);
        Ergonomics.clearLines(3);

        if (!confirmed) {
            return;
        }

        DatabaseManager.stockIn(itemsPropertie.getFirst().getUuid(),locationList.getFirst().getUUID(),stockCount,Note, String.valueOf(createdAt));
        System.out.println(Prefix + GREEN + "Stock added successfully!" + RESET);
        Ergonomics.waitAnyKey();
        Ergonomics.clearLines(2);
    }
    public static void stockoutDataCollector(){
        System.out.print(Prefix + "Enter item's name: ");
        scanner.nextLine();
        String Name = scanner.nextLine();
        Ergonomics.clearLines(1);
        List<Item> itemsPropertie = DatabaseManager.viewItem(Name);
        if(itemsPropertie.isEmpty()){
            System.out.println(Error + "This item does not exist in the database!");
            Ergonomics.waitAnyKey();
            Ergonomics.clearLines(2);
            return;
        }
        System.out.print(Prefix + "Enter location's name: ");
        String Location = scanner.nextLine();
        Ergonomics.clearLines(1);
        List<Location> locationList = DatabaseManager.viewLocationByName(Location);
        if(locationList.isEmpty()){
            System.out.println(Error + "This location does not exist in the database!");
            Ergonomics.waitAnyKey();
            Ergonomics.clearLines(2);
            return;
        }
        System.out.print(Prefix + "Enter the amount of units: ");
        String stock = scanner.nextLine();
        Ergonomics.clearLines(1);
        int stockCount;
        try{
            stockCount = Integer.parseInt(stock);
            if(stockCount <=0){
                System.out.println(Error + "Invalide input");
                Ergonomics.waitAnyKey();
                Ergonomics.clearLines(2);
                return;
            }
            int stockLevels = DatabaseManager.viewStockLevels(itemsPropertie.getFirst().getUuid());
            if(stockCount>stockLevels){
                System.out.println(Error + "Not enough units to do this action, current available units: " + stockLevels);
                Ergonomics.waitAnyKey();
                Ergonomics.clearLines(2);
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println(Error + "Invalid input!");
            Ergonomics.waitAnyKey();
            Ergonomics.clearLines(2);
            return;
        }
        System.out.print(Prefix + "Note: ");
        String Note = scanner.nextLine();
        Ergonomics.clearLines(1);
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
                    System.out.println(Error + "Invalid date format. Use yyyy-MM-dd.");
                    Ergonomics.waitAnyKey();
                    Ergonomics.clearLines(2);
                }
            }
        }

        System.out.println(Prefix + YELLOW + " --- Confirm Stock Out Details ---" + RESET);
        System.out.println(Prefix + CYAN + " Item: " + RESET + Name + CYAN + " | Location: " + RESET + Location + CYAN + " | Amount: " + RESET + stockCount + CYAN + " | Note: " + RESET + Note + CYAN + " | Date: " + RESET + createdAt);

        boolean confirmed = Ergonomics.comfirmAction(scanner);
        Ergonomics.clearLines(3);

        if (!confirmed) {
            return;
        }

        DatabaseManager.stockOut(itemsPropertie.getFirst().getUuid(),locationList.getFirst().getUUID(),stockCount,Note, String.valueOf(createdAt));
        System.out.println(Prefix + GREEN + "Stock removed successfully!" + RESET);
        Ergonomics.waitAnyKey();
        Ergonomics.clearLines(2);
    }
    public static void adjustStockValue(){
        System.out.print(Prefix + "Enter item's name: ");
        scanner.nextLine();
        String Name = scanner.nextLine();
        Ergonomics.clearLines(1);
        List<Item> itemsPropertie = DatabaseManager.viewItem(Name);
        if(itemsPropertie.isEmpty()){
            System.out.println(Error + "This item does not exist in the database!");
            Ergonomics.waitAnyKey();
            Ergonomics.clearLines(2);
            return;
        }
        System.out.print(Prefix + "Enter location's name: ");
        String Location = scanner.nextLine();
        Ergonomics.clearLines(1);
        List<Location> locationList = DatabaseManager.viewLocationByName(Location);
        if(locationList.isEmpty()){
            System.out.println(Error + "This location does not exist in the database!");
            Ergonomics.waitAnyKey();
            Ergonomics.clearLines(2);
            return;
        }
        System.out.print(Prefix + "Enter the amount of units: ");
        String stock = scanner.nextLine();
        Ergonomics.clearLines(1);
        int stockCount;
        try{
            stockCount = Integer.parseInt(stock);
            int stockLevels = DatabaseManager.viewStockLevels(itemsPropertie.getFirst().getUuid());
            if(stockCount+stockLevels<0){
                System.out.println(Error + "Not enough units to do this action, current available units: " + stockLevels);
                Ergonomics.waitAnyKey();
                Ergonomics.clearLines(2);
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println(Error + "Invalid input!");
            Ergonomics.waitAnyKey();
            Ergonomics.clearLines(2);
            return;
        }
        System.out.print(Prefix + "Note: ");
        String Note = scanner.nextLine();
        Ergonomics.clearLines(1);
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
                    System.out.println(Error + "Invalid date format. Use yyyy-MM-dd.");
                    Ergonomics.waitAnyKey();
                    Ergonomics.clearLines(2);
                }
            }
        }

        System.out.println(Prefix + YELLOW + " --- Confirm Stock Adjustment Details ---" + RESET);
        System.out.println(Prefix + CYAN + " Item: " + RESET + Name + CYAN + " | Location: " + RESET + Location + CYAN + " | Amount: " + RESET + stockCount + CYAN + " | Note: " + RESET + Note + CYAN + " | Date: " + RESET + createdAt);

        boolean confirmed = Ergonomics.comfirmAction(scanner);
        Ergonomics.clearLines(3);

        if (!confirmed) {
            return;
        }

        DatabaseManager.adjustStock(itemsPropertie.getFirst().getUuid(),locationList.getFirst().getUUID(),stockCount,Note, String.valueOf(createdAt));
        System.out.println(Prefix + GREEN + "Stock adjusted successfully!" + RESET);
        Ergonomics.waitAnyKey();
        Ergonomics.clearLines(2);
    }
    public static void viewStockFunc(){
        viewStockList option = viewStockSelector();
        switch(option){
            case NAME->viewStockLevelsDataCollector();
            case CATEGORY->viewStockByCategoryDataCollector();
            case DATE->viewStockByDateDataCollector();
            case LOCATION->viewStockByLocationDataCollector();
            case QUIT->{return;}
        }
    }
    public static viewStockList viewStockSelector() {
        System.out.printf(
                YELLOW + "%-4s" + CYAN + "%-28s" + RESET + "%s%n" +
                        YELLOW + "%-4s" + CYAN + "%-28s" + RESET + "%s%n" +
                        YELLOW + "%-4s" + CYAN + "%-28s" + RESET + "%s%n" +
                        YELLOW + "%-4s" + CYAN + "%-28s" + RESET + "%s%n" +
                        YELLOW + "%-4s" + CYAN + "%-28s" + RESET + "%s%n",

                "1.", "View stock by item name", "search and display current stock levels using a specific item name",
                "2.", "View stock by category", "filter and group your current stock levels by their assigned categories",
                "3.", "View stock by date period", "track stock levels and adjustments within a specific timeframe",
                "4.", "View stock by location", "check and compare current stock availability across different locations",
                "0.", "Exit", "go back to the previous menu selection"
        );

        int option = scanner.nextInt();
        switch (option) {
            case 1 -> {
                return viewStockList.NAME;
            }
            case 2 -> {
                return viewStockList.CATEGORY;
            }
            case 3 -> {
                return viewStockList.DATE;
            }
            case 4 -> {
                return viewStockList.LOCATION;
            }
            case 0->{
                return viewStockList.QUIT;
            }
            default -> {
                return viewStockList.QUIT;
            }
        }
    }
    public static void viewStockLevelsDataCollector(){
        System.out.print(Prefix + "Enter item's name: ");
        scanner.nextLine();
        String Name = scanner.nextLine();
        Ergonomics.clearLines(1);
        List<Item> itemsPropertie = DatabaseManager.viewItem(Name);
        int linesPrinted = 0;
        if(itemsPropertie.isEmpty()){
            System.out.println(Error + "This item does not exist in the database!");
            linesPrinted++;
        } else {
            int stockLevels = DatabaseManager.viewStockLevels(itemsPropertie.getFirst().getUuid());
            if(stockLevels == -1){
                System.out.println(Error + "Could not retrieve stock levels for this item.");
                linesPrinted++;
            } else {
                System.out.println(Prefix + Name+" stocks are: " + stockLevels);
                linesPrinted++;
            }
        }
        Ergonomics.waitAnyKey();
        linesPrinted++;
        Ergonomics.clearLines(linesPrinted);
    }
    public static void viewStockByCategoryDataCollector(){
        scanner.nextLine();
        System.out.print(Prefix + "Enter category: ");
        String category = scanner.nextLine().trim();
        Ergonomics.clearLines(1);
        List<String> stockData = DatabaseManager.viewStockLevelsByCategory(category);
        int linesPrinted = 0;
        if (stockData.isEmpty()) {
            System.out.println(Error + "There are no items in this category!");
            linesPrinted++;
        } else {
            for (String line : stockData) {
                System.out.println(Prefix + line);
                linesPrinted++;
            }
        }
        Ergonomics.waitAnyKey();
        linesPrinted++;
        Ergonomics.clearLines(linesPrinted);
    }
    public static void viewStockByLocationDataCollector(){
        scanner.nextLine();
        System.out.print(Prefix + "Enter location's name: ");
        String locationName = scanner.nextLine().trim();
        Ergonomics.clearLines(1);
        List<Location> locationList = DatabaseManager.viewLocationByName(locationName);
        int linesPrinted = 0;
        if(locationList.isEmpty()){
            System.out.println(Error + "This location does not exist in the database!");
            linesPrinted++;
        } else {
            List<String> stockData = DatabaseManager.viewStockLevelsByLocation(locationList.getFirst().getUUID());
            if (stockData.isEmpty()) {
                System.out.println(Error + "There are no stock movements at this location!");
                linesPrinted++;
            } else {
                for (String line : stockData) {
                    System.out.println(Prefix + line);
                    linesPrinted++;
                }
            }
        }
        Ergonomics.waitAnyKey();
        linesPrinted++;
        Ergonomics.clearLines(linesPrinted);
    }
    public static void viewStockByDateDataCollector(){
        scanner.nextLine();
        LocalDate startDate = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (startDate == null) {
            System.out.print(Prefix + "Enter start date (yyyy-MM-dd, press enter for today): ");
            String dateInput = scanner.nextLine().trim();
            Ergonomics.clearLines(1);
            if (dateInput.isEmpty()) {
                startDate = LocalDate.now();
            } else {
                try {
                    startDate = LocalDate.parse(dateInput, formatter);
                } catch (Exception e) {
                    System.out.println(Error + "Invalid date format. Use yyyy-MM-dd.");
                    Ergonomics.waitAnyKey();
                    Ergonomics.clearLines(2);
                }
            }
        }
        LocalDate endDate = null;
        while (endDate == null) {
            System.out.print(Prefix + "Enter end date (yyyy-MM-dd, press enter for today): ");
            String dateInput = scanner.nextLine().trim();
            Ergonomics.clearLines(1);
            if (dateInput.isEmpty()) {
                endDate = LocalDate.now();
            } else {
                try {
                    endDate = LocalDate.parse(dateInput, formatter);
                } catch (Exception e) {
                    System.out.println(Error + "Invalid date format. Use yyyy-MM-dd.");
                    Ergonomics.waitAnyKey();
                    Ergonomics.clearLines(2);
                }
            }
        }
        List<String> stockData = DatabaseManager.viewStockLevelsByDate(startDate.toString(), endDate.toString());
        int linesPrinted = 0;
        if (stockData.isEmpty()) {
            System.out.println(Error + "There are no stock movements for this date range!");
            linesPrinted++;
        } else {
            for (String line : stockData) {
                System.out.println(Prefix + line);
                linesPrinted++;
            }
        }
        Ergonomics.waitAnyKey();
        linesPrinted++;
        Ergonomics.clearLines(linesPrinted);
    }

    public static void transferStockDataCollector(){
        System.out.print(Prefix + "Enter item's name: ");
        scanner.nextLine();
        String Name = scanner.nextLine();
        Ergonomics.clearLines(1);
        List<Item> itemsPropertie = DatabaseManager.viewItem(Name);
        if(itemsPropertie.isEmpty()){
            System.out.println(Error + "This item does not exist in the database!");
            Ergonomics.waitAnyKey();
            Ergonomics.clearLines(2);
            return;
        }
        System.out.print(Prefix + "Transfer from: ");
        String firstLocation = scanner.nextLine();
        Ergonomics.clearLines(1);
        List<Location> firstLocationList = DatabaseManager.viewLocationByName(firstLocation);
        if(firstLocationList.isEmpty()){
            System.out.println(Error + "This location does not exist in the database!");
            Ergonomics.waitAnyKey();
            Ergonomics.clearLines(2);
            return;
        }
        System.out.print(Prefix + "Transfer to: ");
        String lastLocation = scanner.nextLine();
        Ergonomics.clearLines(1);
        List<Location> lastLocationList = DatabaseManager.viewLocationByName(lastLocation);
        if(lastLocationList.isEmpty()){
            System.out.println(Error + "This location does not exist in the database!");
            Ergonomics.waitAnyKey();
            Ergonomics.clearLines(2);
            return;
        }
        System.out.print(Prefix + "Enter amount of units: ");
        String stock = scanner.nextLine();
        Ergonomics.clearLines(1);
        int stockCount;
        try{
            stockCount = Integer.parseInt(stock);
            if(stockCount <=0){
                System.out.println(Error + "Invalide input");
                Ergonomics.waitAnyKey();
                Ergonomics.clearLines(2);
                return;
            }
            int stockLevels = DatabaseManager.viewStockLevelsByItemAndLocation(itemsPropertie.getFirst().getUuid(), firstLocationList.getFirst().getUUID());
            if(stockCount>stockLevels){
                System.out.println(Error + "Not enough units available in "+ firstLocation +", current available units are: " + stockLevels);
                Ergonomics.waitAnyKey();
                Ergonomics.clearLines(2);
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println(Error + "Invalid input!");
            Ergonomics.waitAnyKey();
            Ergonomics.clearLines(2);
            return;
        }
        System.out.print(Prefix + "Note: ");
        String Note = scanner.nextLine();
        Ergonomics.clearLines(1);
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
                    System.out.println(Error + "Invalid date format. Use yyyy-MM-dd.");
                    Ergonomics.waitAnyKey();
                    Ergonomics.clearLines(2);
                }
            }
        }

        System.out.println(Prefix + YELLOW + " --- Confirm Stock Transfer Details ---" + RESET);
        System.out.println(Prefix + CYAN + " Item: " + RESET + Name + CYAN + " | From: " + RESET + firstLocation + CYAN + " | To: " + RESET + lastLocation + CYAN + " | Amount: " + RESET + stockCount + CYAN + " | Note: " + RESET + Note + CYAN + " | Date: " + RESET + createdAt);

        boolean confirmed = Ergonomics.comfirmAction(scanner);
        Ergonomics.clearLines(3);

        if (!confirmed) {
            return;
        }

        DatabaseManager.transferStock(itemsPropertie.getFirst().getUuid(),firstLocationList.getFirst().getUUID(),lastLocationList.getFirst().getUUID(),stockCount,Note,createdAt);
        System.out.println(Prefix + GREEN + "Stock transferred successfully!" + RESET);
        Ergonomics.waitAnyKey();
        Ergonomics.clearLines(2);
    }

    public static void reportsFunc(){
        reportList option = reportsSelector();
        switch(option){
            case GLOBAL->lowStockReportDataCollector();
            case LOCATION->lowStockByLocationDataCollector();
            case QUIT->{return;}
        }
    }
    public static reportList reportsSelector() {
        System.out.printf(
                YELLOW + "%-4s" + CYAN + "%-32s" + RESET + "%s%n" +
                        YELLOW + "%-4s" + CYAN + "%-32s" + RESET + "%s%n" +
                        YELLOW + "%-4s" + CYAN + "%-32s" + RESET + "%s%n",

                "1.", "Global low stock report", "check items that need restocking across all warehouses",
                "2.", "Low stock report by location", "filter items that need restocking for a specific warehouse",
                "0.", "Exit", "go back to the previous menu selection"
        );

        int option = scanner.nextInt();
        switch (option) {
            case 1 -> {
                return reportList.GLOBAL;
            }
            case 2 -> {
                return reportList.LOCATION;
            }
            case 0 -> {
                return reportList.QUIT;
            }
            default -> {
                return reportList.QUIT;
            }
        }
    }
    public static void lowStockReportDataCollector(){
        List<String> lowStockItems = DatabaseManager.getLowStockItems();
        int linesPrinted = 0;
        if (lowStockItems.isEmpty()) {
            System.out.println(Error + "No items are below their reorder point.");
            linesPrinted++;
        } else {
            System.out.println(Prefix + YELLOW + " ==== Low Stock Report ====" + RESET);
            linesPrinted++;
            for (String line : lowStockItems) {
                System.out.println(Prefix + line);
                linesPrinted++;
            }
        }
        Ergonomics.waitAnyKey();
        linesPrinted++;
        Ergonomics.clearLines(linesPrinted);
    }
    public static void lowStockByLocationDataCollector(){
        scanner.nextLine();
        System.out.print(Prefix + "Enter location's name: ");
        String locationName = scanner.nextLine().trim();
        Ergonomics.clearLines(1);
        List<Location> locationList = DatabaseManager.viewLocationByName(locationName);
        int linesPrinted = 0;
        if(locationList.isEmpty()){
            System.out.println(Error + "This location does not exist in the database!");
            linesPrinted++;
        } else {
            List<String> lowStockItems = DatabaseManager.getLowStockItemsByLocation(locationList.getFirst().getUUID());
            if (lowStockItems.isEmpty()) {
                System.out.println(Error + "No items are below their reorder point at this location.");
                linesPrinted++;
            } else {
                System.out.println(Prefix + YELLOW + " ==== Low Stock Report: " + locationName + " ====" + RESET);
                linesPrinted++;
                for (String line : lowStockItems) {
                    System.out.println(Prefix + line);
                    linesPrinted++;
                }
            }
        }
        Ergonomics.waitAnyKey();
        linesPrinted++;
        Ergonomics.clearLines(linesPrinted);
    }

    public static void itemHistoryDataCollector(){
        System.out.print(Prefix + "Enter item's name: ");
        scanner.nextLine();
        String Name = scanner.nextLine();
        Ergonomics.clearLines(1);
        List<Item> itemsPropertie = DatabaseManager.viewItem(Name);
        int linesPrinted = 0;
        if(itemsPropertie.isEmpty()){
            System.out.println(Error + "This item does not exist in the database!");
            linesPrinted++;
        } else {
            List<StockMovement> stockMovements = DatabaseManager.getItemHistory(itemsPropertie.getFirst().getUuid());
            if (stockMovements.isEmpty()) {
                System.out.println(Error + "No movement history found for this item.");
                linesPrinted++;
            } else {
                System.out.println(Prefix + YELLOW + " ==== History " + Name + " ====" + RESET);
                System.out.println(Prefix + CYAN + "    DATE     | type | amount | location | note" + RESET);
                linesPrinted += 2;
                for (StockMovement movement : stockMovements) {
                    System.out.println(Prefix + movement);
                    linesPrinted++;
                }
            }
        }
        Ergonomics.waitAnyKey();
        linesPrinted++;
        Ergonomics.clearLines(linesPrinted);
    }
}