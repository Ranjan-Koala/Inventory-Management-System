# Inventory-Management-System

The Inventory Management System is designed to handle day-to-day inventory operations such as adding products, managing categories, tracking suppliers, monitoring stock levels, and generating low-stock alerts.
This project demonstrates how JDBC and Hibernate ORM can be used together effectively in a Java application for both direct SQL operations and ORM-based entity management.

## 🛠️ Tech Stack Used
### 1. Java

- Core language used for building the entire business logic.

- OOP principles like Encapsulation, Inheritance, Polymorphism, and Abstraction.

- Collections, Enums, Exception Handling, and Console Input.

### 2. JDBC (Java Database Connectivity)

- Handles direct SQL queries.

- Used for CRUD operations on Product, Category, Supplier, and Stock.

- Implements PreparedStatement, ResultSet, and secure DB operations.

### 3. Hibernate ORM

- Converts Java objects into database tables.

- Supports HQL queries, lazy loading, caching, and automatic schema management.

- Used for advanced operations like Low Stock Alerts.

### 4. Maven

- Used for dependency management and project build lifecycle.

- All libraries (Hibernate, MySQL driver, Logging) managed through pom.xml.

### 5. MySQL Database

- Stores all inventory-related data.

- Uses relational structure with foreign keys and indexing.

- Tables include:

        - product

        - category

        - supplier

        - stock_transaction

##  ✨ Features
### 1. Product Management

- Add new products with name, price, category, supplier, and initial stock.

- View all products with detailed information.

- Update product details (price, supplier, category).

- Organized structure for easy retrieval and display.

### 2. Category Management

- Add new categories (Electronics, Grocery, Fashion etc.).

- List all existing categories.

- Helps in organizing and filtering inventory items.

### 3. Supplier Management

- Add and maintain supplier details.

- Assign suppliers to specific products.

- Useful for tracking procurement and restocking needs.

### 4. Stock Management (IN / OUT)

- Add stock for any product (Stock In).

- Reduce stock when items are sold or consumed (Stock Out).

- Automatically updates the product quantity in real-time.

### 5. Low Stock Alert (Hibernate)

- Automatically detect products with low stock using Hibernate HQL queries.

- Helps store managers restock items before they run out.

- Threshold can easily be modified in code.

### 6. Hybrid Database Interaction

- JDBC for fast CRUD operations and direct SQL execution.

- Hibernate for entity mapping, lazy loading, and advanced queries.

- Demonstrates practical usage of both techniques in the same system.

### 7. Error Handling & Validation

- Custom exceptions for insufficient stock.

- Input validations to prevent wrong data entry.

- Improves reliability and user experience.

### 8. Modular Project Structure (Maven Based)

- Cleanly separated modules: DAO, Model, Service, and Utility.

- Easy to maintain and scalable.

- Maven manages all dependencies and builds.

## 📁 Project Folder Structure
<img width="845" height="435" alt="image" src="https://github.com/user-attachments/assets/d6a8ac4b-56a6-4187-86c3-8542ead922db" />

## 📘 User Guide
#### ▶️ Start the Application

Run:

App.java

You will see a menu:
1. Add Product
2. List All Products
3. Add Stock
4. Remove Stock
5. Show Low Stock (Hibernate)
6. Show Low Stock (JDBC)
7. Add Category
8. List Categories
9. Add Supplier
10. Exit

#### ▶️ Add a Product

Follow prompts for:

- Product Name

- Price

- Category ID

- Supplier ID

- Initial Stock

Saves using JDBC.

#### ▶️ List Products

Displays:

- Product ID

- Name

- Category

- Supplier

- Stock

- Price

#### ▶️ Add or Remove Stock

- Stock In — increases quantity.
- Stock Out — decreases quantity.
Throws an exception if stock is insufficient.

#### ▶️ Low Stock Alert

- Shows products with stock below the minimum level.

#### ▶️ Category & Supplier Management

- Add or list categories

- Add or list suppliers

## ⚙️ How to Run Locally
#### 1. Clone the Repository
- git clone https://github.com/<your-username>/<your-repo>.git

#### 2. Import as Maven Project
#### 3. Update MySQL Credentials in
- JDBCUtil.java
- hibernate.cfg.xml

#### 4. Create Database
- CREATE DATABASE inventory_db;

#### 5. Run App.java

## 🚀 Future Enhancements

- Add GUI using Spring Boot REST APIs

- Add authentication & login system

- Add sales reports and dashboard

- Export inventory data to Excel/PDF

- Add unit tests with JUnit

## ❤️ Contributions

- Pull requests are welcome.
