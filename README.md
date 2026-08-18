# Inventory Management System (IMS)

A command-line inventory management application built with Java and PostgreSQL. Manage items, locations, and track stock movements across multiple warehouses with ease.

## Features

- **Item Management**: Add, edit, delete, and view inventory items
- **Location Management**: Create and manage multiple storage locations/warehouses
- **Stock Operations**: 
  - Stock In (receive inventory)
  - Stock Out (remove inventory)
  - Adjust Stock (inventory corrections)
  - Transfer Stock (move items between locations)
- **Stock Level Tracking**: View stock levels by item, category, location, or date range
- **Low Stock Reports**: Get alerts for items below reorder points globally or by location
- **Item History**: Track all movement history for audit trails
- **PostgreSQL Integration**: Persistent data storage with UUID-based records

## Tech Stack

- **Language**: Java 26
- **Database**: PostgreSQL 42.7.2
- **Build Tool**: Maven 3
- **Terminal UI**: JLine 3.26.3

## Prerequisites

Before running the application, ensure you have:

- **Java 26** or higher installed
- **PostgreSQL 12** or higher running and accessible
- **Maven 3.6+** for building the project
- PostgreSQL account with database creation permissions

## Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/GrassInSuit/Inventory-Management-System.git
cd Inventory-Management-System
```

### 2. Set Up PostgreSQL Database

Create a new PostgreSQL database and user:

```sql
-- Create database
CREATE DATABASE inventory_management;

-- Create user (optional, but recommended)
CREATE USER inventory_user WITH PASSWORD 'your_secure_password';
ALTER ROLE inventory_user SET client_encoding TO 'utf8';
ALTER ROLE inventory_user SET default_transaction_isolation TO 'read committed';
ALTER ROLE inventory_user SET default_transaction_deferrable TO on;
ALTER ROLE inventory_user GRANT ALL PRIVILEGES ON DATABASE inventory_management TO inventory_user;
```

### 3. Create Database Schema

Run the following SQL commands in your PostgreSQL database:

```sql
-- Items Table
CREATE TABLE items (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    sku VARCHAR(255) UNIQUE,
    name VARCHAR(255) NOT NULL,
    unit VARCHAR(100),
    category VARCHAR(100),
    reorder_point INTEGER DEFAULT 0,
    active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Locations Table
CREATE TABLE locations (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Stock Movements Table
CREATE TABLE stock_movements (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    item_id UUID NOT NULL REFERENCES items(id),
    location_id UUID NOT NULL REFERENCES locations(id),
    quantity_delta INTEGER NOT NULL,
    movement_type VARCHAR(50) NOT NULL, -- 'IN', 'OUT', 'ADJUST', 'TRANSFER_IN', 'TRANSFER_OUT'
    reference_id UUID,
    note TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better query performance
CREATE INDEX idx_stock_movements_item_id ON stock_movements(item_id);
CREATE INDEX idx_stock_movements_location_id ON stock_movements(location_id);
CREATE INDEX idx_stock_movements_created_at ON stock_movements(created_at);
CREATE INDEX idx_items_category ON items(category);
```

### 4. Build the Project

```bash
mvn clean package
```

### 5. Run the Application

```bash
mvn exec:java
```

Or run the compiled JAR:

```bash
java -cp target/CLI-Inventory-1.0-SNAPSHOT.jar org.example.Main
```

## Usage

When you start the application, you'll be prompted to connect to your PostgreSQL database:

```
---Welcome to IMS---
➤ Connecting to your Database:
→ Enter Database URL (default: localhost:5432):
→ Enter Database's name: inventory_management
→ Enter Username: inventory_user
→ Enter password:
```

### Main Menu

Once connected, you'll see the main menu with 10 options:

```
1. MANAGE ITEMS
   - Add Item: Create new inventory items
   - Delete Item: Deactivate items
   - View Item: Search items by name, category, or date
   - Edit Item: Update item properties

2. MANAGE LOCATIONS
   - Add Location: Register new warehouses/storage areas
   - Delete Location: Remove locations
   - View Location: Browse all locations

3. STOCK IN
   - Record incoming inventory

4. STOCK OUT
   - Record outgoing inventory (with validation)

5. ADJUST STOCK
   - Correct inventory discrepancies

6. TRANSFER STOCK
   - Move items between locations

7. VIEW STOCK LEVELS
   - Check stock by item, category, location, or date range

8. LOW STOCK REPORTS
   - Get global or location-specific reorder alerts

9. VIEW ITEMS HISTORY
   - Audit trail of all item movements

0. EXIT
   - Close the application
```

### Example Workflow

#### Adding an Item

```
1. Select "MANAGE ITEMS" → "Add Item"
2. Enter SKU (auto-generates if left blank)
3. Enter item name (required): "Widget A"
4. Enter unit of measure: "box"
5. Enter category: "Electronics"
6. Enter reorder point: 50
7. Enter date (defaults to today)
8. Confirm and save
```

#### Recording Stock In

```
1. Select "STOCK IN"
2. Enter item name: "Widget A"
3. Enter location: "Warehouse 1"
4. Enter quantity: 100
5. Enter note: "Initial stock purchase"
6. Confirm
```

#### Checking Stock Levels

```
1. Select "VIEW STOCK LEVELS"
2. Choose option (by item, category, location, or date)
3. Enter search parameters
4. View current stock
```

## Project Structure

```
Inventory-Management-System/
├── src/main/java/org/example/
│   ├── Main.java                 # Application entry point & login
│   ├── ContactSystem.java        # Database connection manager
│   ├── DatabaseManager.java      # CRUD operations & queries
│   ├── interFunctions.java       # CLI menu & user interactions
│   ├── Ergonomics.java          # UI formatting & utilities
│   ├── Item.java                # Item data model
│   ├── Location.java            # Location data model
│   ├── StockMovement.java       # Stock movement data model
│   └── ContactSystem.java        # Database connection handler
├── pom.xml                       # Maven dependencies
├── .gitignore                    # Git ignore rules
└── README.md                     # This file
```

## Database Connection Details

The application requires these PostgreSQL connection parameters at startup:

| Parameter | Description | Default |
|-----------|-------------|---------|
| Database URL | Host and port | localhost:5432 |
| Database Name | PostgreSQL database name | (required) |
| Username | PostgreSQL user | postgres |
| Password | User password | (required) |

## Features in Detail

### Item Management
- **SKU**: Unique stock keeping unit (auto-generated if blank)
- **Name**: Item display name (required)
- **Unit**: Measurement unit (each, kg, box, etc.)
- **Category**: Group items for filtering
- **Reorder Point**: Minimum stock threshold for alerts

### Stock Movements
All stock changes are logged with:
- Movement type (IN, OUT, ADJUST, TRANSFER_IN, TRANSFER_OUT)
- Quantity change
- Associated location
- Timestamp
- Optional notes

### Reports
- **Global Low Stock**: Shows all items below reorder point across all locations
- **Location-Specific**: Low stock items for a specific warehouse

## Known Limitations

- Password input appears as plain text when console is unavailable
- Date format is fixed to `yyyy-MM-dd`
- Single-user application (no multi-user support)
- No role-based access control

## Future Enhancements

- Database schema validation on startup
- Environment variable configuration (`.env` support)
- Export reports to CSV/PDF
- Multi-user support with authentication
- REST API for external integrations
- Web-based UI

## Troubleshooting

### Connection Refused
```
Error: Connection refused
```
- Ensure PostgreSQL is running: `psql -U postgres`
- Verify database exists: `\l` in psql
- Check host and port are correct

### Table Not Found
```
Error: table items not found
```
- Run the schema creation SQL from Step 3 above
- Verify you're using the correct database

### Out of Memory
```
Exception: Java heap space
```
- Increase heap size: `java -Xmx2g -Xms1g ...`

### Invalid Date Format
- Use format `yyyy-MM-dd` (e.g., `2024-01-15`)
- Press Enter to use today's date

## License

This project is open source and available under the MIT License.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## Support

For issues, questions, or suggestions, please open an issue on the GitHub repository.

---

**Version**: 1.0-SNAPSHOT  
**Last Updated**: August 2026  
**Developed by**: GrassInSuit
